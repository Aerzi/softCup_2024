import React, { useEffect, useState } from "react";
import { Button, Layout, message, Typography, UploadProps } from "antd";
import { InboxOutlined } from "@ant-design/icons";
import { ProCard } from "@ant-design/pro-components";
import { translateDocs } from "../../../../services/docsService";
import FullScreenLoading from "../../../Other/Loading";
import Dragger from "antd/es/upload/Dragger";
import { getLocalData } from "../../../../utils/Storage";
const { Sider, Content } = Layout;

const { Paragraph } = Typography;

interface ITheme {
  key: string;
  name: string;
  thumbnail: string;
}

function downloadPPTX(fileUrl: string | URL | Request) {
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
      a.download = "presentation.pptx"; // 指定下载文件的名称
      document.body.appendChild(a);
      a.click();
      window.URL.revokeObjectURL(url);
    })
    .catch((e) => console.error(e));
}

const DocsTranslater = () => {
  const [loading, setLoading] = useState(false);
  const [texts, setTexts] = useState<[]>(null);
  const [url, setUrl] = useState<string>(null);

  // 上传参数
  const props: UploadProps = {
    name: "file",
    multiple: true,
    accept: ".txt,.pdf,.doc,.docx,",
    action: "http://localhost:8080/api/student/spark/ppt/doc/ppt",
    headers: {
      "Content-Type": "multipart/form-data",
      Authorization:
        getLocalData("token") !== null ? getLocalData("token") : "",
    },
    onChange(info) {
      const { status } = info.file;
      if (status !== "uploading") {
        console.log(info.file, info.fileList);
      }
      if (status === "done") {
        const { file } = info;
        const formData = new FormData();
        formData?.append("file", file.originFileObj);
        setLoading(true);
        translateDocs(formData)
          .then((res: any) => {
            if (res.code === 200) {
              console.log("data", res.response?.texts, res.response?.url);
              setTexts(res.response?.texts);
              setUrl(res.response?.url);
            }
          })
          .catch((err) => {
            message.error(err.message);
          })
          .finally(() => {
            setLoading(false);
          });
      } else if (status === "error") {
        message.error(`${info.file.name} file upload failed.`);
      }
    },
    onDrop(e) {
      console.log("Dropped files", e.dataTransfer.files);
    },
  };

  return (
    <>
      {loading && <FullScreenLoading spinning={loading} tip={"正在生成中"} />}
      <Layout
        style={{
          position: "relative",
          width: "100%",
          height: "100%",
          minHeight: "630px",
          maxHeight: "120vh",
        }}
      >
        <Content>
          <ProCard title={<h3>上传文档</h3>} style={{ height: "100%" }}>
            <Dragger {...props}>
              <p className="ant-upload-drag-icon">
                <InboxOutlined />
              </p>
              <p className="ant-upload-text">点击或者拖拽文件到此区域</p>
            </Dragger>
          </ProCard>
        </Content>
        <Sider
          width={url ? 800 : 400}
          className="site-layout-background"
          style={{
            backgroundImage:
              "linear-gradient(to right, #a8edea 0%, #fed6e3 100%)",
          }}
        >
          <ProCard
            title={
              <h3 style={{ fontFamily: "阿里妈妈数黑体 Bold" }}>翻译结果</h3>
            }
            style={{
              backgroundImage:
                "linear-gradient(to right, #a8edea 0%, #fed6e3 100%)",
              height: "100%",
              overflow: "auto",
              padding: "10px",
            }}
            extra={
              <Button
                type="primary"
                onClick={() => downloadPPTX(url)}
                style={{
                  backgroundImage: "linear-gradient(135deg, #9333EA, #F50057);",
                }}
                disabled={!url}
              >
                下载文件
              </Button>
            }
          >
            <Paragraph>{texts?.toString()}</Paragraph>
          </ProCard>
        </Sider>
      </Layout>
    </>
  );
};

export default DocsTranslater;
