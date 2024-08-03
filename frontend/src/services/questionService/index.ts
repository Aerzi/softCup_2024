import { getLocalData } from "../../utils/Storage";
import { get, post } from "../config/config";

export interface IQuestion {
  name: string;
  type: string;
  description?: string;
  content: string;
  score?: number;
  difficult?: number;
  correct: string;
  createTime?: string;
  modifyTime?: string;
  classId?: number;
}

// 教师端
// 创建题目
export const addTeacherQuestion = (data: IQuestion) => {
  return post("teacher/question/add", data);
};

// 教师端获取题目列表
export const getPageTeacherQuestion = ({
  pageIndex,
  pageSize,
  classId,
}: {
  pageIndex: number;
  pageSize: number;
  classId: number;
}) => {
  return post("teacher/question/page", {
    pageIndex: pageIndex,
    pageSize: pageSize,
    classId: classId,
  });
};

export const onGetTeacherClassList = () => {
  return get("teacher/question/list");
};

export const onStudentGetQuestionPage = ({
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
