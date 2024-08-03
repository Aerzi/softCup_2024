import React, { useEffect } from "react";
import "./index.less";
import NavHeader from "../../../components/Public/NavHeader";
import { ProCard } from "@ant-design/pro-components";
import DroppedCard from "../../../components/DroppedCard";
import { useNavigate } from "react-router";
import { useSelector } from "react-redux";
import { RootState } from "../../../stores/redux/store";

/* 个人课堂: 编程开发，加入课堂 */
const Classroom = () => {
  const navigate = useNavigate();
  // 引入user, 有student和teacher两种状态
  const user = useSelector((state: RootState) => state.user);
  useEffect(() => {
    user.isLogin === false && navigate("/404");
  }, []);
  return (
    <>
      <div className="xf-teaching__page">
        <header className="xf-teaching__header">
          <NavHeader />
        </header>
        <main className="xf-teaching__main">
          <div className="xf-teaching__main-content">
            <ProCard style={{ marginBlockStart: 8 }} gutter={8} ghost>
              <ProCard
                colSpan="500px"
                layout="center"
                direction="column"
                bordered
                className="xf-teaching__main-content-card-left"
                gutter={8}
                ghost
              >
                <ProCard
                  colSpan="500px"
                  layout="center"
                  bordered
                  className="xf-teaching__main-content-card-left--1"
                >
                  {/* 加入课堂 */}
                </ProCard>
                <ProCard
                  style={{ marginBlockStart: 8 }}
                  colSpan="500px"
                  layout="center"
                  bordered
                  className="xf-teaching__main-content-card-left--2"
                >
                  {/* 课堂管理 */}
                </ProCard>
              </ProCard>
              <ProCard
                layout="center"
                bordered
                className="xf-teaching__main-content-card-right"
              >
                <DroppedCard />
              </ProCard>
            </ProCard>
          </div>
        </main>
        <footer className="xf-teaching__footer"></footer>
      </div>
      <div className="xf-teaching__mask"></div>
    </>
  );
};

export default Classroom;
