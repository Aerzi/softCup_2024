from sparkai.llm.llm import ChatSparkLLM, ChunkPrintHandler
from sparkai.core.messages import ChatMessage
import json

# API 密钥信息
SPARKAI_APP_ID = 'd8b94204'
SPARKAI_API_SECRET = 'Y2U5NzdjNjg3MThjNjAzNzhlZWZhMzMz'
SPARKAI_API_KEY = 'a0db07729ba2bc74052138bbd6786421'
SPARKAI_URL = 'wss://spark-api.xf-yun.com/v3.5/chat'
SPARKAI_DOMAIN = 'generalv3.5'

spark = ChatSparkLLM(
    spark_api_url=SPARKAI_URL,
    spark_app_id=SPARKAI_APP_ID,
    spark_api_key=SPARKAI_API_KEY,
    spark_api_secret=SPARKAI_API_SECRET,
    spark_llm_domain=SPARKAI_DOMAIN,
    streaming=False,
)

def evaluate_code_with_sparkai(source_code):
    prompt = (
        f"请分析以下代码并返回详细的错误分析，优化建议，反馈，"
        f"可读性评分，效率评分：\n{source_code}"
    )

    messages = [ChatMessage(role="user", content=prompt)]
    handler = ChunkPrintHandler()
    response = spark.generate([messages], callbacks=[handler])

    if response and hasattr(response, 'generations') and response.generations:
        first_generation = response.generations[0]
        if isinstance(first_generation, list):
            first_generation = first_generation[0]
        if isinstance(first_generation, dict) and 'text' in first_generation:
            response_content = first_generation['text']
        elif hasattr(first_generation, 'text'):
            response_content = first_generation.text
        else:
            raise ValueError("响应格式无效，无法提取生成内容")
    else:
        raise ValueError("无法提取生成内容")

    return response_content

def parse_response(response_content):
    if response_content is None:
        raise ValueError("response_content is None")

    # 将 response_content 按段落拆分并解析为字典
    lines = response_content.strip().split("\n")
    result = {}
    current_section = None
    current_content = []

    for line in lines:
        line = line.strip()
        if not line:
            continue
        if line.startswith("错误分析："):
            if current_section:
                result[current_section] = "\n".join(current_content).strip()
            current_section = "error_analysis"
            current_content = [line.replace("错误分析：", "").strip()]
        elif line.startswith("优化建议："):
            if current_section:
                result[current_section] = "\n".join(current_content).strip()
            current_section = "optimization_suggestions"
            current_content = [line.replace("优化建议：", "").strip()]
        elif line.startswith("反馈："):
            if current_section:
                result[current_section] = "\n".join(current_content).strip()
            current_section = "feedback"
            current_content = [line.replace("反馈：", "").strip()]
        elif line.startswith("修改后的代码示例："):
            if current_section:
                result[current_section] = "\n".join(current_content).strip()
            current_section = "modified_code"
            current_content = []
        else:
            current_content.append(line)

    if current_section:
        result[current_section] = "\n".join(current_content).strip()

    # 确保评分字段为有效整数
    def extract_score(key, result_dict):
        score_str = result_dict.get(key, "0").split("/")[0]
        try:
            return int(score_str)
        except ValueError:
            return 0

    return result

if __name__ == "__main__":
    # 示例代码
    source_code = """
def fibonacci(n):
    if n<=0:
        return "输入必须是正整数"
    elif n == 1:
        return [0]
    elif n == 2:
        return [0, 1]
    
    fib_sequence = [0, 1]
    for i in range(2, n):
        next_value = fib_sequence[i-1] + fib_sequence[i-2]
        fib_sequence.append(next_value)
    
    return fib_sequence

n = 10
print(fibonacci(n))
    """
    try:
        response_content = evaluate_code_with_sparkai(source_code)
        if response_content:
            result = parse_response(response_content)
            relevant_result = {
                "error_analysis": result.get("error_analysis", "N/A"),
                "optimization_suggestions": result.get("optimization_suggestions", "N/A"),
                "feedback": result.get("feedback", "N/A"),
                "readability_score": result.get("readability_score", 0),
                "efficiency_score": result.get("efficiency_score", 0),
                "modified_code": result.get("modified_code", "N/A"),
            }
            print(json.dumps(relevant_result, ensure_ascii=False, indent=4))
        else:
            print("No valid response content extracted.")
    except Exception as e:
        print(f"出错了: {e}")