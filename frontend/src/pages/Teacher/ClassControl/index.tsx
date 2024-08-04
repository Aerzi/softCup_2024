import React, { useState } from "react";
import { Layout, Menu, Breadcrumb, Typography, Card, Space } from "antd";
import {
  AppstoreOutlined,
  FileOutlined,
  ProjectOutlined,
} from "@ant-design/icons";
import NavHeader from "../../../components/Public/NavHeader";
import "./index.less";
import PageClass from "../../../components/Class/getClassPage";
import QuestionModal from "../../../components/Question/CreateQuestion";
import QuestionPage from "../../../components/Question/QuestionPage";
import CreateClass from "../../../components/Class/CreateClass";
import ClassSelect from "../../../components/Class/getClassList";
import CreateFile from "../../../components/File/CreateFile";

const { SubMenu } = Menu;
const { Title } = Typography;
{
  /* 课堂管理: 创建课堂、编程题，上传文件资源到对应课堂 */
}
const ClassControl = () => {
  const [current, setCurrent] = useState("1");
  // 创建state用于传递当前选中的课堂ID
  const [selectId, setSelectId] = useState(0);

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
      <SubMenu key="sub2" icon={<ProjectOutlined />} title="题目管理">
        <Menu.Item key="3">发布习题任务</Menu.Item>
        <Menu.Item key="4">查看习题任务信息</Menu.Item>
      </SubMenu>
      <SubMenu key={"sub3"} icon={<FileOutlined />} title={"资源管理"}>
        <Menu.Item key="5">添加资源文件</Menu.Item>
        <Menu.Item key="6">查看资源目录</Menu.Item>
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
            {current === "1" || current === "2" ? (
              <Breadcrumb.Item>课堂管理</Breadcrumb.Item>
            ) : current === "3" || current === "4" ? (
              <Breadcrumb.Item>题目管理</Breadcrumb.Item>
            ) : current === "5" || current === "6" ? (
              <Breadcrumb.Item>资源管理</Breadcrumb.Item>
            ) : null}
            {current === "1" ? (
              <Breadcrumb.Item>创建课堂</Breadcrumb.Item>
            ) : current === "2" ? (
              <Breadcrumb.Item>查看课堂信息</Breadcrumb.Item>
            ) : current === "3" ? (
              <Breadcrumb.Item>发布习题任务</Breadcrumb.Item>
            ) : current === "4" ? (
              <Breadcrumb.Item>查看习题任务信息</Breadcrumb.Item>
            ) : current === "5" ? (
              <Breadcrumb.Item>添加资源文件</Breadcrumb.Item>
            ) : current === "6" ? (
              <Breadcrumb.Item>查看资源目录</Breadcrumb.Item>
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
                <Card title="创建课堂" className="xf-Class__card">
                  <CreateClass />
                </Card>
              )}
              {current === "2" && (
                <Card title="查看课堂信息" className="xf-Class__card">
                  <PageClass />
                </Card>
              )}
              {current === "3" && (
                <Card
                  title="创建习题"
                  className="xf-Class__card"
                  extra={
                    <ClassSelect
                      id={selectId}
                      onChange={(id: number) => setSelectId(id)}
                    />
                  }
                >
                  <QuestionModal classId={selectId} />
                </Card>
              )}
              {current === "4" && (
                <Card
                  title="查看习题信息"
                  className="xf-Class__card"
                  extra={
                    <ClassSelect
                      id={selectId}
                      onChange={(id: number) => setSelectId(id)}
                    />
                  }
                >
                  <QuestionPage classId={selectId} />
                </Card>
              )}
              {current === "5" && (
                <Card
                  title="添加资源文件"
                  className="xf-Class__card"
                  extra={
                    <ClassSelect
                      id={selectId}
                      onChange={(id: number) => setSelectId(id)}
                    />
                  }
                >
                  <CreateFile classId={selectId} />
                </Card>
              )}
            </Space>
          </Layout.Content>
        </Layout>
      </Layout>
    </div>
  );
};

export default ClassControl;
