import React, { useState, useEffect } from "react";
import { Form, Input, message, Modal, Select, Space, Table } from "antd";
import { IUser } from "../type";
import {
  addUser,
  editUser,
  getUserPage,
} from "../../../services/userControlService";

interface IPage {
  pageIndex: number;
  pageSize: number;
  total?: number;
  current?: number;
}

const UserControl = () => {
  const [users, setUsers] = useState<IUser[]>([]);

  const [form] = Form.useForm();

  const [open, setOpen] = useState(false);
  // 创建待编辑数据
  const [editData, setEditData] = useState<IUser | null>(null);

  const [loading, setLoading] = useState(false);
  const [pagination, setPagination] = useState<IPage>({
    pageIndex: 1,
    pageSize: 10,
  });

  const onGetUserPage = () => {
    setLoading(true);
    getUserPage({
      pageIndex: pagination.pageIndex,
      pageSize: pagination.pageSize,
    })
      .then((res: any) => {
        if (res.code === 200) {
          setUsers(res.response?.list);
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
      });
    setLoading(false);
  };

  // 添加编辑板块
  const onEditUser = (data: IUser) => {
    setLoading(true);
    editUser(data)
      .then((res: any) => {
        if (res.code === 200) {
          message.success(res.message);
          setOpen(false);
          onGetUserPage();
        } else {
          message.error(res.message);
        }
      })
      .catch((err: any) => {
        message.error(err.message);
      });
  };

  const onNewUser = (data: IUser) => {
    setLoading(true);
    addUser(data)
      .then((res: any) => {
        if (res.code === 200) {
          message.success(res.message);
          setOpen(false);
          onGetUserPage();
        } else {
          message.error(res.message);
        }
      })
      .catch((err: any) => {
        message.error(err.message);
      });
  };

  const handleShowModal = (record: IUser, type: string) => {
    form.resetFields();
    if (type === "edit") {
      setEditData(record);
      setOpen(true);
      form.setFieldsValue({
        ...record,
      });
    } else {
      const id = editData?.id;
      setEditData(null);
      setOpen(true);
    }
  };

  const handleOk = () => {
    form
      .validateFields()
      .then((values) => {
        if (editData === null) {
          onNewUser(values);
          return;
        }
        const data = { ...values, id: editData?.id };
        onEditUser(data);
      })
      .catch((info) => {
        console.log("Validate Failed:", info);
      });
  };

  const handleCancel = () => {
    form.resetFields();
    setEditData(null);
    setOpen(false);
  };

  useEffect(() => {
    onGetUserPage();
  }, [pagination.pageIndex, pagination.pageSize]);

  const handleTableChange = (pagination: any, filters?: any, sorter?: any) => {
    setPagination({
      ...pagination,
      pageIndex: pagination.current,
      pageSize: pagination.pageSize,
    });
    onGetUserPage();
  };

  const columns = [
    {
      title: "用户名",
      dataIndex: "userName",
      key: "userName",
    },
    {
      title: "电话",
      dataIndex: "phone",
      key: "phone",
    },
    {
      title: "年龄",
      dataIndex: "age",
      key: "age",
    },
    {
      title: "状态",
      dataIndex: "status",
      key: "status",
      render: (text: any) => (text === 1 ? "启用" : "禁用"),
    },
    {
      title: "操作",
      key: "action",
      render: (record: IUser) => (
        <Space>
          <a onClick={() => handleShowModal(record, "add")}>新增</a>
          <a onClick={() => handleShowModal(record, "edit")}>编辑</a>
        </Space>
      ),
    },
  ];

  return (
    <>
      <Table
        columns={columns}
        dataSource={users}
        pagination={pagination}
        loading={loading}
        onChange={handleTableChange}
      />
      <Modal open={open} onOk={handleOk} onCancel={handleCancel}>
        <Form
          form={form}
          initialValues={{
            name: editData?.userName,
            phone: editData?.phone,
            age: editData?.age,
            status: editData?.status,
          }}
        >
          <Form.Item
            name="name"
            label="用户名"
            rules={[{ required: true, message: "请输入用户名" }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="phone"
            label="电话"
            rules={[{ required: true, message: "请输入电话" }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="age"
            label="年龄"
            rules={[{ required: true, message: "请输入年龄" }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="status"
            label="状态"
            rules={[{ required: true, message: "请选择状态" }]}
          >
            <Select>
              <Select.Option value={1}>启用</Select.Option>
              <Select.Option value={0}>禁用</Select.Option>
            </Select>
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default UserControl;
