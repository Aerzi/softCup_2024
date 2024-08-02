import React, { useEffect, useState } from "react";
import "./index.less";
import { NavLink } from "react-router-dom";
import UserModal from "../../UserModal";
import { useSelector } from "react-redux";
import { RootState } from "../../../stores/redux/store";
import { message } from "antd";

const NavHeader = () => {
  // 全局挂载messageApi
  const [messageApi, contextHolder] = message.useMessage();

  const [isUserOpen, setIsUserOpen] = useState(false);
  // 对于导航栏，需要设置两种状态，一种是已登录，一种是未登录
  // 引入user, 有student和teacher两种状态
  const user = useSelector((state: RootState) => state.user);
  const [userRole, setUserRole] = useState<string>("");

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
                  to={"/room"}
                  className={({ isActive }) =>
                    `link ${isActive ? "xf-header__nav-item-link--active" : "xf-header__nav-item-link"}`
                  }
                >
                  {/* 个人课堂: 编程开发，加入课堂 */}
                  个人课堂
                </NavLink>
              </li>
              <li className="xf-header__nav-item">
                <NavLink
                  to={"/aid"}
                  className={({ isActive }) =>
                    `link ${isActive ? "xf-header__nav-item-link--active" : "xf-header__nav-item-link"}`
                  }
                >
                  开发辅助
                </NavLink>
              </li>
            </>
          ) : (
            <>
              <li className="xf-header__nav-item">
                <NavLink
                  to={"/classControl"}
                  className={({ isActive }) =>
                    `link ${isActive ? "xf-header__nav-item-link--active" : "xf-header__nav-item-link"}`
                  }
                >
                  课堂管理
                </NavLink>
              </li>
              <li className="xf-header__nav-item">
                <NavLink
                  to={"/schedule"}
                  className={({ isActive }) =>
                    `link ${isActive ? "xf-header__nav-item-link--active" : "xf-header__nav-item-link"}`
                  }
                >
                  智能备课
                </NavLink>
              </li>
            </>
          )}
        </ul>
        <div className="xf-header__user">
          {user.isLogin ? (
            <li className="xf-header__nav-item">
              <NavLink
                to={"/userPanel"}
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
