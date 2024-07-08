import React, { useState } from "react";
import "./index.less";
import { Button, Form, Input, Modal, Tabs } from "antd";
import type { TabsProps } from "antd";

interface UserModalData {
  isOpen: boolean;
  setIsOpen: (arg0: boolean) => void;
}

type FieldType = {
  username?: string;
  password?: string;
  remember?: string;
};

const UserModal = ({ isOpen, setIsOpen }: UserModalData) => {
  const [login] = Form.useForm();

  const [register] = Form.useForm();

  const items: TabsProps["items"] = [
    {
      key: "1",
      label: "登录",
      children: (
        <div>
          <Form name="login" form={login} layout="vertical">
            <Form.Item<FieldType>
              label="用户名"
              name="username"
              rules={[
                { required: true, message: "Please input your username!" },
              ]}
            >
              <Input />
            </Form.Item>

            <Form.Item<FieldType>
              label="密码"
              name="password"
              rules={[
                { required: true, message: "Please input your password!" },
              ]}
            >
              <Input.Password />
            </Form.Item>

            <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
              <Button type="primary" htmlType="submit">
                登录
              </Button>
            </Form.Item>
          </Form>
        </div>
      ),
    },
    {
      key: "2",
      label: "注册",
      children: (
        <div>
          <Form name="login" form={login} layout="vertical">
            <Form.Item<FieldType>
              label="用户名"
              name="username"
              rules={[
                { required: true, message: "Please input your username!" },
              ]}
            >
              <Input />
            </Form.Item>

            <Form.Item<FieldType>
              label="密码"
              name="password"
              rules={[
                { required: true, message: "Please input your password!" },
              ]}
            >
              <Input.Password />
            </Form.Item>

            <Form.Item<FieldType>
              label="确认密码"
              name="password"
              rules={[
                {
                  required: true,
                  message: "Please input your password again!",
                },
              ]}
            >
              <Input.Password />
            </Form.Item>

            <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
              <Button type="primary" htmlType="submit">
                注册
              </Button>
            </Form.Item>
          </Form>
        </div>
      ),
    },
  ];

  const handleOk = () => {
    setIsOpen(false);
  };
  ``;
  const handleCancel = () => {
    setIsOpen(false);
  };

  const onTabsChange = (key: string) => {
    console.log(key);
  };

  return (
    <div>
      <Modal
        title="登录/注册"
        width={600}
        height={400}
        open={isOpen}
        onOk={handleOk}
        onCancel={handleCancel}
        footer={null}
        className="xf-user__modal"
      >
        <Tabs defaultActiveKey="1" items={items} onChange={onTabsChange} />
      </Modal>
    </div>
  );
};

export default UserModal;
