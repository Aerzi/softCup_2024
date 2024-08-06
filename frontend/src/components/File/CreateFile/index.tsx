import React from "react";
import { InboxOutlined } from "@ant-design/icons";
import { message, Upload, UploadProps } from "antd";
import { getLocalData } from "../../../utils/Storage";
import { createFile } from "../../../services/fileService";

const { Dragger } = Upload;

const uploadFile = ({ classId }: { classId: number }) => {
  // 上传参数
  const props: UploadProps = {
    name: "file",
    multiple: true,
    accept:
      ".txt,.pdf,.doc,.docx,.jpg,.jpeg,.png,.gif,.mp4,.avi,.c,.cpp,.java,.py,.js,.ts",
    action: "http://localhost:8080/api/teacher/upload/file",
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
        formData.append("description", file.name);
        formData.append("classId", classId.toString());
        createFile(formData)
          .then((res: any) => {
            if (res.code === 200) {
              message.success("文件处理成功");
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
    <div>
      <Dragger {...props}>
        <p className="ant-upload-drag-icon">
          <InboxOutlined />
        </p>
        <p className="ant-upload-text">点击或者拖拽文件到此区域</p>
        <p className="ant-upload-hint">支持单个或批量上传</p>
      </Dragger>
    </div>
  );
};

export default uploadFile;
