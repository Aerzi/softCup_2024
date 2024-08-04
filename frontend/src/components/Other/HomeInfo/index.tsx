import React from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../../stores/redux/store";
import "./index.less";

const HomeInfo = () => {
  const user = useSelector((state: RootState) => state.user);
  return (
    <>
      {user.isLogin ? (
        <div className="xf-header__info">欢迎您，{user?.data?.userName}</div>
      ) : (
        <div className="xf-header__info">请您先登录以体验完整功能</div>
      )}
    </>
  );
};

export default HomeInfo;
