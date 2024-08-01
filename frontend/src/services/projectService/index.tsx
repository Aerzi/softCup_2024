import { getLocalData } from "../../utils/Storage";
import request from "../http/http";

export const onAddTeacherProject = (data: any) => {
  return request("post", "teacher/project/add", data, {
    headers: {
      Authorization:
        getLocalData("token") !== null ? getLocalData("token") : "",
    },
  });
};

export const onGetProjectPage = (data: any) => {
  return request("post", "teacher/project/page", data, {
    headers: {
      Authorization:
        getLocalData("token") !== null ? getLocalData("token") : "",
    },
  });
};
