import React, { useEffect, useState } from "react";
import { Layout, Form, Input, Button, Modal, Typography, message } from "antd";
import { RightOutlined } from "@ant-design/icons";
import { ProCard } from "@ant-design/pro-components";
import DocsPreviewer from "../DocsPreviewer";
import { chatDocs } from "../../../services/docsService";
import FullScreenLoading from "../../Other/Loading";
import { json } from "react-router";
const { TextArea } = Input;
const { Text, Paragraph } = Typography;
const { Sider, Content } = Layout;

const DocumentQAPanel: React.FC = () => {
  const [question, setQuestion] = useState<string>(null);
  const [fileId, setFileId] = useState("");
  const [loading, setLoading] = useState(false);

  const [form] = Form.useForm();
  const [answer, setAnswer] = useState<string>("");

  const onFinish = (values: any) => {
    setLoading(true);
    console.log("fileId", fileId);
    setQuestion(values?.question);
    chatDocs({
      fileId: fileId,
      question: values?.question,
    })
      .then((res: any) => {
        if (res.code === 200) {
          message.success(res.message);
        } else {
          message.error(res.message);
        }
      })
      .catch((err: any) => {
        message.error(err.message);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  useEffect(() => {
    console.log("webSocket");
    // 创建WebSocket连接
    var socket = new WebSocket("ws://localhost:8888/api/chat/doc");
    console.log(socket);

    // 连接成功时的回调函数
    socket.onopen = function (event) {
      console.log("WebSocket 连接已建立");
      // 可以在这里发送消息给服务器
      socket.send(JSON.stringify({ message: "Hello, server!" }));
    };

    // 接收到服务器消息时的回调函数
    socket.onmessage = function (event) {
      const jsonData = JSON.parse(event?.data) ? JSON.parse(event?.data) : null;
      console.log("收到消息：", jsonData);
      setAnswer(answer + jsonData?.content);
    };

    // 连接关闭时的回调函数
    socket.onclose = function (event) {
      console.log("WebSocket 连接已关闭");
    };

    // 连接发生错误时的回调函数
    socket.onerror = function (error) {
      console.error("WebSocket 发生错误：", error);
    };
  }, []);

  return (
    <>
      {loading && <FullScreenLoading spinning={loading} tip="加载中" />}
      <Layout
        style={{
          position: "relative",
          width: "100%",
          height: "100%",
          minHeight: "630px",
        }}
      >
        <Content>
          <ProCard title={<h3>上传文档</h3>} style={{ height: "100%" }}>
            <DocsPreviewer setFileId={(fileId: string) => setFileId(fileId)} />
          </ProCard>
        </Content>
        <Sider
          width={400}
          className="site-layout-background"
          style={{
            backgroundImage:
              "linear-gradient(to right, #a8edea 0%, #fed6e3 100%)",
          }}
        >
          <ProCard
            title={<h2>Q&A</h2>}
            style={{
              backgroundImage:
                "linear-gradient(to right, #a8edea 0%, #fed6e3 100%)",
              height: "100%",
            }}
          >
            {answer !== "" && question !== null && (
              <ProCard
                title={question}
                style={{ height: "90%", overflow: "auto" }}
              >
                <Paragraph>{answer}</Paragraph>
              </ProCard>
            )}
            <div
              style={{
                position: "absolute",
                bottom: "10px",
                width: "90%",
                padding: "0 5%",
              }}
            >
              <Form form={form} onFinish={onFinish}>
                <Form.Item label="问题" name="question">
                  <Input
                    placeholder="请上传文档后进行问答"
                    value={question}
                    onChange={(e) => setQuestion(e.target.value)}
                    style={{ height: "40px" }}
                    suffix={
                      <Form.Item style={{ height: "15px" }}>
                        <Button
                          type="primary"
                          icon={<RightOutlined />}
                          style={{
                            height: "30px",
                            margin: "5px 0",
                            marginLeft: 8,
                            border: "1px solid #ccc",
                          }}
                          htmlType="submit"
                        ></Button>
                      </Form.Item>
                    }
                  />
                </Form.Item>
              </Form>
            </div>
          </ProCard>
        </Sider>
      </Layout>
    </>
  );
};

export default DocumentQAPanel;
