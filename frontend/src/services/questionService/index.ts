import { IQuestion } from "../../types/questionType";
import { getLocalData } from "../../utils/Storage";
import request from "../http/http";

// 教师端
/**
 * 创建题目
 * @param data
 * @returns
 */
export const addTeacherQuestion = (data: IQuestion) => {
  return request("post", "teacher/question/add", data, {
    headers: {
      Authorization:
        getLocalData("token") !== null ? getLocalData("token") : "",
    },
  });
};

/**
 * 获取题目列表
 * @param pageIndex
 * @param pageSize
 * @returns
 */
export const getPageTeacherQuestion = (pageIndex: number, pageSize: number) => {
  return request(
    "post",
    "teacher/question/page",
    {
      pageIndex: pageIndex,
      pageSize: pageSize,
    },
    {
      headers: {
        Authorization:
          getLocalData("token") !== null ? getLocalData("token") : "",
      },
    }
  );
};
