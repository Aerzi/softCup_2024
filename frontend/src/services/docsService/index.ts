import { post } from "../config/config";

// 首先通过upload上传文件后建立连接
export const uploadDocs = (formData: FormData) => {
  return post("student/spark/chat/doc/upload", formData);
};

export const chatDocs = ({
  fileId,
  question,
}: {
  fileId: number;
  question: string;
}) => {
  return post("student/spark/chat/doc/chat", {
    fileId,
    question,
  });
};
