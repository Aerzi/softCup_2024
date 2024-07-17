import React from "react";
import "./index.less";
import NavHeader from "../../components/NavHeader";
import { ProCard } from "@ant-design/pro-components";

const UserProfile = () => {
  return (
    <>
      <div className="xf-user__page">
        <header className="xf-user__header">
          <NavHeader />
        </header>
        <main className="xf-user__main">
          <div className="xf-user__main-content">
            <ProCard bordered boxShadow className="xf-user__main-content-card">
              个人信息展示
            </ProCard>
          </div>
        </main>
        <footer className="xf-user__footer"></footer>
      </div>
      <div className="xf-user__mask"></div>
    </>
  );
};

export default UserProfile;
