import React, { useEffect, useRef, useState } from "react";
import "./index.less";
import { ProCard } from "@ant-design/pro-components";
import { Button, Input, message, Space, Switch, Typography } from "antd";
import {
  downloadPDF,
  thoughtChainGenerate,
} from "../../services/thoughtService";
import TreeChart from "../TreeChart";
import NavHeader from "../Public/NavHeader";
import FullScreenLoading from "../Other/Loading";

const { Title, Paragraph } = Typography;

function downloadPDFByUrl(fileUrl: string | URL | Request) {
  fetch(fileUrl)
    .then((response) => {
      if (response.ok) {
        return response.blob();
      }
      throw new Error("Network response was not ok.");
    })
    .then((blob) => {
      const url = URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = "document.pdf"; // 指定下载文件的名称
      document.body.appendChild(a);
      a.click();
      window.URL.revokeObjectURL(url);
    })
    .catch((e) => console.error(e));
}

const ChatBox = () => {
  const [inputValue, setInputValue] = useState(""); // 用户输入值
  const [messages, setMessages] = useState([]); // 消息列表
  const inputRef = useRef(null); // 引用输入框DOM元素
  const [showTree, setShowTree] = useState(false);
  const [treeData, setTreeData] = useState(null);

  const [loading, setLoading] = useState(false);

  const [result, setResult] = useState("");

  useEffect(() => {
    // 滚动到消息列表底部
    const chatContent = document.getElementById("chat-content");
    chatContent.scrollTop = chatContent.scrollHeight;
  }, [messages]); // 当messages变化时，执行滚动

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(e.target.value);
  };

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      // 检测是否按下Enter键
      handleSendClick();
    }
  };

  const onDownloadRDF = () => {
    setLoading(true);
    downloadPDF(result)
      .then((res: any) => {
        if (res.code === 200) {
          downloadPDFByUrl(res.response);
        } else {
          message.error("下载失败");
        }
      })
      .catch((err: any) => {
        message.error("网络请求失败");
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const handleSendClick = () => {
    if (inputValue.trim()) {
      // 将用户输入添加到消息列表
      setMessages((prevMessages) => [
        ...prevMessages,
        { text: inputValue, type: "user" },
      ]);
      setInputValue(""); // 清空输入框
      setLoading(true);
      // 发送请求获取AI回答
      thoughtChainGenerate({ question: inputValue, method_choice: 1 })
        .then((response: any) => {
          if (response.code === 200) {
            // 将AI的回答添加到消息列表
            const aiResponse = response.response?.result
              ? response.response.result
              : `AI回复: 暂时忙碌...`;
            setTreeData(response.response?.tree_structure);
            setResult(aiResponse);
            setMessages((prevMessages) => [
              ...prevMessages,
              { text: aiResponse, type: "ai" },
            ]);
          } else {
            message.error("获取AI回答失败");
          }
        })
        .catch((error) => {
          message.error("网络请求失败");
        })
        .finally(() => {
          setLoading(false);
        });
    }
  };

  return (
    <>
      {loading && <FullScreenLoading spinning={loading} tip={"生成中"} />}
      <div className="xf-chat__page">
        <header className="xf-chat__header">
          <NavHeader />
        </header>
        {/* 消息卡片 */}
        <ProCard
          title={
            <h3
              style={{ fontFamily: "阿里妈妈数黑体 Bold", fontSize: "1.5rem" }}
            >
              思维链
            </h3>
          }
          style={{ width: "100%", height: "calc(100vh - 64px)" }}
        >
          <div className="chat-box">
            <div id="chat-content" className="chat-content">
              {messages.map((msg, index) => (
                <div key={index} className={"message-container"}>
                  <div className={`message ${msg.type}`}>
                    {msg.type === "ai" && (
                      <Space style={{ width: "100%" }}>
                        <Title level={5}>切换为图例模式:</Title>
                        <Switch onChange={setShowTree} />
                        <Button
                          type="primary"
                          onClick={onDownloadRDF}
                          style={{ marginLeft: "10px" }}
                        >
                          下载PDF
                        </Button>
                      </Space>
                    )}
                    {msg.type === "ai" && showTree ? (
                      <div
                        style={{
                          width: "1200px",
                          height: "700px",
                          padding: "10px 30px",
                          display: "flex",
                          justifyContent: "center",
                          alignItems: "center",
                        }}
                      >
                        <TreeChart data={treeData} />
                      </div>
                    ) : (
                      <Paragraph>{msg?.text}</Paragraph>
                    )}
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
              <Button
                type="primary"
                onClick={handleSendClick}
                className="send-btn"
              >
                发送
              </Button>
            </div>
          </div>
        </ProCard>
      </div>
    </>
  );
};

export default ChatBox;
