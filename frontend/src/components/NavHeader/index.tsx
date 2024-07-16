import React, { useState } from "react";
import "./index.less";
import { NavLink } from "react-router-dom";
import UserModal from "../UserModal";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../stores/redux/store";
import { updateUser } from "../../stores/slices/userSlice";

const NavHeader = () => {
  const [isUserOpen, setIsUserOpen] = useState(false);
  // 对于导航栏，需要设置两种状态，一种是已登录，一种是未登录
  // 引入user, 有student和teacher两种状态
  const user = useSelector((state: RootState) => state.user);

  // 使用useDispatch钩子获取dispatch函数
  const dispatch = useDispatch();
  // 定义一个切换角色的函数
  const toggleRole = () => {
    // 根据当前角色切换到另一个角色
    dispatch(updateUser(!(user.role === "student")));
  };

  return (
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

        {user.role === "student" ? (
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
                教学课堂
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
      <div className="xf-header__user" onClick={() => setIsUserOpen(true)}>
        {user.isLogin ? (
          <NavLink
            to={"/userprofile"}
            className={({ isActive }) =>
              `link ${isActive ? "xf-header__nav-item-link--active" : "xf-header__nav-item-link"}`
            }
          >
            个人信息
          </NavLink>
        ) : (
          <span>登录/注册</span>
        )}
      </div>
      <UserModal isOpen={isUserOpen} setIsOpen={setIsUserOpen} />
    </nav>
  );
};

export default NavHeader;
