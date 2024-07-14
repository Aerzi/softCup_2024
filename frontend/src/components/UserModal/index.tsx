import React, { useState } from "react";
import "./index.less";
import { Button, Form, Input, Modal, Tabs } from "antd";
import type { TabsProps } from "antd";
import { ProCard } from "@ant-design/pro-components";

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
          <Form name="register" form={register} layout="vertical">
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
        width={850}
        height={400}
        open={isOpen}
        onOk={handleOk}
        onCancel={handleCancel}
        footer={null}
        className="xf-user__modal"
      >
        <span className="xf-user__modal-tabs">
          <Tabs defaultActiveKey="1" items={items} onChange={onTabsChange} />
        </span>
        {/* 除了进行登录注册的筛选外，还需要进行老师和学生的筛选 */}
        <span className="xf-user__modal-card">
          <ProCard
            bordered
            style={{ width: "100%", height: "100%" }}
            className="xf-user__modal-card-body"
          >
            <div>哎呀我去，原来我是老师</div>
          </ProCard>
        </span>
      </Modal>
    </div>
  );
};

export default UserModal;
