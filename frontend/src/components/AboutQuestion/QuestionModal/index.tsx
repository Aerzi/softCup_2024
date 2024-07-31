import React, { useEffect, useState } from "react";
import { Modal, Form, Input, Button, message, Select } from "antd";
import { addTeacherQuestion } from "../../../services/questionService";
import { onGetTeacherClassList } from "../../../services/classService";

interface IQuestion {
  title: string;
  content: string;
  difficulty: string;
}

const QuestionModal = () => {
  const [form] = Form.useForm();
  const [visible, setVisible] = useState(false);
  const [loading, setLoading] = useState(false);

  const showModal = () => {
    form.resetFields();
    setVisible(true);
  };

  const [classList, setClassList] = useState([]);

  const handleOk = () => {
    form
      .validateFields()
      .then((values) => {
        setLoading(true);
        addTeacherQuestion(values).then((res: any) => {
          if (res.code === 200) {
            message.success("发布问题成功");
            setVisible(false);
            setLoading(false);
            form.resetFields();
          } else {
            message.error("发布问题失败");
            setLoading(false);
          }
        });
      })
      .catch((info) => {
        console.log("Validate Failed:", info);
      });
  };

  const onGetClassList = () => {
    onGetTeacherClassList()
      .then((res: any) => {
        if (res.code === 200) {
          setClassList(res.response);
        } else {
          message.error(res.message);
        }
      })
      .catch((err: any) => {
        message.error(err.message);
      });
  };

  const handleCancel = () => {
    form.resetFields();
    setVisible(false);
  };

  let flag = false;
  useEffect(() => {
    if (!flag) {
      onGetClassList();
      flag = true;
    }
  }, []);

  return (
    <>
      <Button type="primary" onClick={showModal}>
        发布
      </Button>
      <Modal
        title="发布编程习题任务"
        visible={visible}
        confirmLoading={loading}
        onOk={handleOk}
        onCancel={handleCancel}
      >
        <Form form={form}>
          <Form.Item
            name="classId"
            label="课堂编号"
            rules={[{ required: true, message: "请选择课堂编号!" }]}
          >
            <Select>
              {classList.map((item: any) => (
                <Select.Option value={item.id} key={item.id}>
                  {item.name}
                </Select.Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item
            name="type"
            label="类型"
            rules={[{ required: true, message: "请选择类型!" }]}
          >
            <Select>
              <Select.Option value="objective">客观题</Select.Option>
              <Select.Option value="programming">编程题</Select.Option>
            </Select>
          </Form.Item>
          <Form.Item
            name="name"
            label="题目标题"
            rules={[{ required: true, message: "请输入题目标题!" }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="description"
            label="题目描述"
            rules={[{ required: false }]}
          >
            <Input.TextArea />
          </Form.Item>
          <Form.Item
            name="content"
            label="题目内容"
            rules={[{ required: true, message: "请输入题干内容!" }]}
          >
            <Input.TextArea />
          </Form.Item>
          <Form.Item
            name="correct"
            label="正确答案"
            rules={[{ required: true, message: "请输入答案解析!" }]}
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

export default QuestionModal;
