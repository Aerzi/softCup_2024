import React, { useState, useEffect } from "react";
import { message, Table, Modal, Form, Input, Space } from "antd";
import {
  getClassPage,
  editClass,
  deleteClass,
} from "../../../services/classService";
import { IClassRoom } from "../type";

interface IPage {
  pageIndex: number;
  pageSize: number;
  total?: number;
  current?: number;
}

const PageClass = () => {
  // 加载本地数据源
  const [classes, setClasses] = useState<IClassRoom[]>([]);
  const [loading, setLoading] = useState(false);
  const [pagination, setPagination] = useState<IPage>({
    pageIndex: 1,
    pageSize: 5,
  });

  const [form] = Form.useForm();

  const [open, setOpen] = useState(false);
  // 创建待编辑数据
  const [editData, setEditData] = useState<IClassRoom | null>(null);

  const onGetClassPage = () => {
    setLoading(true);
    getClassPage(pagination)
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
    onGetClassPage();
  }, [pagination.pageIndex]);

  const handleTableChange = (pagination: IPage) => {
    console.log("pagination", pagination);
    setPagination({
      ...pagination,
      pageIndex: pagination.current,
    });
  };

  const handleShowModal = (record: IClassRoom) => {
    form.resetFields();
    setEditData(record);
    setOpen(true);
  };

  const handleOk = () => {
    form
      .validateFields()
      .then((values) => {
        const data = { ...values, id: editData?.id };
        editClass(data).then((res: any) => {
          if (res.code === 200) {
            onGetClassPage();
            setOpen(false);
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
    setOpen(false);
  };

  const handleDelete = (id: number) => {
    Modal.confirm({
      title: "确认删除课程?",
      content: "你确定要删除这个课程吗?",
      okText: "确认",
      cancelText: "取消",
      onOk: () =>
        deleteClass(id).then((res: any) => {
          if (res.code === 200) {
            onGetClassPage();
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
      minWidth: 400,
    },
    {
      title: "课堂描述",
      dataIndex: "description",
      key: "description",
      minWidth: 400,
    },
    {
      title: "操作",
      key: "action",
      render: (text: any, record: IClassRoom) => (
        <Space>
          <a onClick={() => handleShowModal(record)}>编辑</a>
          <a onClick={() => handleDelete(record.id)}>删除</a>
        </Space>
      ),
      minWidth: 230,
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
        open={open}
        title="编辑课程"
        onOk={handleOk}
        onCancel={handleCancel}
      >
        <Form
          form={form}
          initialValues={{
            name: editData?.name,
            description: editData?.description,
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
