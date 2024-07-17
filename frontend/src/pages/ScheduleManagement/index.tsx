import React, { useState } from "react";
import NavHeader from "../../components/NavHeader";
import "./index.less";


const ScheduleManagement = () => {

  return (
    <>
      <div className="xf-Schedule__page">
        <header className="xf-Schedule__header">
          <NavHeader />
        </header>
        <main className="xf-Schedule__main">
          
        </main>
        <footer className="xf-Schedule__footer"></footer>
      </div>
      <div className="xf-Schedule__mask"></div>
    </>
  );
};

export default ScheduleManagement;
