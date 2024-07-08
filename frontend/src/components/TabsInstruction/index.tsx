import React from "react";
import "./index.less";
import { Button } from "antd";
import TabsContentBox from "../TabsContentBox";

const TabsInstruction = () => {
  return (
    <>
      <div className="xf-tabs-instruction">
        <div className="xf-tabs-instruction__button-list">
          <div className="xf-tabs-instruction__button-item">
            <span className="xf-tabs-instruction__button-item-text">
              学习规划
            </span>
          </div>
          <div className="xf-tabs-instruction__button-item">
            <span className="xf-tabs-instruction__button-item-text">
              教学课堂
            </span>
          </div>
          <div className="xf-tabs-instruction__button-item">
            <span className="xf-tabs-instruction__button-item-text">
              模拟竞赛
            </span>
          </div>
        </div>
      </div>
      <div className="xf-tabs-instruction__content">
        <TabsContentBox />
      </div>
    </>
  );
};

export default TabsInstruction;
