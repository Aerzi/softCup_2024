import React, { useState, useEffect } from "react";
import { Table } from "antd";
import { StudentGetQuestionPage } from "../../../services/questionService";

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

const StudentQuestion = ({ classId }: { classId: number }) => {
  const [questions, setQuestions] = useState<IQuestion[]>([]);
  const [loading, setLoading] = useState(false);
  const [pagination, setPagination] = useState<IPage>({
    pageIndex: 1,
    pageSize: 10,
    total: 0,
  });

  const fetchQuestions = () => {
    setLoading(true);
    StudentGetQuestionPage({
      pageIndex: pagination.pageIndex,
      pageSize: pagination.pageSize,
      classId,
    })
      .then((res: any) => {
        if (res.code === 200) {
          setQuestions(res.response.list);
          setPagination({
            ...pagination,
            total: res.response.total,
          });
        } else {
          console.error(res.message);
        }
      })
      .catch((err: any) => {
        console.error(err.message);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchQuestions();
  }, [pagination.pageIndex, pagination.pageSize, classId]);

  const handleTableChange = (pagination: IPage, filters: any, sorter: any) => {
    setPagination({
      ...pagination,
    });
    fetchQuestions();
  };

  const columns = [
    {
      title: "题目标题",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "题目描述",
      dataIndex: "description",
      key: "description",
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
          <a href="#">开始作答</a>
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

export default StudentQuestion;
