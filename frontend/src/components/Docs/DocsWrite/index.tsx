import React, { useState } from "react";
import { Layout, Form, Input, Button, Typography, message } from "antd";
import { RightOutlined } from "@ant-design/icons";
import { ProCard } from "@ant-design/pro-components";
import { chatDocs, rewriteDocs } from "../../../services/docsService";
import FullScreenLoading from "../../Other/Loading";
const { TextArea } = Input;
const { Text, Paragraph } = Typography;
const { Sider, Content } = Layout;

const DocumentWriter = () => {
  const [loading, setLoading] = useState(false);

  const [form] = Form.useForm();
  const [answer, setAnswer] = useState<string>(null);

  const onFinish = (values: any) => {
    setLoading(true);
    rewriteDocs({
      level: values?.level,
      text: values?.text,
    })
      .then((res: any) => {
        if (res.code === 200) {
          message.success(res?.message);
          setAnswer(res?.response?.text);
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
          <ProCard title={<h3>生成文字</h3>} style={{ height: "100%" }}>
            {answer && (
              <ProCard
                title={"扩写内容"}
                style={{ height: "90%", overflow: "auto" }}
              >
                <Paragraph>{answer}</Paragraph>
              </ProCard>
            )}
            {!answer && (
              <ProCard style={{ height: "90%", overflow: "auto" }}>
                <Paragraph style={{ color: "#ccc", userSelect: "none" }}>
                  请输入文本
                </Paragraph>
              </ProCard>
            )}
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
            title={<h2>文本扩写</h2>}
            style={{
              backgroundImage:
                "linear-gradient(to right, #a8edea 0%, #fed6e3 100%)",
              height: "100%",
            }}
          >
            <Form form={form} onFinish={onFinish}>
              <Form.Item label="扩写等级" name="level">
                <Input
                  placeholder="请输入扩写等级"
                  style={{ height: "40px" }}
                />
              </Form.Item>
              <Form.Item label="扩写文本" name="text">
                <Input.TextArea
                  placeholder="请输入你的文本"
                  style={{ height: "40px" }}
                />
              </Form.Item>
              <Form.Item style={{ height: "15px" }}>
                <Button
                  type="primary"
                  icon={<RightOutlined />}
                  iconPosition="end"
                  style={{
                    height: "30px",
                    margin: "5px 0",
                    marginLeft: 8,
                    border: "1px solid #ccc",
                    width: "100%",
                  }}
                  htmlType="submit"
                >
                  开始扩写
                </Button>
              </Form.Item>
            </Form>
          </ProCard>
        </Sider>
      </Layout>
    </>
  );
};

export default DocumentWriter;
