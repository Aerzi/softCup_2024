import axios, { AxiosRequestConfig } from "axios";
import { getLocalData, setLocalData } from "../../utils/Storage";
import { message } from "antd";

// 创建一个 axios 实例
const instance = axios.create({
  baseURL: "http://localhost:8080/api",
  timeout: 10000, // 请求超时时间
});

// 请求拦截器
instance.interceptors.request.use((config) => {
  // 在发送请求之前做些什么
  config.headers.token = getLocalData("token") || "";
  return config;
});

// 响应拦截器
instance.interceptors.response.use(
  (response) => {
    // 保存响应标头中的token
    response.headers.Authorization &&
      setLocalData("token", response.headers.Authorization);
    return response.data;
  },
  (error) => {
    if (error.response) {
      // 检查HTTP状态码
      const { status, data } = error.response;

      // 如果状态码是403，但业务状态码是200，则视为成功响应
      if (status === 403 && data.code === 200) {
        console.log("特殊情况的成功响应");
        return data; // 直接返回数据
      }

      // 其他状态码的错误处理
      message.error(`服务器错误 ${status}: ${data.message || "未知错误"}`);
    } else if (error.request) {
      // 请求已发出但没有收到响应
      message.error("请求发出但没有收到响应");
    } else {
      // 发生了触发请求错误的问题
      message.error("请求错误: " + error.message);
    }
    return Promise.reject(error);
  }
);

// 封装不同的请求方法
const request = (
  method: string,
  url: string,
  data: any,
  config: AxiosRequestConfig<any> | undefined
) => {
  switch (method.toLowerCase()) {
    case "get":
      return instance.get(url, config);
    case "post":
      return instance.post(url, data, config);
    case "put":
      return instance.put(url, data, config);
    case "delete":
      return instance.delete(url, config);

    default:
      throw new Error("请求方法不被支持");
  }
};

export default request;
