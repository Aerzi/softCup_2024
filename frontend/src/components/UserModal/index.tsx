import React, { useState } from "react";
import "./index.less";
import { Button, Form, Input, Modal, Tabs } from "antd";
import type { TabsProps } from "antd";
import { ProCard } from "@ant-design/pro-components";
import student from "./student.svg";
import teacher from "./teacher.svg";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../stores/redux/store";
import { updateUser } from "../../stores/slices/userSlice";
import { use } from "echarts";

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

  // 引入user, 有student和teacher两种状态
  const user = useSelector((state: RootState) => state.user);

  // 使用useDispatch钩子获取dispatch函数
  const dispatch = useDispatch();
  // 定义一个切换角色的函数
  const toggleRole = () => {
    // 根据当前角色切换到另一个角色
    dispatch(
      updateUser({ role: user.role === "student" ? "teacher" : "student" })
    );
  };

  // 定义一个切换是否登录的函数
  const toggleLogin = () => {
    dispatch(updateUser({ isLogin: !user.isLogin }));
  };

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
              <Button
                type="primary"
                htmlType="submit"
                onClick={() => toggleLogin()}
              >
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
        open={isOpen}
        onOk={handleOk}
        onCancel={handleCancel}
        footer={null}
        className="xf-user__modal"
      >
        <div className="xf-user__modal-content">
          <span className="xf-user__modal-tabs">
            <Tabs defaultActiveKey="1" items={items} onChange={onTabsChange} />
          </span>
          {/* 除了进行登录注册的筛选外，还需要进行老师和学生的筛选 */}
          <span className="xf-user__modal-card">
            <ProCard
              bordered
              style={{ width: "100%", height: "100%" }}
              className="xf-user__modal-card-body"
              onClick={() => toggleRole()}
            >
              {/* 判断之后，展示导入的图片 */}
              {user.role === "student" ? (
                <img src={student} className="xf-user__modal-card-img" />
              ) : (
                <img src={teacher} className="xf-user__modal-card-img" />
              )}
            </ProCard>
          </span>
        </div>
      </Modal>
    </div>
  );
};

export default UserModal;
