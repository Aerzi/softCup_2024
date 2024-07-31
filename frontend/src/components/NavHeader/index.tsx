import React, { useEffect, useState } from "react";
import "./index.less";
import { NavLink } from "react-router-dom";
import UserModal from "../UserModal";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../stores/redux/store";
import { updateUser } from "../../stores/slices/userSlice";
import { onLogin } from "../../services/userService";
import { message } from "antd";
import { setLocalData } from "../../utils/Storage";

const NavHeader = () => {
  // 全局挂载messageApi
  const [messageApi, contextHolder] = message.useMessage();

  const [isUserOpen, setIsUserOpen] = useState(false);
  // 对于导航栏，需要设置两种状态，一种是已登录，一种是未登录
  // 引入user, 有student和teacher两种状态
  const user = useSelector((state: RootState) => state.user);
  const [userRole, setUserRole] = useState<string>("student");

  useEffect(() => {
    setUserRole(user.role);
  }, [user.role]);

  return (
    <>
      {contextHolder}
      <nav className="xf-header__nav">
        <ul className="xf-header__nav-list">
          <li className="xf-header__nav-item">
            <NavLink
              to={"/"}
              className={({ isActive }) =>
                `link ${isActive ? "xf-header__nav-item-link--active" : "xf-header__nav-item-link"}`
              }
            >
              系统首页
            </NavLink>
          </li>

          {userRole === "student" ? (
            <>
              <li className="xf-header__nav-item">
                <NavLink
                  to={"/studyplan"}
                  className={({ isActive }) =>
                    `link ${isActive ? "xf-header__nav-item-link--active" : "xf-header__nav-item-link"}`
                  }
                >
                  学习规划
                </NavLink>
              </li>
              <li className="xf-header__nav-item">
                <NavLink
                  to={"/teachingroom"}
                  className={({ isActive }) =>
                    `link ${isActive ? "xf-header__nav-item-link--active" : "xf-header__nav-item-link"}`
                  }
                >
                  {/* 在个人课堂界面中，学生用户可以获取到推荐的视频与自己的当前资源清单，并且提示哪些内容告急 */}
                  个人课堂
                </NavLink>
              </li>
              <li className="xf-header__nav-item">
                <NavLink
                  to={"/softwaredevelopment"}
                  className={({ isActive }) =>
                    `link ${isActive ? "xf-header__nav-item-link--active" : "xf-header__nav-item-link"}`
                  }
                >
                  编程开发
                </NavLink>
              </li>
            </>
          ) : (
            <>
              <li className="xf-header__nav-item">
                <NavLink
                  to={"/classmanagement"}
                  className={({ isActive }) =>
                    `link ${isActive ? "xf-header__nav-item-link--active" : "xf-header__nav-item-link"}`
                  }
                >
                  课程管理
                </NavLink>
              </li>
            </>
          )}
        </ul>
        <div className="xf-header__user">
          {user.isLogin ? (
            <li className="xf-header__nav-item">
              <NavLink
                to={"/userprofile"}
                className={({ isActive }) =>
                  `link ${isActive ? "xf-header__nav-item-link--active" : "xf-header__nav-item-link"}`
                }
              >
                个人信息
              </NavLink>
            </li>
          ) : (
            <div className="link-box">
              <span className="login-link" onClick={() => setIsUserOpen(true)}>
                登录/注册
              </span>
            </div>
          )}
        </div>
        <UserModal isOpen={isUserOpen} setIsOpen={setIsUserOpen} />
      </nav>
    </>
  );
};

export default NavHeader;
