import React from "react";
import NavHeader from "../../../components/Public/NavHeader";
import "./index.less";
import { Carousel, Divider, Typography } from "antd";
import TabsInstruction from "../../../components/TabsInstruction";
import ClassTools from "../../../components/ClassTools";
import ThoughtChain from "../../../components/ThoughtChain";
import homeJpg from "../../assets/images/home.jpg";

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
            <Carousel arrows>
              <div className="xf-home__main-content--first-carousel">
                <div className="xf-home__main-content--first-carousel-text">
                  <Title className="xf-home__main-content-text--bold">
                    系统介绍
                  </Title>

                  <Paragraph className="xf-home__main-content-text--light">
                    当今时代下，互联网发展日新月异，科技走进千家万户融入进人们的日常生活，许多学生或社会群体选择了去学习计算机科学与技术，越来越多的高校更加重视了计算机领域的高新人才培养。
                  </Paragraph>

                  <Paragraph className="xf-home__main-content-text--light">
                    并且，随着互联网和教育政策推进落实，计算机教育已经逐步呈现年轻化趋势。而对于全国各大高校，不论何种专业，学习至少一门编程语言也已经成为大学生们心中一件极为必要的事。
                    讯智学堂,基于<Text mark>『确定』和『自然』</Text>
                    的设计价值观，通过模块化的解决方案，降低冗余的学习成本，让学生专注于
                    <Text className="xf-home__main-content-text--light" strong>
                      知识的海洋
                    </Text>
                    。
                  </Paragraph>
                </div>
              </div>
              <div>
                <h3 className="xf-home__main-content--first-carousel">2</h3>
              </div>
              <div>
                <h3 className="xf-home__main-content--first-carousel">3</h3>
              </div>
              <div>
                <h3 className="xf-home__main-content--first-carousel">4</h3>
              </div>
            </Carousel>

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
