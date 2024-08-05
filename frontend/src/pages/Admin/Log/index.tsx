import React, { useEffect, useState } from "react";
import { Layout, Menu, Breadcrumb, Space, Typography } from "antd";
import { AppstoreOutlined, FileOutlined } from "@ant-design/icons";
import "./index.less";
import TeacherPPTCreater from "../../../components/Docs/Teacher/PPTCreater";
import DocsTranslater from "../../../components/Docs/Teacher/TranslateDocs";
import { useNavigate } from "react-router";
import { useSelector } from "react-redux";
import { RootState } from "../../../stores/redux/store";
import LogControl from "../../../components/Admin/LogControl";

const { Title } = Typography;
{
  /* 智能备课: 生成备课ppt，文档翻译 */
}
const AdminPanel = () => {
  const navigate = useNavigate();
  // 引入user, 有student和teacher两种状态
  const user = useSelector((state: RootState) => state.user);
  useEffect(() => {
    user.isLogin === false && navigate("/404");
  }, []);

  const [current, setCurrent] = useState("1");
  const [collapsed, setCollapsed] = useState(false);

  const menu = (
    <Menu
      onClick={(e) => setCurrent(e.key)}
      defaultSelectedKeys={["1"]}
      defaultOpenKeys={["sub1"]}
      mode="inline"
      theme="light"
    >
      <Menu.Item key="1" icon={<AppstoreOutlined />}>
        日志管理
      </Menu.Item>
      <Menu.Item key="2" icon={<FileOutlined />}>
        用户管理
      </Menu.Item>
    </Menu>
  );

  return (
    <div className="xf-log__page">
      <header className="xf-log__header">
        <Title
          level={2}
          style={{ color: "#fff", lineHeight: "64px", marginLeft: "20px" }}
        >
          后台管理系统
        </Title>
      </header>
      <Layout style={{ minHeight: "calc(100vh - 64px)" }}>
        <Layout.Sider
          width={200}
          className="site-layout-background"
          style={{ backgroundColor: "#fff" }}
          collapsible
          collapsed={collapsed}
          onCollapse={(value) => setCollapsed(value)}
        >
          <div
            style={{ width: "100%", height: "100%", backgroundColor: "#fff" }}
          >
            {menu}
          </div>
        </Layout.Sider>
        <Layout style={{ padding: "0 24px 24px" }}>
          <Breadcrumb style={{ margin: "16px 0" }}>
            <Breadcrumb.Item>首页</Breadcrumb.Item>
            {current === "1" ? (
              <Breadcrumb.Item>日志管理</Breadcrumb.Item>
            ) : current === "2" ? (
              <Breadcrumb.Item>用户管理</Breadcrumb.Item>
            ) : null}
          </Breadcrumb>
          <Layout.Content
            style={{
              background: "#fff",
              padding: 24,
              margin: 0,
              minHeight: 280,
            }}
          >
            <Space direction="vertical" style={{ width: "100%" }}>
              {current === "1" && <LogControl />}
              {current === "2" && <UserControl />}
            </Space>
          </Layout.Content>
        </Layout>
      </Layout>
    </div>
  );
};

export default AdminPanel;
