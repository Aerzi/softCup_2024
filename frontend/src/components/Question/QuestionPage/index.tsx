import React, { useState, useEffect } from "react";
import { Form, Input, message, Modal, Select, Space, Table } from "antd";
import {
  deleteQuestion,
  editQuestion,
  getQuestionPage,
} from "../../../services/questionService";
import { IQuestion } from "../type";

interface IPage {
  pageIndex: number;
  pageSize: number;
  total?: number;
  current?: number;
}

const QuestionPage = ({ classId }: { classId: number }) => {
  const [questions, setQuestions] = useState<IQuestion[]>([]);

  const [form] = Form.useForm();

  const [open, setOpen] = useState(false);
  // 创建待编辑数据
  const [editData, setEditData] = useState<IQuestion | null>(null);

  const [loading, setLoading] = useState(false);
  const [pagination, setPagination] = useState<IPage>({
    pageIndex: 1,
    pageSize: 10,
  });

  const onGetQuestionPage = () => {
    setLoading(true);
    getQuestionPage({
      pageIndex: pagination.pageIndex,
      pageSize: pagination.pageSize,
      classId: classId,
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

  // 添加编辑板块
  const onEditQuestion = (data: IQuestion) => {
    setLoading(true);
    editQuestion(data)
      .then((res: any) => {
        if (res.code === 200) {
          message.success(res.message);
          setOpen(false);
          onGetQuestionPage();
        } else {
          message.error(res.message);
        }
      })
      .catch((err: any) => {
        message.error(err.message);
      });
  };

  // 添加删除模块
  const onDeleteQuestion = (id: number) => {
    setLoading(true);
    deleteQuestion(id)
      .then((res: any) => {
        if (res.code === 200) {
          message.success(res.message);
          onGetQuestionPage();
        } else {
          message.error(res.message);
        }
      })
      .catch((err: any) => {
        message.error(err.message);
      });
  };

  const handleDeleteQuestion = (id: number) => {
    Modal.confirm({
      title: "确认删除课程?",
      content: "你确定要删除这个课程吗?",
      okText: "确认",
      cancelText: "取消",
      onOk: () => onDeleteQuestion(id),
    });
  };

  const handleShowModal = (record: IQuestion) => {
    form.resetFields();
    setEditData(record);
    setOpen(true);
    form.setFieldsValue({
      ...record,
    });
  };

  const handleOk = () => {
    form
      .validateFields()
      .then((values) => {
        const data = { ...values, id: editData?.id };
        onEditQuestion(data);
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
    onGetQuestionPage();
  }, [pagination.pageIndex, pagination.pageSize]);

  const handleTableChange = (pagination: any, filters?: any, sorter?: any) => {
    setPagination({
      ...pagination,
      pageIndex: pagination.current,
      pageSize: pagination.pageSize,
    });
    onGetQuestionPage();
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
    },
    {
      title: "输入输出格式",
      dataIndex: "format",
      key: "format",
    },
    {
      title: "提示",
      dataIndex: "tips",
      key: "tips",
    },
    {
      title: "操作",
      key: "action",
      render: (text: any, record: IQuestion) => (
        <Space>
          <a onClick={() => handleShowModal(record)}>编辑</a>
          <a onClick={() => handleDeleteQuestion(record.id)}>删除</a>
        </Space>
      ),
    },
  ];

  return (
    <>
      <Table
        columns={columns}
        dataSource={questions}
        pagination={pagination}
        loading={loading}
        onChange={handleTableChange}
      />
      <Modal
        open={open}
        title="编辑题目"
        onOk={handleOk}
        onCancel={handleCancel}
      >
        <Form
          form={form}
          initialValues={{
            name: editData?.name,
            description: editData?.description,
            format: editData?.format,
            tips: editData?.tips,
            difficult: editData?.difficult,
            example: editData?.example,
          }}
        >
          <Form.Item
            name="name"
            label="题目名称"
            rules={[{ required: true, message: "请输入题目名称!" }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="description"
            label="题目描述"
            rules={[{ required: true, message: "请输入题目描述!" }]}
          >
            <Input.TextArea />
          </Form.Item>
          <Form.Item
            name="difficult"
            label="难度"
            rules={[{ required: true, message: "请选择难度!" }]}
          >
            <Select>
              <Select.Option value={1}>简单</Select.Option>
              <Select.Option value={2}>中等</Select.Option>
              <Select.Option value={3}>困难</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item
            name="format"
            label="输入输出格式"
            rules={[{ required: true, message: "请输入输入输出格式!" }]}
          >
            <Input.TextArea />
          </Form.Item>
          <Form.Item
            name="tips"
            label="提示"
            rules={[{ required: true, message: "请输入提示!" }]}
          >
            <Input.TextArea />
          </Form.Item>
          <Form.Item
            name="example"
            label="示例"
            rules={[{ required: true, message: "请输入示例!" }]}
          >
            <Input.TextArea />
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default QuestionPage;
