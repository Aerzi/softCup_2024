import React, { useState } from "react";
import "./index.less";
import { NavLink } from "react-router-dom";
import UserModal from "../UserModal";

const NavHeader = () => {
  const [isUserOpen, setIsUserOpen] = useState(false);
  // 对于导航栏，需要设置两种状态，一种是已登录，一种是未登录

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
      </ul>
      <div className="xf-header__user" onClick={() => setIsUserOpen(true)}>
        登录/注册
      </div>
      <UserModal isOpen={isUserOpen} setIsOpen={setIsUserOpen} />
    </nav>
  );
};

export default NavHeader;
