import React from "react";
import NavHeader from "../../components/NavHeader";
import "./index.less";
import { Carousel, Divider, Typography } from "antd";
import TabsInstruction from "../../components/TabsInstruction";
import ClassTools from "../../components/ClassTools";
import ThoughtChain from "../../components/ThoughtChain";

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
            <Carousel autoplay arrows>
              <div className="xf-home__main-content--first-carousel">
                <div className="xf-home__main-content--first-carousel-text">
                  <Title className="xf-home__main-content-text--bold">
                    系统介绍
                  </Title>

                  <Paragraph className="xf-home__main-content-text--light">
                    蚂蚁的企业级产品是一个庞大且复杂的体系。这类产品不仅量级巨大且功能复杂，而且变动和并发频繁，常常需要设计与开发能够快速的做出响应。同时这类产品中有存在很多类似的页面以及组件，可以通过抽象得到一些稳定且高复用性的内容。
                  </Paragraph>

                  <Paragraph className="xf-home__main-content-text--light">
                    随着商业化的趋势，越来越多的企业级产品对更好的用户体验有了进一步的要求。带着这样的一个终极目标，我们（蚂蚁集团体验技术部）经过大量的项目实践和总结，逐步打磨出一个服务于企业级产品的设计体系
                    Ant Design。基于<Text mark>『确定』和『自然』</Text>
                    的设计价值观，通过模块化的解决方案，降低冗余的生产成本，让设计者专注于
                    <Text className="xf-home__main-content-text--light" strong>
                      更好的用户体验
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
