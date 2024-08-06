import React from "react";
import { Spin } from "antd";
import "./index.less"; // 引入相应的样式文件

const FullScreenLoading = ({
  spinning,
  tip = "加载中...",
}: {
  spinning: boolean;
  tip?: string;
}) => {
  return (
    <div
      className="full-screen-loading"
      style={{ display: spinning ? "block" : "none" }}
    >
      <Spin tip={tip} />
    </div>
  );
};

export default FullScreenLoading;
