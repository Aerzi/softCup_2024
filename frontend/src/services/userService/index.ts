import { get, post, put } from "../config/config";
import { UserInfo } from "../../types/UserType";

// 统一接口

// 统一登录接口 -- 登录后开放用户权限，允许访问所有网页
export const onLogin = (userName: string, password: string) => {
  return post("user/login", {
    userName: userName,
    password: password,
  });
};

// 统一注销接口 -- 注销后退出系统
export const onLogout = () => {
  return get("user/logout");
};

// 学生相关
// 学生注册接口
export const onStudentRegister = (userName: string, password: string) => {
  return post("student/user/register", {
    userName: userName,
    password: password,
  });
};

// 学生获取用户信息
export const onGetStudentInfo = () => {
  return get("student/user/info");
};

// 学生修改信息
export const onEditStudentInfo = (data: UserInfo) => {
  return put("student/user/edit", data);
};

// 教师相关
//教师注册接口
export const onTeacherRegister = (userName: string, password: string) => {
  return post("teacher/user/register", {
    userName: userName,
    password: password,
  });
};

// 教师获取信息
export const onGetTeacherInfo = () => {
  return get("teacher/user/info");
};

// 教师修改信息
export const onEditTeacherInfo = (data: UserInfo) => {
  return put("teacher/user/edit", data);
};
