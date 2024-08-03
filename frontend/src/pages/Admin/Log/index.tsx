import React from "react";
import NavHeader from "../../../components/Public/NavHeader";
import "./index.less";

const HomeworkManagement = () => {
  return (
    <>
      <div className="xf-Homework__page">
        <header className="xf-Homework__header">
          <NavHeader />
        </header>
        <main className="xf-Homework__main"></main>
        <footer className="xf-Homework__footer"></footer>
      </div>
      <div className="xf-Homework__mask"></div>
    </>
  );
};

export default HomeworkManagement;
