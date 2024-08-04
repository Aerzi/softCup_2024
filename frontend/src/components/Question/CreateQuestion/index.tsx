import React, { useEffect, useState } from "react";
import { Button, Form, Input, message, Select } from "antd";
import { IQuestion } from "../type";
import { addQuestion } from "../../../services/questionService";

const CreateQuestion = ({ classId }: { classId: number }) => {
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);

  const onFinish = (values: IQuestion) => {
    setLoading(true);
    const questionList = {
      ...values,
      classId: classId,
    };
    addQuestion(questionList)
      .then((res: any) => {
        if (res.code === 200) {
          message.success(res.message);
          form.resetFields();
          setLoading(false);
        } else {
          message.error(res.message);
        }
      })
      .catch((err) => {
        message.error(err.message);
      });
  };

  return (
    <>
      <Form form={form} onFinish={onFinish}>
        <Form.Item
          name="name"
          label="题目标题"
          rules={[{ required: true, message: "请输入简短的题目标题!" }]}
          style={{ width: "100%", padding: "0 5%", height: "40px" }}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="description"
          label="题目描述"
          rules={[{ required: true, message: "请输入详细的题目描述!" }]}
          style={{ width: "100%", padding: "0 5%", height: "100px" }}
        >
          <Input.TextArea style={{ height: "90px" }} />
        </Form.Item>
        <Form.Item
          name="format"
          label="输入输出格式"
          rules={[
            {
              required: true,
              message: "请输入输入/输出格式，中间使用/进行分隔！",
            },
          ]}
          style={{ width: "100%", padding: "0 5%", height: "70px" }}
        >
          <Input.TextArea />
        </Form.Item>
        <Form.Item
          name="example"
          label="输入输出样例"
          rules={[
            {
              required: true,
              message: "请输入输入/输出样例，中间使用/进行分隔！",
            },
          ]}
          style={{ width: "100%", padding: "0 5%", height: "70px" }}
        >
          <Input.TextArea />
        </Form.Item>
        <Form.Item
          name="difficult"
          label="难度"
          rules={[{ required: true, message: "请选择难度!" }]}
          style={{ width: "100%", padding: "0 5%", height: "40px" }}
        >
          <Select>
            <Select.Option value={1}>简单</Select.Option>
            <Select.Option value={2}>中等</Select.Option>
            <Select.Option value={3}>困难</Select.Option>
          </Select>
        </Form.Item>
        <Form.Item
          name="tips"
          label="提示信息"
          rules={[
            {
              required: true,
              message: "请输入提示信息！",
            },
          ]}
          style={{ width: "100%", padding: "0 5%", height: "70px" }}
        >
          <Input.TextArea />
        </Form.Item>
        <Form.Item style={{ width: "100%", padding: "0 5%", height: "40px" }}>
          <Button type="primary" loading={loading} htmlType="submit">
            创建任务
          </Button>
        </Form.Item>
      </Form>
    </>
  );
};

export default CreateQuestion;
