import React from "react";
import { InboxOutlined } from "@ant-design/icons";
import { message, Upload, Button, UploadProps } from "antd";
import { createFile } from "../../../services/fileService";

const { Dragger } = Upload;

const uploadFile = ({ classId }: { classId: number }) => {
  // 上传参数
  const props: UploadProps = {
    name: "file",
    multiple: true,
    action: "http://localhost:8080/api/teacher/upload/file", // 替换成你的上传API地址
    onChange(info) {
      const { status } = info.file;
      if (status !== "uploading") {
        console.log(info.file, info.fileList);
      }
      if (status === "done") {
        // 调用你的createFile函数
        const { file } = info;
        const formData = new FormData();
        formData.append("file", file.originFileObj); // 将文件添加到FormData中
        createFile({
          file: formData,
          description: "description",
          classId: classId,
        })
          .then((res: any) => {
            if (res.code === 200) {
              message.success(`${file.name} file uploaded successfully.`);
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
  const handleUpload = () => {
    // 这里可以添加上传逻辑，例如触发文件选择或者直接上传
  };
  return (
    <div>
      <Dragger {...props}>
        <p className="ant-upload-drag-icon">
          <InboxOutlined />
        </p>
        <p className="ant-upload-text">
          Click or drag file to this area to upload
        </p>
        <p className="ant-upload-hint">
          Support for a single or bulk upload. Strictly prohibited from
          uploading company data or other banned files.
        </p>
      </Dragger>
      <Button type="primary" onClick={handleUpload} style={{ marginTop: 16 }}>
        Upload Files
      </Button>
    </div>
  );
};

export default uploadFile;
