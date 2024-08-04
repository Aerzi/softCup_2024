import React, { useEffect, useState } from "react";
import "./index.less";
import NavHeader from "../../../components/Public/NavHeader";
import { useNavigate } from "react-router";
import { useSelector } from "react-redux";
import { RootState } from "../../../stores/redux/store";
import ClassPanel from "../../../components/Class/Student/getClassList";

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
            <ClassPanel />
          </div>
        </main>
        <footer className="xf-teaching__footer"></footer>
      </div>
      <div className="xf-teaching__mask"></div>
    </>
  );
};

export default Classroom;
