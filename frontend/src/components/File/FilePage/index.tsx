import React, { useState, useEffect } from "react";
import { Form, Input, message, Modal, Select, Space, Table } from "antd";
import { IFile } from "../type";
import {
  deleteFile,
  editFile,
  getFilePage,
} from "../../../services/fileService";

interface IPage {
  pageIndex: number;
  pageSize: number;
  total?: number;
  current?: number;
}

const FilePage = ({ classId }: { classId: number }) => {
  const [files, setFiles] = useState<IFile[]>([]);

  const [form] = Form.useForm();

  const [open, setOpen] = useState(false);
  // 创建待编辑数据
  const [editData, setEditData] = useState<IFile | null>(null);

  const [loading, setLoading] = useState(false);
  const [pagination, setPagination] = useState<IPage>({
    pageIndex: 1,
    pageSize: 5,
  });

  const onGetFilePage = () => {
    setLoading(true);
    getFilePage({
      pageIndex: pagination.pageIndex,
      pageSize: pagination.pageSize,
      classId: classId,
    })
      .then((res: any) => {
        if (res.code === 200) {
          setFiles(res.response.list);
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
  const onEditFile = (data: IFile) => {
    setLoading(true);
    editFile(data)
      .then((res: any) => {
        if (res.code === 200) {
          message.success(res.message);
          setOpen(false);
          onGetFilePage();
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
    deleteFile(id)
      .then((res: any) => {
        if (res.code === 200) {
          message.success(res.message);
          onGetFilePage();
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

  const handleShowModal = (record: IFile) => {
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
        onEditFile(data);
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
    onGetFilePage();
  }, [pagination.pageIndex, pagination.pageSize]);

  const handleTableChange = (pagination: any, filters?: any, sorter?: any) => {
    setPagination({
      ...pagination,
      pageIndex: pagination.current,
      pageSize: pagination.pageSize,
    });
    onGetFilePage();
  };

  const columns = [
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
      title: "文件格式",
      dataIndex: "extension",
      key: "extension",
    },
    {
      title: "文件类型",
      dataIndex: "type",
      key: "type",
    },
    {
      title: "操作",
      key: "action",
      render: (text: any, record: IFile) => (
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
        dataSource={files}
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
        </Form>
      </Modal>
    </>
  );
};

export default FilePage;
