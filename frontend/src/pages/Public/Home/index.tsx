import React from "react";
import NavHeader from "../../../components/Public/NavHeader";
import "./index.less";
import { Typography } from "antd";
import TabsInstruction from "../../../components/TabsInstruction";
import ClassTools from "../../../components/ClassTools";
import ThoughtChain from "../../../components/ThoughtChain";
import HomeInfo from "../../../components/Other/HomeInfo";

// 文本描述
const { Title, Paragraph, Text, Link } = Typography;

const Home = () => {
  return (
    <>
      <div className="xf-home__page">
        <header className="xf-home__header">
          <NavHeader />
        </header>
        <main className="xf-home__main">
          {/* 这部分由于没有什么交互性，所以不需要组件化 */}
          <div className="xf-home__main-content--first">
            <HomeInfo />
            <div className="xf-home__main-content--first-show">
              <h2 className="xf-home__main-content--first-show-title">
                产教融合的超级实验开发与运行环境
              </h2>
              <h3 className="xf-home__main-content--first-show-subtitle">
                Super Labs Development & Runtime Environment for Industry and
                Education
              </h3>
              <TabsInstruction />
            </div>
          </div>
          <div className="xf-home__main-content--second">
            <ClassTools />
          </div>
          <div className="xf-home__main-content--third">
            <ThoughtChain />
          </div>
        </main>
        <footer className="xf-home__footer"></footer>
      </div>
      <div className="xf-home__mask"></div>
    </>
  );
};

export default Home;
