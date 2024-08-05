import React, { useEffect, useState } from "react";
import { Layout, Menu, Breadcrumb, Typography, Card, Space } from "antd";
import {
  AppstoreOutlined,
  FileOutlined,
  ProjectOutlined,
} from "@ant-design/icons";
import NavHeader from "../../../components/Public/NavHeader";
import "./index.less";
import ImageGenerator from "../../../components/Image";
import DocumentQAPanel from "../../../components/Docs/chatDocs/indes";
import DocumentWriter from "../../../components/Docs/DocsWrite";
import PPTCreater from "../../../components/Docs/PPTCreater";
import { useNavigate } from "react-router";
import { useSelector } from "react-redux";
import { RootState } from "../../../stores/redux/store";

const { SubMenu } = Menu;
const { Title } = Typography;
{
  /* 开发辅助: 文档速读，文档扩写，图片生成，文档翻译 */
}
const ClassControl = () => {
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
      <SubMenu key="sub1" icon={<AppstoreOutlined />} title="智能文档">
        <Menu.Item key="1">文档问答</Menu.Item>
        <Menu.Item key="2">文档扩写</Menu.Item>
      </SubMenu>
      <Menu.Item key="3">智能PPT</Menu.Item>
      <SubMenu key={"sub3"} icon={<FileOutlined />} title={"智能图片"}>
        <Menu.Item key="4">图片生成</Menu.Item>
      </SubMenu>
    </Menu>
  );

  return (
    <div className="xf-Class__page">
      <header className="xf-Class__header">
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
            <Breadcrumb.Item>智能辅助</Breadcrumb.Item>
            {current === "1" || current === "2" ? (
              <Breadcrumb.Item>智能文档</Breadcrumb.Item>
            ) : current === "3" ? (
              <Breadcrumb.Item>智能PPT</Breadcrumb.Item>
            ) : current === "4" ? (
              <Breadcrumb.Item>智能图片</Breadcrumb.Item>
            ) : null}
            {current === "1" ? (
              <Breadcrumb.Item>文档问答</Breadcrumb.Item>
            ) : current === "2" ? (
              <Breadcrumb.Item>文档扩写</Breadcrumb.Item>
            ) : current === "3" ? (
              <Breadcrumb.Item>PPT生成</Breadcrumb.Item>
            ) : current === "4" ? (
              <Breadcrumb.Item>图片生成</Breadcrumb.Item>
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
              {current === "1" && <DocumentQAPanel />}
              {current === "2" && <DocumentWriter />}
              {current === "3" && <PPTCreater />}
              {current === "4" && <ImageGenerator />}
            </Space>
          </Layout.Content>
        </Layout>
      </Layout>
    </div>
  );
};

export default ClassControl;
