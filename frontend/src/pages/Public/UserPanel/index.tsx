import React from "react";
import "./index.less";
import NavHeader from "../../../components/Public/NavHeader";
import { ProCard } from "@ant-design/pro-components";
import UserBox from "../../../components/UserBox";
import { Button, message } from "antd";
import { onLogout } from "../../../services/userService";
import { useNavigate } from "react-router";
import { useDispatch } from "react-redux";
import { updateUser } from "../../../stores/slices/userSlice";

const UserPanel = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const onUserLogout = () => {
    // 清除本地的数据
    localStorage.clear();
    onLogout()
      .then((res: any) => {
        if (res.code === 200) {
          dispatch(updateUser({ isLogin: false, data: null, role: "student" }));
          // 跳转到登录页
          navigate("/");
        } else {
          message.error(res.message);
        }
      })
      .catch((err: any) => {
        message.error(err.message);
      });
  };
  return (
    <>
      <div className="xf-user__page">
        <header className="xf-user__header">
          <NavHeader />
        </header>
        <main className="xf-user__main">
          <div className="xf-user__main-content">
            <ProCard
              title="个人信息"
              bordered
              boxShadow
              className="xf-user__main-content-card"
            >
              <UserBox />
              <div className="exit-button-container">
                <Button
                  type="primary"
                  size="large"
                  className="exit-button"
                  onClick={onUserLogout}
                >
                  退出系统
                </Button>
              </div>
            </ProCard>
          </div>
        </main>
        <footer className="xf-user__footer"></footer>
      </div>
      <div className="xf-user__mask"></div>
    </>
  );
};

export default UserPanel;
