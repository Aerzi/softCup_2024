import React, { useEffect, useRef, useState } from "react";
import "./index.less";
import { ProCard } from "@ant-design/pro-components";
import { Button, Input } from "antd";

const ChatBox = () => {
    const [inputValue, setInputValue] = useState(''); // 用户输入值
    const [messages, setMessages] = useState([]); // 消息列表
    const inputRef = useRef(null); // 引用输入框DOM元素

    useEffect(() => {
        // 滚动到消息列表底部
        const chatContent = document.getElementById('chat-content');
        chatContent.scrollTop = chatContent.scrollHeight;
    }, [messages]); // 当messages变化时，执行滚动

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setInputValue(e.target.value);
    };

    const handleKeyDown = (e: React.KeyboardEvent) => {
        if (e.key === 'Enter') { // 检测是否按下Enter键
            handleSendClick();
        }
    };

    const handleSendClick = () => {
        if (inputValue.trim()) {
            // 将用户输入添加到消息列表
            setMessages(prevMessages => [...prevMessages, { text: inputValue, type: 'user' }]);
            setInputValue(''); // 清空输入框
            // 模拟AI回复，这里使用setTimeout来模拟网络延迟
            setTimeout(() => {
                setMessages(prevMessages => [...prevMessages, { text: `AI回复: ${inputValue}`, type: 'ai' }]);
            }, 1000);
        }
    };

    return (
        <div className='xf-chat__page'>
            <ProCard
                title={'思维链'}
                style={{ width: '100%', height: '100%', }}
                className="card-theme-light"
            >
                <div className="chat-box">
                    <div id="chat-content" className="chat-content">
                        {messages.map((msg, index) => (
                            <div key={index} className={"message-container"}>
                                <div className={`message ${msg.type}`}>
                                    {msg.text}
                                </div>
                            </div>
                        ))}
                    </div>
                    <div className="chat-footer">
                        <Input
                            type="text"
                            value={inputValue}
                            onChange={handleInputChange}
                            onKeyDown={handleKeyDown}
                            placeholder="输入你的消息..."
                            ref={inputRef}
                        />
                        <Button type="primary" onClick={handleSendClick} className="send-btn">发送</Button>
                    </div>

                </div>
            </ProCard>
        </div>

    );
};

export default ChatBox;