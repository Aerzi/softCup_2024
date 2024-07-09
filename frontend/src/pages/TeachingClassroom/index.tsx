import React from "react";
import "./index.less";
import NavHeader from "../../components/NavHeader";

const TeachingClassroom = () => {
  return (
    <>
      <div className="xf-teaching__page">
        <header className="xf-teaching__header">
          <NavHeader />
        </header>
        <main className="xf-teaching__main"></main>
        <footer className="xf-teaching__footer"></footer>
      </div>
      <div className="xf-teaching__mask"></div>
    </>
  );
};

export default TeachingClassroom;
