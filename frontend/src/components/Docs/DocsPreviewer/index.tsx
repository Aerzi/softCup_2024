import React, { useState } from "react";
import { Typography, Space, Button, message, UploadProps } from "antd";
import { IFile } from "../../File/type";
import { ProCard } from "@ant-design/pro-components";
import Dragger from "antd/es/upload/Dragger";
import { InboxOutlined } from "@ant-design/icons";
import { getLocalData } from "../../../utils/Storage";
import { uploadDocs } from "../../../services/docsService";
import FullScreenLoading from "../../Other/Loading";
const { Text } = Typography;

const DocsPreviewer = ({ setFileId }: { setFileId: any }) => {
  const [file, setFile] = useState<IFile | null>(null);
  const [visible, setVisible] = useState(false);
  const [loading, setLoading] = useState(false);

  const preview = () => {
    switch (file?.type) {
      case "pdf":
        return (
          <embed
            src={file?.filePath}
            type="application/pdf"
            width="100%"
            height="600px"
            style={{ margin: "0 auto" }}
          />
        );
      default:
        return (
          <div>
            <Text>文件类型不支持直接预览</Text>
            <Button href={file?.filePath} target="_blank" download>
              下载文件
            </Button>
          </div>
        );
    }
  };

  // 上传参数
  const props: UploadProps = {
    name: "file",
    multiple: true,
    accept: ".txt,.pdf,.doc,.docx,.c,.cpp,.java,.py,.js,.ts",
    action: "http://localhost:8080/api/student/spark/chat/doc/upload",
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
        formData.append("file", file.originFileObj);
        setLoading(true);
        uploadDocs(formData)
          .then((res: any) => {
            if (res.code === 200) {
              message.success("文件处理成功");
              setVisible(true);
              setFile({
                type: res.response?.type,
                filePath: res.response?.url,
              });
              setFileId(res.response?.response?.data?.fileId);
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
      {loading && <FullScreenLoading spinning={loading} tip={"加载中..."} />}
      {!visible && (
        <Dragger {...props}>
          <p className="ant-upload-drag-icon">
            <InboxOutlined />
          </p>
          <p className="ant-upload-text">点击或者拖拽文件到此区域</p>
        </Dragger>
      )}
      {visible && (
        <ProCard style={{ width: "100%", height: "100%", margin: "0 auto" }}>
          <Space
            direction="vertical"
            style={{
              width: "100%",
              height: "100%",
              minHeight: "630px",
              overflow: "auto",
            }}
          >
            {preview()}
          </Space>
        </ProCard>
      )}
    </>
  );
};

export default DocsPreviewer;
