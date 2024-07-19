import React from "react";
import {
  Layout,
  Menu,
  Breadcrumb,
  Button,
  Typography,
  Card,
  Space,
} from "antd";
import {
  AppstoreOutlined,
  ProjectOutlined,
  CodeOutlined,
} from "@ant-design/icons";
import NavHeader from "../../components/NavHeader";
import "./index.less";
import CreateClassForm from "../../components/AboutClass/CreateClass";
import PageClass from "../../components/AboutClass/getPageClass";

const { SubMenu } = Menu;
const { Title } = Typography;

const ClassManagement = () => {
  const [current, setCurrent] = React.useState("1");

  const menu = (
    <Menu
      onClick={(e) => setCurrent(e.key)}
      defaultSelectedKeys={["1"]}
      defaultOpenKeys={["sub1"]}
      mode="inline"
      theme="dark"
    >
      <SubMenu key="sub1" icon={<AppstoreOutlined />} title="课堂管理">
        <Menu.Item key="1">创建课堂</Menu.Item>
        <Menu.Item key="2">查看课堂信息</Menu.Item>
      </SubMenu>
      <SubMenu key="sub2" icon={<ProjectOutlined />} title="项目管理">
        <Menu.Item key="3">发布项目开发任务</Menu.Item>
      </SubMenu>
      <SubMenu key="sub3" icon={<CodeOutlined />} title="编程习题">
        <Menu.Item key="4">发布编程习题任务</Menu.Item>
      </SubMenu>
    </Menu>
  );

  return (
    <div className="xf-Class__page">
      <header className="xf-Class__header">
        <NavHeader />
      </header>
      <Layout style={{ minHeight: "calc(100vh - 64px)" }}>
        <Layout.Sider width={200} className="site-layout-background">
          {menu}
        </Layout.Sider>
        <Layout style={{ padding: "0 24px 24px" }}>
          <Breadcrumb style={{ margin: "16px 0" }}>
            <Breadcrumb.Item>首页</Breadcrumb.Item>
            <Breadcrumb.Item>课堂管理</Breadcrumb.Item>
          </Breadcrumb>
          <Layout.Content
            style={{
              background: "#fff",
              padding: 24,
              margin: 0,
              minHeight: 280,
            }}
          >
            <Title
              level={2}
              style={{ textAlign: "center", fontFamily: "阿里妈妈数黑体 Bold" }}
            >
              当前操作：
              {current === "1"
                ? "创建课堂"
                : current === "2"
                  ? "查看课堂信息"
                  : current === "3"
                    ? "发布项目开发任务"
                    : "发布编程习题任务"}
            </Title>
            <Space direction="vertical" style={{ width: "100%" }}>
              {current === "1" && (
                <Card title="创建课堂">
                  <CreateClassForm />
                </Card>
              )}
              {current === "2" && (
                <Card title="查看课堂信息">
                  <PageClass />
                </Card>
              )}
              {current === "3" && (
                <Card
                  title="发布项目开发任务"
                  extra={<Button type="primary">发布</Button>}
                >
                  <p>在这里填写项目开发任务的相关信息。</p>
                </Card>
              )}
              {current === "4" && (
                <Card
                  title="发布编程习题任务"
                  extra={<Button type="primary">发布</Button>}
                >
                  <p>在这里填写编程习题任务的相关信息。</p>
                </Card>
              )}
            </Space>
          </Layout.Content>
        </Layout>
      </Layout>
    </div>
  );
};

export default ClassManagement;
