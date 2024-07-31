import React, { useState, useEffect } from "react";
import { Table } from "antd";
import { getPageTeacherQuestion } from "../../../services/questionService";

interface IQuestion {
  id: number;
  title: string;
  content: string;
  difficulty: string;
}

interface IPage {
  pageIndex: number;
  pageSize: number;
  total: number;
}

const QuestionPage = () => {
  const [questions, setQuestions] = useState<IQuestion[]>([]);
  const [loading, setLoading] = useState(false);
  const [pagination, setPagination] = useState<IPage>({
    pageIndex: 1,
    pageSize: 10,
    total: 0,
  });

  const fetchQuestions = () => {
    setLoading(true);
    getPageTeacherQuestion(pagination.pageIndex, pagination.pageSize, 1)
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

  useEffect(() => {
    fetchQuestions();
  }, [pagination.pageIndex, pagination.pageSize]);

  const handleTableChange = (pagination: any, filters: any, sorter: any) => {
    setPagination((prevState) => ({
      ...prevState,
      ...pagination,
    }));
    fetchQuestions();
  };

  const columns = [
    {
      title: "题目标题",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "题目内容",
      dataIndex: "content",
      key: "content",
    },
    {
      title: "难度",
      dataIndex: "difficult",
      key: "difficult",
    },
    {
      title: "操作",
      key: "action",
      render: (text: any, record: IQuestion) => (
        <span>
          <a href="#">编辑</a>
          <span className="ant-divider" />
          <a href="#">删除</a>
        </span>
      ),
    },
  ];

  return (
    <Table
      columns={columns}
      dataSource={questions}
      pagination={pagination}
      loading={loading}
      onChange={handleTableChange}
    />
  );
};

export default QuestionPage;
