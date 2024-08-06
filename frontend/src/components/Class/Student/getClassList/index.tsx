import React, { useEffect, useState } from "react";
import {
  Button,
  Card,
  Col,
  Divider,
  message,
  Row,
  Space,
  Table,
  Tag,
  Typography,
} from "antd";
import { IClassRoom } from "../../type";
import {
  getStudentClassList,
  joinClass,
} from "../../../../services/classService";
import { ProCard } from "@ant-design/pro-components";
import { IQuestion } from "../../../Question/type";
import { getStudentQuestionPage } from "../../../../services/questionService";
import { setLocalData } from "../../../../utils/Storage";
import { IFile } from "../../../File/type";
import { getStudentFilePage } from "../../../../services/fileService";
import FilePreviewer from "../../../DroppedCard/FilePreviewer";

const { Title, Paragraph, Text, Link } = Typography;

interface IPage {
  pageIndex: number;
  pageSize: number;
  total?: number;
  current?: number;
}

const ClassPanel = () => {
  const [classList, setClassList] = useState<IClassRoom[]>([]);
  // 状态：当前课堂的详细信息
  const [currentClassInfo, setCurrentClassInfo] = useState<IClassRoom | null>(
    null
  );

  const [questions, setQuestions] = useState<IQuestion[]>([]);
  const [files, setFiles] = useState<IFile[]>([]);

  const [pagination, setPagination] = useState<IPage>({
    pageIndex: 1,
    pageSize: 5,
  });
  const [filePagination, setFilePagination] = useState<IPage>({
    pageIndex: 1,
    pageSize: 5,
  });

  const [loading, setLoading] = useState(false);
  const [fileLoading, setFileLoading] = useState(false);

  const onGetStudentClassList = () => {
    getStudentClassList()
      .then((res: any) => {
        if (res.code === 200) {
          setClassList(res.response);
        } else {
          message.error(res.message);
        }
      })
      .catch((err) => {
        message.error(err.message);
      });
  };

  // 获取问题列表
  const onGetStudentQuestionPage = () => {
    setLoading(true);
    getStudentQuestionPage({
      pageIndex: pagination.pageIndex,
      pageSize: pagination.pageSize,
      classId: currentClassInfo?.id,
    })
      .then((res: any) => {
        if (res.code === 200) {
          setQuestions(res.response.list);
          setPagination({
            ...pagination,
            total: res.response.total,
          });
        } else {
          // 这里可以根据实际的错误处理逻辑来调整
          console.error(res.message);
        }
      })
      .catch((err: any) => {
        // 这里可以根据实际的错误处理逻辑来调整
        console.error(err.message);
      });
    setLoading(false);
  };

  // 获取文件列表
  const onGetStudentFilePage = () => {
    setFileLoading(true);
    getStudentFilePage({
      pageIndex: filePagination.pageIndex,
      pageSize: filePagination.pageSize,
      classId: currentClassInfo?.id,
    })
      .then((res: any) => {
        if (res.code === 200) {
          setFiles(res.response.list);
          setFilePagination({
            ...filePagination,
            total: res.response.total,
          });
        } else {
          // 这里可以根据实际的错误处理逻辑来调整
          message.error("[file]" + res.message);
        }
      })
      .catch((err: any) => {
        // 这里可以根据实际的错误处理逻辑来调整
        message.error("[file]" + err.message);
      })
      .finally(() => {
        setFileLoading(false);
      });
  };

  const onJoinClass = () => {
    // 加入课堂
    joinClass(currentClassInfo?.id)
      .then((res: any) => {
        if (res.code === 200) {
          message.success(res.message);
        } else {
          message.error(res.message);
        }
      })
      .catch((err) => {
        message.error(err.message);
      })
      .finally(() => {
        onGetStudentClassList();
      });
  };

  const handleTableChange = (pagination: any, filters?: any, sorter?: any) => {
    setPagination({
      ...pagination,
      pageIndex: pagination.current,
      pageSize: pagination.pageSize,
    });
    onGetStudentQuestionPage();
  };

  const handleFileTableChange = (
    pagination: any,
    filters?: any,
    sorter?: any
  ) => {
    setPagination({
      ...pagination,
      pageIndex: pagination.current,
      pageSize: pagination.pageSize,
    });
    onGetStudentFilePage();
  };

  useEffect(() => {
    onGetStudentClassList();
  }, []);

  useEffect(() => {
    onGetStudentQuestionPage();
    onGetStudentFilePage();
  }, [currentClassInfo]);

  const handleClassCardClick = (value: number) => {
    // 处理选中课程的逻辑
    setCurrentClassInfo(
      classList.find((classItem) => classItem.id === value) || null
    );
  };

  const getDifficultyTag = (level: any) => {
    const color =
      level === "1" ? "#87d068" : level === "2" ? "#faad14" : "#f5222d";
    const text = level === "1" ? "简单" : level === "2" ? "中等" : "困难";
    return <Tag color={color}>{text}</Tag>;
  };

  const columns = [
    {
      title: "题目标题",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "详细描述",
      dataIndex: "description",
      key: "description",
    },
    {
      title: "难度",
      dataIndex: "difficult",
      key: "difficult",
      render: (record: number) => getDifficultyTag(record),
    },
    {
      title: "操作",
      key: "action",
      render: (text: any, record: IQuestion) => (
        <Space>
          <a
            href="#/codePen"
            target="_blank"
            rel="noopener noreferrer"
            onClick={() => {
              setLocalData("questionData", record);
            }}
          >
            进入答题
          </a>
        </Space>
      ),
      minWidth: 100,
    },
  ];

  const fileColumns = [
    {
      title: "文件标题",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "详细描述",
      dataIndex: "description",
      key: "description",
    },
    {
      title: "操作",
      key: "action",
      render: (text: any, record: IFile) => (
        <Space>
          <FilePreviewer id={record?.id} />
        </Space>
      ),
      width: 100,
    },
  ];

  return (
    <ProCard style={{ marginBlockStart: 8 }} gutter={8} ghost>
      <ProCard
        style={{ height: "740px", padding: "10px 20px", overflow: "auto" }}
        title="课堂列表"
      >
        <Row gutter={16} style={{ marginTop: "20px" }}>
          {classList.map((classItem) => (
            <Col span={12} key={classItem.id}>
              <Card
                hoverable
                onClick={() => handleClassCardClick(classItem.id)}
                title={classItem.name}
                style={{
                  height: "180px",
                  cursor: "pointer",
                  padding: "10px 0",
                  margin: "10px",
                }}
              >
                {/* 这里可以添加更多课堂信息展示 */}
                <div>课堂描述:{classItem.description}</div>
                <div>
                  创建时间:{new Date(classItem.createTime).toLocaleString()}
                </div>
              </Card>
            </Col>
          ))}
        </Row>
      </ProCard>
      <ProCard
        style={{ height: "740px", padding: "10px 20px", overflow: "auto" }}
        title="课堂详情"
      >
        {currentClassInfo && (
          <div>
            <Space
              direction="horizontal"
              size="large"
              style={{
                width: "100%",
                justifyContent: "space-between",
                alignItems: "center",
              }}
            >
              <Title level={2}>{currentClassInfo.name}</Title>
              <Button
                type="primary"
                style={{ float: "right" }}
                disabled={currentClassInfo?.is_add === true ? true : false}
                onClick={onJoinClass}
              >
                加入课堂
              </Button>
            </Space>
            <Title level={3}>课程描述</Title>
            <Paragraph style={{ marginBottom: "10px", fontSize: "1.2rem" }}>
              {currentClassInfo.description}
            </Paragraph>
            <Divider />
            <Title level={3}>创建时间</Title>
            <Text type="secondary">
              {new Date(currentClassInfo.createTime).toLocaleString()}
            </Text>
            <Divider />
            {/* 课程内部的题目 */}
            <Title level={3}>题目展示</Title>
            <Table
              columns={columns}
              dataSource={questions}
              pagination={pagination}
              loading={loading}
              onChange={handleTableChange}
            />
            <Divider />
            <Title level={3}>资源目录</Title>
            <Table
              columns={fileColumns}
              dataSource={files}
              pagination={filePagination}
              loading={fileLoading}
              onChange={handleFileTableChange}
            />
          </div>
        )}
      </ProCard>
    </ProCard>
  );
};

export default ClassPanel;
