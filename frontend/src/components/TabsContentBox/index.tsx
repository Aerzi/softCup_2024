import React from "react";
import "./index.less";
import { CheckOutlined } from "@ant-design/icons";
import studyPlanPng from "../../assets/images/studyPlan.png";

interface TabsContentData {
  [key: number]: JSX.Element;
}

const TabsContentBox = ({ activeKey }: { activeKey: number }) => {
  const contentMap = {
    0: (
      <>
        <div className="xf-tabs-content-box__left">
          <h3 className="xf-tabs-content-box__left-title">学习规划特点</h3>
          <p className="xf-tabs-content-box__left-text">
            <CheckOutlined style={{ color: "rgb(96,190,158)" }} /> 特点1
          </p>
          <p className="xf-tabs-content-box__left-text">
            <CheckOutlined style={{ color: "rgb(96,190,158)" }} /> 特点2
          </p>
          <p className="xf-tabs-content-box__left-text">
            <CheckOutlined style={{ color: "rgb(96,190,158)" }} /> 特点3
          </p>
          <p className="xf-tabs-content-box__left-text">
            <CheckOutlined style={{ color: "rgb(96,190,158)" }} /> 特点4
          </p>
          <p className="xf-tabs-content-box__left-textarea">
            简述学生可以在此实现何种功能
          </p>
        </div>
        <div className="xf-tabs-content-box__right">
          <div className="xf-tabs-content-box__right-content">
            <img src={studyPlanPng} alt="" />
          </div>
        </div>
      </>
    ),
    1: (
      <>
        <div className="xf-tabs-content-box__right">
          <div className="xf-tabs-content-box__right-content"></div>
        </div>
        <div className="xf-tabs-content-box__left">
          <h3 className="xf-tabs-content-box__left-title">教学课堂特点</h3>
          <p className="xf-tabs-content-box__left-text">
            <CheckOutlined style={{ color: "rgb(96,190,158)" }} /> 特点1
          </p>
          <p className="xf-tabs-content-box__left-text">
            <CheckOutlined style={{ color: "rgb(96,190,158)" }} /> 特点2
          </p>
          <p className="xf-tabs-content-box__left-text">
            <CheckOutlined style={{ color: "rgb(96,190,158)" }} /> 特点3
          </p>
          <p className="xf-tabs-content-box__left-text">
            <CheckOutlined style={{ color: "rgb(96,190,158)" }} /> 特点4
          </p>
          <p className="xf-tabs-content-box__left-textarea">
            简述学生可以在此实现何种功能
          </p>
        </div>
      </>
    ),
    2: (
      <>
        <div className="xf-tabs-content-box__left">
          <h3 className="xf-tabs-content-box__left-title">模拟竞赛特点</h3>
          <p className="xf-tabs-content-box__left-text">
            <CheckOutlined style={{ color: "rgb(96,190,158)" }} /> 特点1
          </p>
          <p className="xf-tabs-content-box__left-text">
            <CheckOutlined style={{ color: "rgb(96,190,158)" }} /> 特点2
          </p>
          <p className="xf-tabs-content-box__left-text">
            <CheckOutlined style={{ color: "rgb(96,190,158)" }} /> 特点3
          </p>
          <p className="xf-tabs-content-box__left-text">
            <CheckOutlined style={{ color: "rgb(96,190,158)" }} /> 特点4
          </p>
          <p className="xf-tabs-content-box__left-textarea">
            简述学生可以在此实现何种功能
          </p>
        </div>
        <div className="xf-tabs-content-box__right">
          <div className="xf-tabs-content-box__right-content"></div>
        </div>
      </>
    ),
  } as TabsContentData;

  const content = contentMap[activeKey] || <div>404</div>;

  return (
    <>
      <div className="xf-tabs-content-box">{content}</div>
    </>
  );
};

export default TabsContentBox;
