import { IDocs } from "../../components/Docs/type";
import { get, post } from "../config/config";

// 首先通过upload上传文件后建立连接
export const uploadDocs = (formData: FormData) => {
  return post("student/spark/chat/doc/upload", formData);
};

export const chatDocs = ({
  fileId,
  question,
}: {
  fileId: string;
  question: string;
}) => {
  return post("student/spark/chat/doc/chat", {
    fileId,
    question,
  });
};

export const rewriteDocs = ({
  level,
  text,
}: {
  level: number;
  text: string;
}) => {
  return post("student/spark/rewrite/generate", {
    level,
    text,
  });
};

export const pptByDocs = (formData: FormData) => {
  return post("student/spark/ppt/doc/ppt", formData);
};

// 获取主题列表
export const getThemeList = () => {
  return get("student/spark/ppt/themeList");
};

// 获取进度条
export const getProcess = (sid: string) => {
  return get(`/student/spark/ppt/ppt/progress/${sid}`);
};
