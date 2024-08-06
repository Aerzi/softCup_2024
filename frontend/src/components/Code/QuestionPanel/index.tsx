import React, { useState } from "react";
import { Layout, Typography, Space, Button, Tag, Divider } from "antd";
import { IQuestion } from "../../Question/type";
import { ProCard } from "@ant-design/pro-components";
import "./index.less";
import { IAnswer } from "../type";
import { SwitchTransition, CSSTransition } from "react-transition-group";

const { Text, Paragraph, Title } = Typography;
const { Sider } = Layout;

const QuestionPanel = ({
  question,
  answer,
}: {
  question: IQuestion;
  answer: IAnswer;
}) => {
  const [visible, setVisible] = useState(false);
  const getDifficultyTag = (level: any) => {
    return (
      <Tag
        color={
          level === "1" ? "#87d068" : level === "2" ? "#faad14" : "#f5222d"
        }
      >
        {level === "1" ? "简单" : level === "2" ? "中等" : "困难"}
      </Tag>
    );
  };
  return (
    <Sider width={400} style={{ color: "white" }}>
      <ProCard
        extra={
          <Button
            type="default"
            disabled={answer === null ? true : false}
            onClick={() => setVisible(!visible)}
          >
            查看反馈
          </Button>
        }
        style={{
          height: "100%",
          color: "rgb(250,250,250)",
          padding: "20px",
          borderRadius: 0,
        }}
      >
        <SwitchTransition mode="out-in">
          <CSSTransition
            classNames={{
              enter: "transition-enter",
              enterActive: "transition-enter-active",
              exit: "transition-exit",
              exitActive: "transition-exit-active",
            }}
            timeout={500}
            key={visible ? "answer" : "panel"}
          >
            {visible ? (
              <>
                <Title level={3} className="question-title">
                  <Space>
                    问题{question?.id}
                    {question?.name}
                  </Space>
                </Title>
                <Divider />
                <Title className="question-description" level={5}>
                  评测反馈
                </Title>
                <Paragraph>{answer?.feedback}</Paragraph>
                <Divider />
                {answer?.modified_code !== null && (
                  <>
                    <Title className="question-description" level={5}>
                      改进方式
                    </Title>
                    <Paragraph className="question-format">
                      {answer?.modified_code}
                    </Paragraph>
                    <Divider />
                  </>
                )}
                <Title level={5}>错误分析</Title>
                <Paragraph code type="secondary">
                  {answer?.error_analysis}
                </Paragraph>
                <Divider />
                <Title level={5}>优化建议</Title>
                <Paragraph code type="secondary">
                  {answer?.optimization_suggestions}
                </Paragraph>
              </>
            ) : (
              <>
                <Title level={3} className="question-title">
                  <Space>
                    问题{question?.id}
                    {question?.name}
                  </Space>
                </Title>
                <Text className="question-difficulty" type="secondary">
                  难度：{getDifficultyTag(question?.difficult)}
                </Text>
                <Divider />
                <Title className="question-description" level={5}>
                  题目详细描述
                </Title>
                <Paragraph>{question?.description}</Paragraph>
                <Divider />
                <Title className="question-description" level={5}>
                  输入输出格式
                </Title>
                <Text className="question-format">{question?.format}</Text>
                <Divider />
                <Space className="question-example">
                  <Title level={5}>输入示例</Title>
                  <Text code type="secondary">
                    {question?.example.split("\n")[0]}
                  </Text>
                </Space>
                <Divider />
                <Space className="question-output">
                  <Title level={5}>输出示例</Title>
                  <Text code type="secondary">
                    {question?.example.split("\n")[1]}
                  </Text>
                </Space>
                <Divider />
                <Title className="question-tips" level={4}>
                  说明/提示
                </Title>
                <Text className="question-tips" type="secondary">
                  {question?.tips}
                </Text>
              </>
            )}
          </CSSTransition>
        </SwitchTransition>
      </ProCard>
    </Sider>
  );
};

export default QuestionPanel;
