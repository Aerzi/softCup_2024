import React from "react";
import NavHeader from "../../components/NavHeader";
import "./index.less";

const MockCompetition = () => {
  return (
    <>
      <div className="xf-mock__page">
        <header className="xf-mock__header">
          <NavHeader />
        </header>
        <main className="xf-mock__main"></main>
        <footer className="xf-mock__footer"></footer>
      </div>
      <div className="xf-mock__mask"></div>
    </>
  );
};

export default MockCompetition;
