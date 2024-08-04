import React, { useEffect, useState } from "react";
import { Layout, Menu, Breadcrumb, Typography, Space, Button } from "antd";
import { LogoutOutlined } from "@ant-design/icons";
import { IQuestion } from "../../../components/Question/type";
import { getLocalData, setLocalData } from "../../../utils/Storage";
import Timer from "../../../components/Other/Timer";
import { useNavigate } from "react-router";
import QuestionPanel from "../../../components/Code/QuestionPanel";
import CodeSphere from "../../../components/Code/CodeSphere";
import { IAnswer } from "../../../components/Code/type";

const { Header, Content } = Layout;
const { Title } = Typography;

const CodePen = () => {
  const navigate = useNavigate();

  const [question, setQuestion] = useState<IQuestion>(null);

  const [resData, setResData] = useState<IAnswer>(null);

  useEffect(() => {
    setQuestion(getLocalData("questionData") as IQuestion);
    return () => {
      setLocalData("questionData", null);
    };
  }, []);

  const handleExitClick = () => {
    setLocalData("questionData", null);
    navigate("/room");
  };

  return (
    <Layout style={{ minHeight: "100vh" }}>
      <Header style={{ padding: "0 16px", height: "64px" }}>
        <Title
          level={2}
          style={{
            color: "white",
            lineHeight: "30px",
            textAlign: "center",
            marginBottom: "5px",
          }}
        >
          {question?.name}
        </Title>
        <Timer />
      </Header>
      <Button
        type="default"
        icon={<LogoutOutlined />}
        onClick={handleExitClick}
        style={{
          position: "absolute",
          top: "20px",
          right: "10px",
          height: "24px",
        }}
      >
        退出
      </Button>
      <Layout style={{ height: "calc(100vh - 64px)" }}>
        <QuestionPanel question={question} answer={resData} />
        <Layout
          style={{
            height: "100%",
            backgroundColor: "rgb(30,30,30)",
            padding: 0,
          }}
        >
          <Content
            style={{
              margin: "0",
              overflow: "initial",
              width: "100%",
              height: "100%",
              padding: 0,
            }}
          >
            <div
              style={{
                minWidth: "1000px",
                padding: 24,
                background: "rgb(30,30,30)",
                minHeight: 360,
                height: "100%",
              }}
            >
              {/* 这里是主要内容区域，可以放置你的代码编辑器组件 */}
              <CodeSphere
                select={true}
                questionId={question?.id}
                classId={question?.classId}
                onAnswer={(res: IAnswer) => setResData(res)}
              />
            </div>
          </Content>
        </Layout>
      </Layout>
    </Layout>
  );
};

export default CodePen;
