import React from "react";
import { HashRouter, Route, Routes } from "react-router-dom";
import Home from "./pages/Public/Home";
import ScheduleManagement from "./pages/Teacher/Schedule";
import UserPanel from "./pages/Public/UserPanel";
import Classroom from "./pages/Student/ClassRoom";
import DevAid from "./pages/Student/DevAid";
import ClassControl from "./pages/Teacher/ClassControl";
import ErrorPage from "./pages/Public/ErrorPage";
import CodePen from "./pages/Student/CodePen";

function App() {
  return (
    <div id={"app"}>
      <HashRouter>
        <Routes>
          {/* 首页: 三端共用 */}
          <Route path="/" element={<Home />} />
          {/* 用户中心: 三段共用 */}
          <Route path="/userPanel" element={<UserPanel />} />
          {/* 404 */}
          <Route path="/404" element={<ErrorPage />} />
          {/* 以下路由需要权限判断 */}
          {/* 个人课堂: 编程开发，加入课堂 */}
          <Route path="/room" element={<Classroom />} />
          {/* 开发辅助: 文档速读，文档扩写，图片生成，文档翻译 */}
          <Route path="/aid" element={<DevAid />} />
          {/* 课堂管理: 创建课堂、编程题，上传文件资源到对应课堂 */}
          <Route path="/classControl" element={<ClassControl />} />
          {/* 智能备课: 生成备课ppt，文档翻译 */}
          <Route path="/schedule" element={<ScheduleManagement />} />
          {/* 在线编译平台 */}
          <Route path="/codePen" element={<CodePen />} />
          {/* 日志管理, 用户管理 */}
        </Routes>
      </HashRouter>
    </div>
  );
}

export default App;
