import { IQuestion } from "../../components/Question/type";
import { del, get, post, put } from "../config/config";

// 教师端
// 创建题目
export const addQuestion = (data: IQuestion) => {
  return post("teacher/question/add", data);
};

// 编辑题目
export const editQuestion = (data: IQuestion) => {
  return put("teacher/question/edit", data);
};

// 删除题目
export const deleteQuestion = (id: number) => {
  return del(`teacher/question/delete/${id}`);
};

// 教师端获取题目列表
export const getQuestionPage = ({
  pageIndex,
  pageSize,
  classId,
}: {
  pageIndex: number;
  pageSize?: number;
  classId: number;
}) => {
  return post("teacher/question/page", {
    pageIndex: pageIndex,
    pageSize: pageSize,
    classId: classId,
  });
};

export const onGetClassList = () => {
  return get("teacher/question/list");
};

// 学生端
export const StudentGetQuestionPage = ({
  pageIndex,
  pageSize,
  classId,
}: {
  pageIndex: number;
  pageSize: number;
  classId: number;
}) => {
  return post("student/question/page", {
    pageIndex,
    pageSize,
    classId,
  });
};
