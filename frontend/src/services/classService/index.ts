import { IClassStateType, IClassType } from "../../types/ClassType";
import { getLocalData } from "../../utils/Storage";
import request from "../config/config";

// 教师端
/**
 * 创建课程
 * @param data
 * @returns
 */
export const onCreateClass = (data: IClassType) => {
  return request("post", "teacher/class/create", data, {
    headers: {
      Authorization:
        getLocalData("token") !== null ? getLocalData("token") : "",
    },
  });
};

/**
 * 获取课程列表
 * @param pageIndex
 * @param pageSize
 * @returns
 */
export const onGetPageClass = (pageIndex: number, pageSize: number) => {
  return request(
    "post",
    "teacher/class/page",
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

/**
 * 编辑课程
 * @param data
 * @returns
 */
export const onEditClass = (data: IClassStateType) => {
  return request("put", "teacher/class/edit", data, {
    headers: {
      Authorization:
        getLocalData("token") !== null ? getLocalData("token") : "",
    },
  });
};

/**
 * 删除课程
 * @param data
 * @returns
 */
export const onDeleteClass = (id: number) => {
  return request(
    "delete",
    `teacher/class/delete/${id}`,
    {},
    {
      headers: {
        Authorization:
          getLocalData("token") !== null ? getLocalData("token") : "",
      },
    }
  );
};

export const onGetTeacherClassList = () => {
  return request(
    "get",
    "teacher/class/list",
    {},
    {
      headers: {
        Authorization:
          getLocalData("token") !== null ? getLocalData("token") : "",
      },
    }
  );
};

// 学生加入课堂
