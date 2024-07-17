import request from "../http/http";

// 统一接口

/**
 * 统一登录接口 -- 登录后开放用户权限，允许访问所有网页
 * @param userName 
 * @param password 
 * @returns 
 */
export const onLogin = (userName: string, password: string) => {
    return request({
        url: 'user/login',
        method: 'post',
        data: {
            userName: userName,
            password: password
        },
        headers:{}
    })
}

/**
 * 统一注销接口 -- 注销后退出系统
 * @returns 
 */
export const onLogout = () => {
    return request({
        url: 'user/logout',
        method: 'get',
        headers:{}
    })
}

// 学生相关
/**
 * 学生注册接口
 * @param userName 
 * @param password 
 * @returns 
 */
export const onStudentRegister = (userName: string, password: string) => {
    return request({
        url: 'student/user/register',
        method: 'post',
        data: {
            userName: userName,
            password: password
        },
        headers:{}
    })
}

// 教师相关
/**
 * 教师注册接口
 * @param userName 
 * @param password 
 * @returns 
 */
export const onTeacherRegister = (userName: string, password: string) => {
    return request({
        url: 'teacher/user/register',
        method: 'post',
        data: {
            userName: userName,
            password: password
        },
        headers:{}
    })
}