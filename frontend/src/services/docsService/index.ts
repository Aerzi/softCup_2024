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

// 教师端
// 获取主题列表
export const getTeacherThemeList = () => {
  return get("teacher/spark/ppt/themeList");
};

// 生成备课大纲
export const generateOutline = (data: IDocs) => {
  return post("teacher/spark/ppt/outline", data);
};

// 生成PPT
export const generatePPTByTeacher = (sid: string) => {
  return post("teacher/spark/ppt/ppt", {
    sid: sid,
  });
};

// 教师端获取进度
export const getTeacherProcess = (sid: string) => {
  return get(`/teacher/spark/ppt/ppt/progress/${sid}`);
};

// 教师端文档翻译
export const translateDocs = (formData: FormData) => {
  return post("teacher/spark/translation/doc/translate", formData);
};
