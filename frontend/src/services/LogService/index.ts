import { del, post } from "../config/config";

export const getLogPage = ({
  pageIndex,
  pageSize,
}: {
  pageIndex: number;
  pageSize: number;
}) => {
  return post("admin/event/log/page", {
    pageIndex,
    pageSize,
  });
};

// 删除
export const delLog = (id: number) => {
  return del(`admin/event/log/delete/${id}`);
};
