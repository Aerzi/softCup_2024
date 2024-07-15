import React from "react";
import "./index.less";
import NavHeader from "../../components/NavHeader";
import { ProCard } from "@ant-design/pro-components";
import { Avatar } from "antd";
import { UserOutlined } from "@ant-design/icons";
import GradientStacked from "../../components/GradientStacked";
import LiquidChart from "../../components/LiquidChart";
import DescJob from "../../components/DescJob";
import RadarChart from "../../components/RadarChart";
import TablePro from "../../components/TablePro";

const StudyPlan = () => {
  return (
    <>
      <div className="xf-plan__page">
        <header className="xf-plan__header">
          <NavHeader />
        </header>
        <main className="xf-plan__main">
          <div className="xf-plan__main-content">
            <div className="xf-plan__main-header">
              <ProCard className="xf-plan__main-header-card">
                <span className="xf-plan__main-header-left">
                  <Avatar size={64} icon={<UserOutlined />} />
                  <span className="xf-plan__main-header-text">姓名</span>
                </span>
                <span className="xf-plan__main-header-right">
                  <div className="xf-plan__main-header-right-body">
                    <span className="xf-plan__main-header-text">
                      学号:012211080212
                    </span>
                    <span className="xf-plan__main-header-text">
                      学校:马房山男子职业技术学院
                    </span>
                    <span className="xf-plan__main-header-text">
                      加入时间:2022-09-01
                    </span>
                  </div>
                </span>
              </ProCard>
            </div>
            <div className="xf-plan__main-body">
              {/* 这部分主要是数据展板，展示数据，使用proCard来进行排布 */}
              <ProCard style={{ marginBlockStart: 8 }} gutter={8} ghost>
                <ProCard
                  title="完成任务占比"
                  colSpan="300px"
                  layout="center"
                  bordered
                  className="xf-plan__main-card"
                >
                  <LiquidChart />
                </ProCard>
                <ProCard
                  title="已选课程"
                  colSpan="300px"
                  layout="center"
                  bordered
                  className="xf-plan__main-card"
                >
                  <DescJob classNum={20} />
                </ProCard>
                <ProCard
                  layout="center"
                  bordered
                  className="xf-plan__main-card"
                >
                  <GradientStacked />
                </ProCard>
              </ProCard>
              <ProCard style={{ marginBlockStart: 8 }} gutter={8} ghost>
                <ProCard title="个人能力" colSpan="30%" bordered className="xf-plan__main-card xf-plan__main-card--long">
                  <RadarChart />
                </ProCard>
                <ProCard
                  title="学习清单"
                  bordered
                  layout="center"
                  className="xf-plan__main-card xf-plan__main-card--long"
                >
                  <TablePro />
                </ProCard>
              </ProCard>
            </div>
          </div>
        </main>
        <footer className="xf-plan__footer"></footer>
      </div>
      <div className="xf-plan__mask"></div>
    </>
  );
};

export default StudyPlan;
