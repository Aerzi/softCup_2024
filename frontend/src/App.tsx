import React from "react";
import { HashRouter, Route, Routes } from "react-router-dom";
import Home from "./pages/Public/Home";
import ScheduleManagement from "./pages/Teacher/Schedule";
import HomeworkManagement from "./pages/HomeworkManagement";
import UserPanel from "./pages/Public/UserPanel";
import Classroom from "./pages/Student/ClassRoom";
import DevAid from "./pages/Student/DevAid";
import { useSelector } from "react-redux";
import { RootState } from "./stores/redux/store";
import ClassControl from "./pages/Teacher/ClassControl";

function App() {
  const user = useSelector((state: RootState) => state.user);
  const PrivateRoute = ({
    path,
    element,
    roles,
    ...rest
  }: {
    path: string;
    element: React.JSX.Element;
    roles: Array<string>;
  }) => {
    // 这里的roles是一个数组，包含可以访问该路由的角色
    return (
      <Route
        {...rest}
        element={
          roles.includes(user?.role) ? (
            element
          ) : (
            <div>您没有权限访问这个页面</div>
          )
        }
      />
    );
  };

  return (
    <div id={"app"}>
      <HashRouter>
        <Routes>
          {/* 首页: 三端共用 */}
          <Route path="/" element={<Home />} />
          {/* 用户中心: 三段共用 */}
          <Route path="/userPanel" element={<UserPanel />} />
          {/* 以下路由需要权限判断 */}
          <Route
            path="/room"
            element={
              user.role !== "student" ? <div>404</div> : <ScheduleManagement />
            }
          />

          {/* 个人课堂: 编程开发，加入课堂 */}
          {/* <PrivateRoute
            path="/room"
            roles={["student"]}
            element={<Classroom />}
          /> */}
          {/* 开发辅助: 文档速读，文档扩写，图片生成，文档翻译 */}
          {/* <PrivateRoute path="/aid" roles={["student"]} element={<DevAid />} /> */}
          {/* 课堂管理: 创建课堂、编程题，上传文件资源到对应课堂 */}
          {/* <PrivateRoute
            path="/classControl"
            roles={["teacher"]}
            element={<ClassControl />}
          /> */}
          {/* 智能备课: 生成备课ppt，文档翻译 */}
          {/* <PrivateRoute
            path="/schedule"
            roles={["teacher"]}
            element={<ScheduleManagement />}
          /> */}
          {/* 日志管理, 用户管理 */}
        </Routes>
      </HashRouter>
    </div>
  );
}

export default App;
