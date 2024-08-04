import { IFile } from "../../components/File/type";
import { del, get, post, put } from "../config/config";

export const createFile = (formData: FormData) => {
  return post("teacher/upload/file", formData);
};

// 请求filePage
export const getFilePage = ({
  pageIndex,
  pageSize,
  classId,
}: {
  pageIndex: number;
  pageSize: number;
  classId: number;
}) => {
  return post("teacher/file/page", {
    pageIndex,
    pageSize,
    classId,
  });
};

// 编辑file
export const editFile = (formData: IFile) => {
  return put("teacher/file/edit", formData);
};

// 删除file
export const deleteFile = (id: number) => {
  return del(`teacher/file/delete/${id}`);
};

// 学生端
export const getStudentFilePage = ({
  pageIndex,
  pageSize,
  classId,
}: {
  pageIndex: number;
  pageSize: number;
  classId: number;
}) => {
  return post("student/file/page", {
    pageIndex,
    pageSize,
    classId,
  });
};

// 获取文件信息
export const selectById = (id: number) => {
  return get(`student/file/select/${id}`);
};
