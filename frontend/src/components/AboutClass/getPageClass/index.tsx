import React, { useState, useEffect } from "react";
import { message, Table, Modal, Form, Input, Space, Button } from "antd";
import {
  onGetPageClass,
  onEditClass,
  onDeleteClass,
} from "../../../services/classService";

interface IClass {
  id: number;
  name: string;
  description: string;
}

interface IPage {
  pageIndex: number;
  pageSize: number;
  total: number;
}

const PageClass = () => {
  const [classes, setClasses] = useState<IClass[]>([]);
  const [loading, setLoading] = useState(false);
  const [pagination, setPagination] = useState<IPage>({
    pageIndex: 1,
    pageSize: 10,
    total: 0,
  });
  const [form] = Form.useForm();
  const [visible, setVisible] = useState(false);
  const [editClass, setEditClass] = useState<IClass | null>(null);

  const fetchClasses = () => {
    setLoading(true);
    onGetPageClass(pagination.pageIndex, pagination.pageSize)
      .then((res: any) => {
        if (res.code === 200) {
          setClasses(res.response.list);
          setPagination({
            ...pagination,
            total: res.response.total,
          });
          message.success(res.message);
        } else {
          message.error(res.message);
        }
      })
      .catch((err: any) => {
        message.error(err.message);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  useEffect(() => {
    fetchClasses();
  }, [pagination.pageIndex, pagination.pageSize]);

  const handleTableChange = (pagination: IPage, filters: any, sorter: any) => {
    setPagination({
      ...pagination,
    });
    fetchClasses();
  };

  const showModal = (record: IClass) => {
    form.resetFields();
    setEditClass(record);
    setVisible(true);
  };

  const handleOk = () => {
    form
      .validateFields()
      .then((values) => {
        const data = { ...values, id: editClass?.id };
        onEditClass(data).then((res: any) => {
          if (res.code === 200) {
            fetchClasses();
            setVisible(false);
          } else {
            message.error(res.message);
          }
        });
      })
      .catch((info) => {
        console.log("Validate Failed:", info);
      });
  };

  const handleCancel = () => {
    form.resetFields();
    setVisible(false);
  };

  const handleDelete = (id: number) => {
    Modal.confirm({
      title: "确认删除课程?",
      content: "你确定要删除这个课程吗?",
      okText: "确认",
      cancelText: "取消",
      onOk: () =>
        onDeleteClass(id).then((res: any) => {
          if (res.code === 200) {
            fetchClasses();
          } else {
            message.error(res.message);
          }
        }),
    });
  };

  const columns = [
    {
      title: "课堂名称",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "课堂描述",
      dataIndex: "description",
      key: "description",
    },
    {
      title: "操作",
      key: "action",
      render: (text: any, record: IClass) => (
        <Space>
          <a onClick={() => showModal(record)}>编辑</a>
          <a onClick={() => handleDelete(record.id)}>删除</a>
        </Space>
      ),
    },
  ];

  return (
    <>
      <Table
        columns={columns}
        dataSource={classes}
        pagination={pagination}
        loading={loading}
        onChange={handleTableChange}
      />
      <Modal
        title="编辑课程"
        visible={visible}
        onOk={handleOk}
        onCancel={handleCancel}
      >
        <Form
          form={form}
          initialValues={{
            name: editClass?.name,
            description: editClass?.description,
          }}
        >
          <Form.Item
            name="name"
            label="课堂名称"
            rules={[{ required: true, message: "请输入课堂名称!" }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="description"
            label="课堂描述"
            rules={[{ required: true, message: "请输入课堂描述!" }]}
          >
            <Input.TextArea />
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default PageClass;
