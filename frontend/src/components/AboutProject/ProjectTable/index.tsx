import React, { useState, useEffect } from "react";
import { Table } from "antd";
import { onGetProjectPage } from "../../../services/projectService";

interface IProject {
  id: number;
  title: string;
  description: string;
  difficulty: string;
}

interface IPage {
  pageIndex: number;
  pageSize: number;
  total: number;
}

const ProjectPage: React.FC = () => {
  const [projects, setProjects] = useState<IProject[]>([]);
  const [loading, setLoading] = useState(false);
  const [pagination, setPagination] = useState<IPage>({
    pageIndex: 0,
    pageSize: 10,
    total: 0,
  });

  const fetchProjects = () => {
    setLoading(true);
    onGetProjectPage({
      pageIndex: pagination.pageIndex.toString(),
      pageSize: pagination.pageSize.toString(),
      classId: 1,
      userId: 1,
    })
      .then((res: any) => {
        if (res.code === 200) {
          setProjects(res.response.list);
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
    fetchProjects();
  }, [pagination.pageIndex, pagination.pageSize]);

  const handleTableChange = (pagination: IPage, filters: any, sorter: any) => {
    setPagination({
      ...pagination,
    });
    fetchProjects();
  };

  const columns = [
    {
      title: "项目标题",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "项目描述",
      dataIndex: "description",
      key: "description",
    },
    {
      title: "难度",
      dataIndex: "difficult",
      key: "difficult",
    },
    {
      title: "操作",
      key: "action",
      render: (text: any, record: IProject) => (
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
      dataSource={projects}
      pagination={pagination}
      loading={loading}
      onChange={handleTableChange}
    />
  );
};

export default ProjectPage;
