import { IUser } from "../../components/Admin/type";
import { post, put } from "../config/config";

export const getUserPage = ({
  pageIndex,
  pageSize,
}: {
  pageIndex: number;
  pageSize: number;
}) => {
  return post("admin/user/page", {
    pageIndex,
    pageSize,
  });
};

export const addUser = (data: IUser) => {
  return post("admin/user/addUser", data);
};

export const editUser = (data: IUser) => {
  return put("admin/user/editUser", data);
};
