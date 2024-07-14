import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from "axios";

// 定义接口
interface IRequestConfig extends AxiosRequestConfig {
  // 可以在这里添加额外的配置属性
}

interface IResponse<T = any> {
  data: T;
  status: number;
  statusText: string;
  // 根据实际返回的数据结构添加更多属性
}

interface IError {
  response?: AxiosResponse;
  message: string;
  // 可以根据需要添加更多属性
}

// 创建axios实例
const instance: AxiosInstance = axios.create({
  baseURL: "你的API基础URL",
  timeout: 1000, // 超时时间
});

// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    // 在发送请求之前做些什么，例如设置token
    config.headers.Authorization = `Bearer ${localStorage.getItem("token")}`;
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 响应拦截器
instance.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      // 服务器拒绝了请求
      console.error("服务器拒绝了请求", error.response);
    } else if (error.request) {
      // 服务器没有响应
      console.error("服务器没有响应", error.request);
    } else {
      // 设置请求时出了点问题
      console.error("请求设置时出了问题", error.message);
    }
    return Promise.reject({ message: "网络或服务器错误" });
  }
);

// 封装请求方法
function request<T = any>(config: IRequestConfig): Promise<IResponse<T>> {
  return new Promise((resolve, reject) => {
    instance(config)
      .then((response: AxiosResponse<T>) => {
        resolve({
          data: response.data,
          status: response.status,
          statusText: response.statusText,
        });
      })
      .catch((error: any) => {
        reject({
          message: error.message || "请求失败",
        });
      });
  });
}

// 导出封装好的axios实例
export default request;

// 使用示例
// import http from './http';
// http({ url: '/user', method: 'get' }).then(response => {
//   console.log(response.data);
// }).catch(error => {
//   console.error(error);
// });
