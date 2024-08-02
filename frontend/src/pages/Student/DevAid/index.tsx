import React from "react";
import "./index.less";
import NavHeader from "../../../components/Public/NavHeader";

const DevAid = () => {
  return (
    <>
      <div className="xf-aid__page">
        <header className="xf-aid__header">
          <NavHeader />
        </header>
        <main className="xf-aid__main"></main>
        <footer className="xf-aid__footer"></footer>
      </div>
      <div className="xf-aid__mask"></div>
    </>
  );
};

export default DevAid;
