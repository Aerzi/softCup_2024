import React, { useState, useEffect } from "react";
import { Form, Input, message, Modal, Select, Space, Table } from "antd";
import { ILog } from "../type";
import { delLog, getLogPage } from "../../../services/LogService";

interface IPage {
  pageIndex: number;
  pageSize: number;
  total?: number;
  current?: number;
}

const LogControl = () => {
  const [logs, setLogs] = useState<ILog[]>([]);

  const [loading, setLoading] = useState(false);
  const [pagination, setPagination] = useState<IPage>({
    pageIndex: 1,
    pageSize: 10,
  });

  const onGetLogPage = () => {
    setLoading(true);
    getLogPage({
      pageIndex: pagination.pageIndex,
      pageSize: pagination.pageSize,
    })
      .then((res: any) => {
        if (res.code === 200) {
          setLogs(res?.response?.list);
          setPagination({
            ...pagination,
            total: res.response?.total,
          });
        } else {
          // 这里可以根据实际的错误处理逻辑来调整
          console.error(res.message);
        }
      })
      .catch((err: any) => {
        // 这里可以根据实际的错误处理逻辑来调整
        console.error(err.message);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  // 添加删除模块
  const onDeleteLog = (id: number) => {
    setLoading(true);
    delLog(id)
      .then((res: any) => {
        if (res.code === 200) {
          message.success(res.message);
          onGetLogPage();
        } else {
          message.error(res.message);
        }
      })
      .catch((err: any) => {
        message.error(err.message);
      });
  };

  const handleDelete = (id: number) => {
    Modal.confirm({
      title: "确认删除",
      content: "你确定要删除这个日志吗?",
      okText: "确认",
      cancelText: "取消",
      onOk: () => onDeleteLog(id),
    });
  };

  useEffect(() => {
    onGetLogPage();
  }, [pagination.pageIndex, pagination.pageSize]);

  const handleTableChange = (pagination: any, filters?: any, sorter?: any) => {
    setPagination({
      ...pagination,
      pageIndex: pagination.current,
      pageSize: pagination.pageSize,
    });
    onGetLogPage();
  };

  const columns = [
    {
      title: "日志ID",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "用户名",
      dataIndex: "userName",
      key: "userName",
    },
    {
      title: "内容",
      dataIndex: "content",
      key: "content",
    },
    {
      title: "发生时间",
      dataIndex: "createTime",
      key: "createTime",
    },
    {
      title: "操作",
      key: "action",
      render: (text: any, record: ILog) => (
        <Space>
          <a onClick={() => handleDelete(record?.id)}>删除</a>
        </Space>
      ),
    },
  ];

  return (
    <>
      <Table
        columns={columns}
        dataSource={logs}
        pagination={pagination}
        loading={loading}
        onChange={handleTableChange}
      />
    </>
  );
};

export default LogControl;
