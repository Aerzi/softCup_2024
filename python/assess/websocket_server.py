import asyncio
import websockets
import json
from pingce import evaluate_code_with_sparkai,parse_response  # 从pingce.py中导入run_spark_model函数

async def process_question(websocket, path):
    async for message in websocket:
        data = json.loads(message)
        source_code = data.get('source_code')
        
        response_content = evaluate_code_with_sparkai(source_code)
    
        result = parse_response(response_content)
        
        await websocket.send(json.dumps(result))

start_server = websockets.serve(process_question, "localhost", 8891)

asyncio.get_event_loop().run_until_complete(start_server)
print("启动")
asyncio.get_event_loop().run_forever()