import asyncio
import websockets
import json
from AI_programming_main_script import run_spark_model,build_tree_structure  # 从AI.py中导入run_spark_model函数

async def process_question(websocket, path):
    async for message in websocket:
        data = json.loads(message)
        question = data.get('question')
        method_choice = data.get('method_choice')
        
        result, _ = run_spark_model(question, method_choice)
        
        tree_structure = build_tree_structure(result)
        
        formatted_tree_structure = json.dumps(tree_structure, indent=4, ensure_ascii=False)
        
        response = {
            'result': result,
            'tree_structure': json.loads(formatted_tree_structure)  # 保持格式化后的 tree_structure
        }
        
        await websocket.send(json.dumps(response, ensure_ascii=False))

start_server = websockets.serve(process_question, "localhost", 8890)

asyncio.get_event_loop().run_until_complete(start_server)
print("启动")
asyncio.get_event_loop().run_forever()