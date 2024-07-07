import React from "react";
import "./index.less";
import { NavLink } from "react-router-dom";

const NavHeader = () => {
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
            to={"/mockcompetition"}
            className={({ isActive }) =>
              `link ${isActive ? "xf-header__nav-item-link--active" : "xf-header__nav-item-link"}`
            }
          >
            模拟竞赛
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
      <div className="xf-header__user"></div>
    </nav>
  );
};

export default NavHeader;
