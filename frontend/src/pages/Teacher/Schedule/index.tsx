import React from "react";
import NavHeader from "../../../components/Public/NavHeader";
import "./index.less";
{
  /* 智能备课: 生成备课ppt，文档翻译 */
}
const Schedule = () => {
  return (
    <>
      <div className="xf-Schedule__page">
        <header className="xf-Schedule__header">
          <NavHeader />
        </header>
        <main className="xf-Schedule__main"></main>
        <footer className="xf-Schedule__footer"></footer>
      </div>
      <div className="xf-Schedule__mask"></div>
    </>
  );
};

export default Schedule;
