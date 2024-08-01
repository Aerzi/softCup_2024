from sparkai.llm.llm import ChatSparkLLM, ChunkPrintHandler
from sparkai.core.messages import ChatMessage
from anytree import Node, RenderTree
import os

# API 密钥信息
SPARKAI_APP_ID = 'd8b94204'
SPARKAI_API_SECRET = 'Y2U5NzdjNjg3MThjNjAzNzhlZWZhMzMz'
SPARKAI_API_KEY = 'a0db07729ba2bc74052138bbd6786421'
# SparkAI 模型 Spark3.5 URL
SPARKAI_URL = 'wss://spark-api.xf-yun.com/v3.5/chat'
# SparkAI 模型 Spark3.5的域名
SPARKAI_DOMAIN = 'generalv3.5'

def extract_project_details(question):
    # 使用 SparkAI 模型生成项目详情
    messages = [ChatMessage(
        role="user",
        content=f"请分析以下项目描述，并列出相关模块、技术栈和任务的详细步骤: {question}"
    )]
    handler = ChunkPrintHandler()
    response = spark.generate([messages], callbacks=[handler])

    # 调试输出响应对象结构

    # 从响应中提取内容
    response_content = None
    if response and hasattr(response, 'generations') and response.generations:
        nested_generations = response.generations
        if isinstance(nested_generations, list):
            while nested_generations:
                first_generation = nested_generations[0]
                if isinstance(first_generation, list):
                    nested_generations = first_generation
                    break
                elif isinstance(first_generation, dict) and 'text' in first_generation:
                    response_content = first_generation['text']
                    break
                elif hasattr(first_generation, 'text'):
                    response_content = first_generation.text
                    break
                else:
                    raise ValueError("响应格式无效，无法提取生成内容")
        else:
            raise ValueError("响应格式无效，无法提取生成内容")
    else:
        raise ValueError("无法提取生成内容")

    # 简单解析文本内容为字典
    details = {
        "modules": ["模块A", "模块B", "模块C"],
        "technologies": ["技术X", "技术Y"],
        "tasks": {
            "模块A": ["任务1.1", "任务1.2", "任务1.3"],
            "模块B": ["任务2.1", "任务2.2", "任务2.3"],
            "模块C": ["任务3.1", "任务3.2", "任务3.3"]
        }
    }
    # 根据实际 response_content 调整解析逻辑
    return details

def generate_tree_of_thought_prompt(question):
    prompts = [
        f"问题: {question}",
        "步骤 1: 确定项目主要目标和每一个潜在的子目标，子目标越多越好。",
        "步骤 2: 为每个子目标提出至少两个可能的、十分详细的子目标解决方案,每个方案200字。",
        "步骤 3: 评估每一个子目标解决方案会带来的结果，略微简短，并在这一步输出的最前面给每个方案定性：效果卓越/效果不佳/效果一般。",
        "步骤 4: 选择最优异的解决方案，结合主要目标为项目生成具有时序逻辑的详尽开发流程，形成最终答案，800字。",
        "步骤 5: 总结开发流程，着重指出难点。"
    ]
    return "\n".join(prompts)

def generate_pseudocode_prompt(question):
    prompts = [
        f"问题: {question}",
        "步骤 1: 定义项目的功能需求。",
        "步骤 2: 整理各个需求之间的先后顺序与依赖关系。",
        "步骤 3: 列出实现每一功能的主要算法和逻辑步骤。",
        "步骤 4: 为每一步编写伪代码。",
        "步骤 5: 整合所有伪代码以形成完整的项目代码计划。"
    ]
    return "\n".join(prompts)

def generate_project_development_prompt(question):
    prompts = [
        f"项目开发问题: {question}",
        "步骤 1: 明确项目的具体需求和目标，尽量详细。",
        "步骤 2: 选择合适的平台和技术。",
        "步骤 3: 创建详细的开发计划,500字以上,包括每个模块和使用的工具(必须详细)。",
        "步骤 4: 编写和测试每个模块。",
        "步骤 5: 集成所有模块并进行最终测试和优化。"
    ]
    detailed_steps = [
        "可能的平台如平台X，软件如软件Y，相关技术如技术Z等。",
        "推荐的框架和工具包可以参考框架X和工具包Y。"
    ]
    return "\n".join(prompts + detailed_steps)

def generate_dynamic_prompt(question, method):
    method_map = {
        "逐步解释": generate_tree_of_thought_prompt,
        "逐步伪代码": generate_pseudocode_prompt
    }
    # 增加动态选择逻辑
    if "项目开发" in question:
        return generate_project_development_prompt(question)
    else:
        method_func = method_map.get(method)
        if method_func:
            return method_func(question)
        else:
            raise ValueError("无效的方法，请选择有效的选项。")

def split_final_answer(answer_text):
    steps = answer_text.split('步骤 ')
    result = []
    for step in steps:
        step = step.strip()
        if step:
            if step[0].isdigit():
                result.append(f"步骤 {step}")
            else:
                result[-1] += f" {step}"
    return result

def run_spark_model(question, method_choice):
    method_dict = {
        "1": "逐步解释",
        "2": "逐步伪代码"
    }

    method = method_dict.get(method_choice)
    try:
        if method:
            prompt = generate_dynamic_prompt(question, method)

            # 生成详细的响应内容
            messages = [ChatMessage(
                role="user",
                content=prompt
            )]

            handler = ChunkPrintHandler()
            response = spark.generate([messages], callbacks=[handler])

            # 确认提取的响应内容
            response_text = None
            if response and hasattr(response, 'generations') and response.generations:
                nested_generations = response.generations
                if isinstance(nested_generations, list):
                    while nested_generations:
                        first_generation = nested_generations[0]
                        if isinstance(first_generation, list):
                            nested_generations = first_generation
                        elif isinstance(first_generation, dict) and 'text' in first_generation:
                            response_text = first_generation['text']
                            break
                        elif hasattr(first_generation, 'text'):
                            response_text = first_generation.text
                            break
                        else:
                            response_text = "无法提取正确的响应内容"
                else:
                    response_text = "无法提取正确的响应内容"
            else:
                response_text = "无法提取正确的响应内容"

            # 如果响应包含最终答案段落，进行切割
            if response_text:
                split_answer = split_final_answer(response_text)
                return split_answer, None
            else:
                return "无法提取正确的响应内容", None
        else:
            return "无效的选择，请重新运行程序并选择有效的选项。", None
    except Exception as e:
        # 确保在异常情况下返回两个值
        return str(e), None

spark = ChatSparkLLM(
    spark_api_url=SPARKAI_URL,
    spark_app_id=SPARKAI_APP_ID,
    spark_api_key=SPARKAI_API_KEY,
    spark_api_secret=SPARKAI_API_SECRET,
    spark_llm_domain=SPARKAI_DOMAIN,
    streaming=False,
)

def build_tree_structure(steps):
    def parse_step(step_text):
        lines = step_text.strip().split('\n')
        if not lines:
            return {"name": "", "children": []}

        step_name = lines[0].strip()
        children = []
        current_child = None
        is_step_two = step_name.startswith("步骤 2")  # 检查是否为步骤2
        
        for line in lines[1:]:
            line = line.strip()
            if line:
                if line.startswith("子目标"):
                    current_child = {"name": line, "children": []}
                    children.append(current_child)
                elif is_step_two and (line.startswith("方案A") or line.startswith("方案B")) and current_child:
                    solution_node = {"name": line, "children": []}
                    current_child["children"].append(solution_node)
                elif current_child:
                    current_child["children"].append({"name": line, "children": []})
                else:
                    children.append({"name": line, "children": []})
                    
        return {"name": step_name, "children": children}

    root = {"name": "指导概览", "children": []}
    for step in steps:
        parsed_step = parse_step(step)
        if parsed_step["name"]:  # 忽略空步骤
            root["children"].append(parsed_step)

    return root

def display_tree_structure(tree):
    import json
    print(json.dumps(tree, indent=4, ensure_ascii=False))



if __name__ == "__main__":
    question = input("请输入您的项目开发问题描述：")
    method_choice = input("请选择方法（1: 逐步解释，2: 逐步伪代码）：")

    steps, error = run_spark_model(question, method_choice)
    if error:
        print(f"出错了: {error}")
    else:
        for step in steps:
            print(step)

        tree_structure = build_tree_structure(steps)
        display_tree_structure(tree_structure)
        