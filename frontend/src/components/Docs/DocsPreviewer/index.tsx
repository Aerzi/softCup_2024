import React, { useState } from "react";
import { Typography, Space, Button, message, Modal, UploadProps } from "antd";
import { IFile } from "../../File/type";
import { ProCard } from "@ant-design/pro-components";
import Dragger from "antd/es/upload/Dragger";
import { InboxOutlined } from "@ant-design/icons";
import { getLocalData } from "../../../utils/Storage";
import { uploadDocs } from "../../../services/docsService";
import DocViewer from "@cyntler/react-doc-viewer";
const { Text } = Typography;

const DocsPreviewer = () => {
  const [file, setFile] = useState<IFile | null>(null);
  const [visible, setVisible] = useState(false);

  const preview = () => {
    switch (file?.type) {
      case "DOC":
        return <DocViewer documents={[{ uri: file?.filePath }]} />;
      case "PDF":
        return (
          <embed
            src={file?.filePath}
            type="application/pdf"
            width="1200px"
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
        uploadDocs(formData)
          .then((res: any) => {
            if (res.code === 200) {
              message.success("文件处理成功");
              setVisible(true);
              setFile(res.data);
            } else {
              message.error(res.message);
            }
          })
          .catch((err) => {
            message.error(err.message);
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
      {!visible && (
        <Dragger {...props}>
          <p className="ant-upload-drag-icon">
            <InboxOutlined />
          </p>
          <p className="ant-upload-text">点击或者拖拽文件到此区域</p>
          <p className="ant-upload-hint">支持单个或批量上传</p>
        </Dragger>
      )}
      {visible && (
        <ProCard style={{ width: "100%", height: "100%", margin: "0 auto" }}>
          <Space direction="vertical">{preview()}</Space>
        </ProCard>
      )}
    </>
  );
};

export default DocsPreviewer;
