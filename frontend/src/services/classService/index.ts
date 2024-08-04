import { IClassRoom } from "../../components/Class/type";
import { del, get, post, put } from "../config/config";

// 创建课程
export const createClass = (data: IClassRoom) => {
  return post("teacher/class/create", data);
};

// 获取课程列表
export const getClassPage = ({
  pageIndex,
  pageSize,
}: {
  pageIndex: number;
  pageSize: number;
}) => {
  return post("teacher/class/page", {
    pageIndex: pageIndex,
    pageSize: pageSize,
  });
};

// 编辑课程
export const editClass = (data: IClassRoom) => {
  return put("teacher/class/edit", data);
};

// 删除课程
export const deleteClass = (id: number) => {
  return del(`teacher/class/delete/${id}`);
};

export const getClassList = () => {
  return get("teacher/class/list");
};
