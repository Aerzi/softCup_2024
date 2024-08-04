import React, { useState } from "react";
import { Layout, Form, Input, Button, Modal, Typography } from "antd";
import { RightOutlined } from "@ant-design/icons";
import { ProCard } from "@ant-design/pro-components";
import DocsPreviewer from "../DocsPreviewer";
const { TextArea } = Input;
const { Text } = Typography;
const { Sider, Content } = Layout;

const DocumentQAPanel: React.FC = () => {
  const [visible, setVisible] = useState(false);
  const [question, setQuestion] = useState("");
  const [answer, setAnswer] = useState("");

  const showModal = () => {
    setVisible(true);
  };

  const handleOk = () => {
    setVisible(false);
  };

  const handleCancel = () => {
    setVisible(false);
  };

  const handleDocumentChange = (info: any) => {
    if (info.file.status === "done") {
      // 处理文档上传成功的逻辑
      console.log("文件上传成功");
    }
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    // 处理提问的逻辑
    console.log("提问:", question);
    // 假设我们从某个API获取了答案
    setAnswer("这里是根据文档和问题生成的答案。");
    showModal();
  };

  return (
    <>
      <Layout
        style={{
          position: "relative",
          width: "100%",
          height: "100%",
          minHeight: "630px",
        }}
      >
        <Content style={{ margin: "24px 16px 0" }}>
          <div style={{ padding: 24, minHeight: 360 }}>
            <h2>上传文档</h2>
            <DocsPreviewer />
          </div>
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
            <div
              style={{
                position: "absolute",
                bottom: "10px",
                width: "90%",
                padding: "0 5%",
              }}
            >
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
                        onClick={showModal}
                        icon={<RightOutlined />}
                        style={{
                          height: "30px",
                          margin: "5px 0",
                          marginLeft: 8,
                          border: "1px solid #ccc",
                        }}
                      ></Button>
                    </Form.Item>
                  }
                />
              </Form.Item>
            </div>
          </ProCard>
        </Sider>

        <Modal
          title="答案"
          visible={visible}
          onOk={handleOk}
          onCancel={handleCancel}
        >
          <Text>{answer}</Text>
        </Modal>
      </Layout>
    </>
  );
};

export default DocumentQAPanel;
