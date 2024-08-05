import React, { useEffect, useState } from "react";
import { Layout, Menu, Breadcrumb, Typography, Card, Space } from "antd";
import { AppstoreOutlined, FileOutlined } from "@ant-design/icons";
import NavHeader from "../../../components/Public/NavHeader";
import "./index.less";
import ImageGenerator from "../../../components/Image";
import DocumentWriter from "../../../components/Docs/DocsWrite";
import TeacherPPTCreater from "../../../components/Docs/Teacher/PPTCreater";
import DocsTranslater from "../../../components/Docs/Teacher/TranslateDocs";
import { useNavigate } from "react-router";
import { useSelector } from "react-redux";
import { RootState } from "../../../stores/redux/store";

{
  /* 智能备课: 生成备课ppt，文档翻译 */
}
const Schedule = () => {
  const [current, setCurrent] = useState("1");
  const [collapsed, setCollapsed] = useState(false);

  const navigate = useNavigate();
  // 引入user, 有student和teacher两种状态
  const user = useSelector((state: RootState) => state.user);
  useEffect(() => {
    user.isLogin === false && navigate("/404");
  }, []);

  const menu = (
    <Menu
      onClick={(e) => setCurrent(e.key)}
      defaultSelectedKeys={["1"]}
      defaultOpenKeys={["sub1"]}
      mode="inline"
      theme="light"
    >
      <Menu.Item key="1" icon={<AppstoreOutlined />}>
        生成备课PPT
      </Menu.Item>
      <Menu.Item key="2" icon={<FileOutlined />}>
        文档翻译
      </Menu.Item>
    </Menu>
  );

  return (
    <div className="xf-Schedule__page">
      <header className="xf-Schedule__header">
        <NavHeader />
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
            <Breadcrumb.Item>智能备课</Breadcrumb.Item>
            {current === "1" ? (
              <Breadcrumb.Item>生成备课PPT</Breadcrumb.Item>
            ) : current === "2" ? (
              <Breadcrumb.Item>文档翻译</Breadcrumb.Item>
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
              {current === "1" && <TeacherPPTCreater />}
              {current === "2" && <DocsTranslater />}
            </Space>
          </Layout.Content>
        </Layout>
      </Layout>
    </div>
  );
};

export default Schedule;
