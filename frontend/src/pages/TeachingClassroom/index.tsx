import React from "react";
import "./index.less";
import NavHeader from "../../components/NavHeader";
import { ProCard } from "@ant-design/pro-components";

const TeachingClassroom = () => {
  return (
    <>
      <div className="xf-teaching__page">
        <header className="xf-teaching__header">
          <NavHeader />
        </header>
        <main className="xf-teaching__main">
          <div className="xf-teaching__main-content">
            <ProCard style={{ marginBlockStart: 8 }} gutter={8} ghost>
              <ProCard colSpan="300px" layout="center" direction="column" bordered className="xf-teaching__main-content-card-left">
                <ProCard colSpan="300px" layout="center" bordered className="xf-teaching__main-content-card-left--1">
                  colSpan - 300px
                </ProCard>
                <ProCard colSpan="300px" layout="center" bordered className="xf-teaching__main-content-card-left--2">
                  colSpan - 300px
                </ProCard>
              </ProCard>
              <ProCard layout="center" bordered className="xf-teaching__main-content-card-right">
                Auto
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

export default TeachingClassroom;
