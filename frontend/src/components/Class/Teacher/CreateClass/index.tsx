import React, { useState } from "react";
import { Form, Input, Button, message } from "antd";
import { createClass } from "../../../../services/classService";
import { IClassRoom } from "../../type";

const CreateClass = () => {
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);

  const onFinish = (values: IClassRoom) => {
    setLoading(true);
    createClass(values)
      .then((res: any) => {
        if (res.code === 200) {
          message.success("创建课堂成功！");
          form.resetFields();
          setLoading(false);
        } else {
          message.error(res.message);
          setLoading(false);
        }
      })
      .catch((err: any) => {
        message.error(err.message);
        setLoading(false);
      });
  };

  return (
    <Form
      form={form}
      onFinish={onFinish}
      autoComplete="off"
      size="large"
      layout="vertical"
      style={{ width: "100%", minHeight: "400px" }}
    >
      <Form.Item
        name="name"
        label="课堂名称"
        rules={[{ required: true, message: "请输入课堂名称!" }]}
        style={{ width: "100%", padding: "0 5%", height: "70px" }}
      >
        <Input />
      </Form.Item>
      <Form.Item
        name="description"
        label="课堂描述"
        rules={[{ required: true, message: "请输入课堂描述!" }]}
        style={{ width: "100%", padding: "0 5%", height: "140px" }}
      >
        <Input.TextArea />
      </Form.Item>
      <Form.Item style={{ width: "100%", padding: "0 5%", height: "40px" }}>
        <Button type="primary" loading={loading} htmlType="submit">
          创建课堂
        </Button>
      </Form.Item>
    </Form>
  );
};

export default CreateClass;
