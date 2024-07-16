import React, { useState } from "react";
import "./index.less";
import TablePro from "../TablePro";
import { Button } from "antd";
import TreeChart from "../TreeChart";

const LinkLoader = () => {
  const [isLinkLoading, setIsLinkLoading] = useState(false);
  return (
    <div className="xf-table">
      <div className="xf-table__header">
        <Button onClick={() => setIsLinkLoading(!isLinkLoading)}>思维链</Button>
      </div>
      {isLinkLoading ? (
        <div className="xf-table__content">
          <TreeChart />
        </div>
      ) : (
        <TablePro />
      )}
    </div>
  );
};

export default LinkLoader;
