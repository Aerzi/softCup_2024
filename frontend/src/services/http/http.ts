import axios, { AxiosRequestConfig } from "axios";
import { getLocalData, setLocalData } from "../../utils/Storage";
import { message } from "antd";
import { status } from "nprogress";

// 创建一个 axios 实例
const instance = axios.create({
  baseURL: "http://localhost:8080/api",
  timeout: 200000, // 请求超时时间
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
    response?.headers?.authorization &&
      setLocalData("token", response.headers.authorization);
    return response.data;
  },
  (error) => {
    if (error.response) {
      // 检查HTTP状态码
      const { status, data } = error.response;
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

export const get = (url: string, data?: any, config?: any) => {
  if (data !== null) {
    // 说明需要对url进行拼接
  }
  return request(
    "get",
    url,
    {},
    {
      headers: {
        Authorization:
          getLocalData("token") !== null ? getLocalData("token") : "",
      },
    }
  );
};

export const post = (url: string, data?: any, config?: any) => {
  return request("post", url, data, {
    ...config,
    headers: {
      Authorization:
        getLocalData("token") !== null ? getLocalData("token") : "",
    },
  });
};

export const put = (url: string, data?: any, config?: any) => {
  return request("put", url, data, {
    ...config,
    headers: {
      Authorization:
        getLocalData("token") !== null ? getLocalData("token") : "",
    },
  });
};

export default request;
