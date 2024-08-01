import React, { useState } from "react";
import { Modal, Form, Input, Button, Select, message } from "antd";
import { onAddTeacherProject } from "../../../services/projectService";

interface IProject {
  title: string;
  description: string;
  difficult: number;
}

const ProjectModal: React.FC = () => {
  const [form] = Form.useForm();
  const [visible, setVisible] = useState(false);
  const [loading, setLoading] = useState(false);

  const showModal = () => {
    form.resetFields();
    setVisible(true);
  };

  const handleOk = () => {
    form
      .validateFields()
      .then((values) => {
        setLoading(true);
        onAddTeacherProject({ ...values, userId: 1, classId: 1 }).then(
          (res: any) => {
            if (res.code === 200) {
              message.success("发布项目成功");
              setVisible(false);
              setLoading(false);
              form.resetFields();
            } else {
              message.error("发布项目失败");
              setLoading(false);
            }
          }
        );
      })
      .catch((info) => {
        console.log("Validate Failed:", info);
      });
  };

  const handleCancel = () => {
    form.resetFields();
    setVisible(false);
  };

  return (
    <>
      <Button type="primary" onClick={showModal}>
        发布
      </Button>
      <Modal
        title="发布项目开发任务"
        visible={visible}
        confirmLoading={loading}
        onOk={handleOk}
        onCancel={handleCancel}
      >
        <Form form={form}>
          <Form.Item
            name="name"
            label="项目标题"
            rules={[{ required: true, message: "请输入项目标题!" }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="description"
            label="项目描述"
            rules={[{ required: true, message: "请输入项目描述!" }]}
          >
            <Input.TextArea />
          </Form.Item>
          <Form.Item
            name="difficult"
            label="难度"
            rules={[{ required: true, message: "请选择难度!" }]}
          >
            <Select>
              <Select.Option value="60">简单</Select.Option>
              <Select.Option value="80">中等</Select.Option>
              <Select.Option value="100">困难</Select.Option>
            </Select>
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default ProjectModal;
