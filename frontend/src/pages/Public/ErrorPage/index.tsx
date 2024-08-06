import React from "react";
import { Link } from "react-router-dom"; // 引入Link组件用于导航
import "./index.less"; // 引入LESS样式文件

const ErrorPage = () => {
  return (
    <div className="not-found-page">
      <div className="error-code">404</div>
      <div className="error-description">
        Oops! The page you are looking for does not exist.
      </div>
      <Link to="/" className="return-btn">
        Return to Home
      </Link>
    </div>
  );
};

export default ErrorPage;
