import { Token } from "monaco-editor";
import request from "../http/http";
import { getLocalData } from "../../utils/Storage";

// 统一接口

/**
 * 统一登录接口 -- 登录后开放用户权限，允许访问所有网页
 * @param userName
 * @param password
 * @returns
 */
export const onLogin = (userName: string, password: string) => {
  return request(
    "post",
    "user/login",
    {
      userName: userName,
      password: password,
    },
    {}
  );
};

/**
 * 统一注销接口 -- 注销后退出系统
 * @returns
 */
export const onLogout = () => {
  return request(
    "get",
    "user/logout",
    {},
    {
      headers: {
        token: getLocalData("token") !== null ? getLocalData("token") : "",
      },
    }
  );
};

// 学生相关
/**
 * 学生注册接口
 * @param userName
 * @param password
 * @returns
 */
export const onStudentRegister = (userName: string, password: string) => {
  return request(
    "post",
    "student/user/register",
    {
      userName: userName,
      password: password,
    },
    {}
  );
};

// 教师相关
/**
 * 教师注册接口
 * @param userName
 * @param password
 * @returns
 */
export const onTeacherRegister = (userName: string, password: string) => {
  return request(
    "post",
    "teacher/user/register",
    {
      userName: userName,
      password: password,
    },
    {}
  );
};
