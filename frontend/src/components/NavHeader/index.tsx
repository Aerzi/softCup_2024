import React from "react";

import { Link } from "react-router-dom";

const NavHeader = () => {
  return (
    <nav className="xf-home__nav">
      <ul className="xf-home__nav-list">
        <li className="xf-home__nav-item">
          <Link to={"/"} className="xf-home__nav-item-link">
            首页
          </Link>
        </li>
        <li className="xf-home__nav-item">
          <Link to={"/studyplan"} className="xf-home__nav-item-link">
            学习规划
          </Link>
        </li>
        <li className="xf-home__nav-item">
          <Link to={"/teachingroom"} className="xf-home__nav-item-link">
            教学课堂
          </Link>
        </li>
        <li className="xf-home__nav-item">
          <Link to={"/mockcompetition"} className="xf-home__nav-item-link">
            模拟竞赛
          </Link>
        </li>
        <li className="xf-home__nav-item">
          <Link to={"/userprofile"} className="xf-home__nav-item-link">
            个人信息
          </Link>
        </li>
      </ul>
    </nav>
  );
};

export default NavHeader;
