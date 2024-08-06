import React, { useState } from "react";
import { Layout, Form, Input, Button, Image, message, Space } from "antd";
import { ProCard } from "@ant-design/pro-components";
import { generateImage } from "../../services/imageService";
import FullScreenLoading from "../Other/Loading";
import {
  DownloadOutlined,
  RotateLeftOutlined,
  RotateRightOutlined,
  SwapOutlined,
  UndoOutlined,
  ZoomInOutlined,
  ZoomOutOutlined,
} from "@ant-design/icons";
const { Sider, Content } = Layout;

const ImageGenerator: React.FC = () => {
  const [form] = Form.useForm();
  const [url, setUrl] = useState<string>(null);
  const [loading, setLoading] = useState(false);

  const onFinish = (values: any) => {
    setLoading(true);
    // 这里可以添加生成图片的逻辑
    generateImage(values)
      .then((res: any) => {
        if (res.code === 200) {
          setUrl(res.response);
        } else {
          message.error(res.message);
        }
      })
      .catch((err) => {
        message.error(err.message);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  // 下载
  const onDownload = (imgUrl: string) => {
    fetch(imgUrl)
      .then((response) => response.blob())
      .then((blob) => {
        const url = URL.createObjectURL(new Blob([blob]));
        const link = document.createElement<"a">("a");
        link.href = url;
        link.download = "image.png";
        document.body.appendChild(link);
        link.click();
        URL.revokeObjectURL(url);
        link.remove();
      });
  };

  return (
    <>
      {loading && <FullScreenLoading spinning={loading} tip={"生成中..."} />}
      <div
        style={{
          position: "relative",
          width: "100%",
          height: "100%",
          minHeight: "630px",
          backgroundImage:
            "linear-gradient(to right, #a8edea 0%, #fed6e3 100%)",
        }}
      >
        <Layout
          style={{
            position: "relative",
            width: "100%",
            height: "100%",
            minHeight: "630px",
          }}
        >
          <Content
            style={{
              margin: "24px 16px 0",
            }}
          >
            {/* 这里是待生成图片的区域 */}
            <div style={{ textAlign: "center" }}>
              {url === null && <h1>待生成图片</h1>}
              {url && (
                <Image
                  width={"100%"}
                  src={url}
                  preview={{
                    toolbarRender: (
                      _,
                      {
                        image: { url },
                        transform: { scale },
                        actions: {
                          onFlipY,
                          onFlipX,
                          onRotateLeft,
                          onRotateRight,
                          onZoomOut,
                          onZoomIn,
                          onReset,
                        },
                      }
                    ) => (
                      <Space size={12} className="toolbar-wrapper">
                        <DownloadOutlined onClick={() => onDownload(url)} />
                        <SwapOutlined rotate={90} onClick={onFlipY} />
                        <SwapOutlined onClick={onFlipX} />
                        <RotateLeftOutlined onClick={onRotateLeft} />
                        <RotateRightOutlined onClick={onRotateRight} />
                        <ZoomOutOutlined
                          disabled={scale === 1}
                          onClick={onZoomOut}
                        />
                        <ZoomInOutlined
                          disabled={scale === 50}
                          onClick={onZoomIn}
                        />
                        <UndoOutlined onClick={onReset} />
                      </Space>
                    ),
                  }}
                />
              )}
            </div>
          </Content>
          <Sider
            width={300}
            className="site-layout"
            style={{
              backgroundImage:
                "linear-gradient(to right, #a8edea 0%, #fed6e3 100%)",
            }}
          >
            <div
              className="site-layout-background"
              style={{
                padding: 24,
                backgroundColor: "#fff",
                width: "100%",
                height: "100%",
                backgroundImage:
                  "linear-gradient(to right, #a8edea 0%, #fed6e3 100%)",
              }}
            >
              <ProCard
                title={<h3>图片生成要求</h3>}
                style={{
                  backgroundImage:
                    "linear-gradient(to right, #a8edea 0%, #fed6e3 100%)",
                }}
              >
                <Form form={form} layout="vertical" onFinish={onFinish}>
                  <Form.Item
                    name="width"
                    label="宽度"
                    rules={[{ required: true, message: "请输入宽度!" }]}
                  >
                    <Input placeholder="请输入宽度" />
                  </Form.Item>
                  <Form.Item
                    name="height"
                    label="高度"
                    rules={[{ required: true, message: "请输入高度!" }]}
                  >
                    <Input placeholder="请输入高度" />
                  </Form.Item>
                  <Form.Item
                    name="content"
                    label="具体要求"
                    rules={[{ required: true, message: "请输入具体要求!" }]}
                  >
                    <Input.TextArea placeholder="请输入具体要求" />
                  </Form.Item>
                  <Form.Item
                    style={{ textAlign: "center", padding: "10px 10%" }}
                  >
                    <Button
                      type="primary"
                      htmlType="submit"
                      style={{
                        width: "100%",
                        background:
                          "linear-gradient(to right, #1e3799 0%, #2a52be 50%, #3d84e6 100%)",
                      }}
                    >
                      生成
                    </Button>
                  </Form.Item>
                </Form>
              </ProCard>
            </div>
          </Sider>
        </Layout>
      </div>
    </>
  );
};

export default ImageGenerator;
