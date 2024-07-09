import React from "react";
import "./index.less";
import NavHeader from "../../components/NavHeader";

const StudyPlan = () => {
  return (
    <>
      <div className="xf-plan__page">
        <header className="xf-plan__header">
          <NavHeader />
        </header>
        <main className="xf-plan__main"></main>
        <footer className="xf-plan__footer"></footer>
      </div>
      <div className="xf-plan__mask"></div>
    </>
  );
};

export default StudyPlan;
