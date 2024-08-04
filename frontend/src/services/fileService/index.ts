import { IFile } from "../../components/File/type";
import { post } from "../config/config";

export const createFile = (data: IFile) => {
  return post("teacher/upload/file", data, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};
