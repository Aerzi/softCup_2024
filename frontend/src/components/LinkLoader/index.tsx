import React from "react";
import "./index.less";
import { Button } from "antd";

const LinkLoader = ({
  isLinkLoading,
  setIsLinkLoading,
}: {
  isLinkLoading: boolean;
  setIsLinkLoading: (arg0: boolean) => void;
}) => {
  return (
    <div className="xf-table">
      <div className="xf-table__header">
        <Button type="primary" onClick={() => setIsLinkLoading(!isLinkLoading)}>
          {isLinkLoading ? "返回项目开发" : "启用思维链"}
        </Button>
      </div>
    </div>
  );
};

export default LinkLoader;
