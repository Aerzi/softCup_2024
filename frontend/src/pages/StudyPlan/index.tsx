import React, { useEffect, useState } from "react";
import "./index.less";
import NavHeader from "../../components/NavHeader";
import { ProCard } from "@ant-design/pro-components";
import { Avatar } from "antd";
import { UserOutlined } from "@ant-design/icons";
import GradientStacked from "../../components/GradientStacked";
import LiquidChart from "../../components/LiquidChart";
import DescJob from "../../components/DescJob";
import RadarChart from "../../components/RadarChart";
import PageClass from "../../components/AboutClass/getPageClass";
import { onGetStudentInfo } from "../../services/userService";
import { updateUser } from "../../stores/slices/userSlice";
import { useDispatch } from "react-redux";
import { UserInfo } from "../../types/UserType";

const StudyPlan = () => {
  const dispatch = useDispatch();
  const [userData, setUserData] = useState<UserInfo>(null);
  const onGetUserInfo = () => {
    onGetStudentInfo()
      .then((res: any) => {
        dispatch(updateUser({ data: res.response }));
        setUserData(res.response);
      })
      .catch((err: any) => {
        console.log(err);
      });
  };

  let flag = false;
  useEffect(() => {
    if (!flag) {
      flag = true;
      onGetUserInfo();
    }
  }, []);

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
                  <span className="xf-plan__main-header-text">
                    姓名:{userData?.userName}
                  </span>
                </span>
                <span className="xf-plan__main-header-right">
                  <div className="xf-plan__main-header-right-body">
                    <span className="xf-plan__main-header-text">
                      手机: {userData?.phone}
                    </span>
                    <span className="xf-plan__main-header-text">
                      学校:马房山男子职业技术学院
                    </span>
                    <span className="xf-plan__main-header-text">
                      性别:{userData?.sex === 1 ? "男" : "女"}
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
                  colSpan="350px"
                  layout="center"
                  bordered
                  className="xf-plan__main-card"
                >
                  <LiquidChart />
                </ProCard>
                <ProCard
                  title="已选课程"
                  colSpan="350px"
                  layout="center"
                  bordered
                  className="xf-plan__main-card"
                >
                  <DescJob classNum={20} />
                </ProCard>
                <ProCard
                  title="个人能力"
                  colSpan="auto"
                  bordered
                  className="xf-plan__main-card xf-plan__main-card"
                >
                  <RadarChart />
                </ProCard>
              </ProCard>
              <ProCard style={{ marginBlockStart: 8 }} gutter={8} ghost>
                <ProCard
                  layout="center"
                  bordered
                  className="xf-plan__main-card"
                >
                  <GradientStacked />
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
function dispatch(arg0: any) {
  throw new Error("Function not implemented.");
}
