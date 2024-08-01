import React, { useState } from "react";
import "./index.less";
import { Button, Form, Input, message, Modal, Tabs } from "antd";
import type { TabsProps } from "antd";
import { ProCard } from "@ant-design/pro-components";
import student from "./student.svg";
import teacher from "./teacher.svg";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../stores/redux/store";
import { updateUser } from "../../stores/slices/userSlice";
import {
  onLogin,
  onLogout,
  onStudentRegister,
  onTeacherRegister,
} from "../../services/userService";
import { setLocalData } from "../../utils/Storage";
import { useNavigate } from "react-router";

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
  const navigate = useNavigate();
  // 全局挂载messageApi
  const [messageApi, contextHolder] = message.useMessage();

  const [login] = Form.useForm();
  const [register] = Form.useForm();

  // tabs激活页管理
  const [activeTabsKey, setActiveTabsKey] = useState("1");

  const [isStudent, setIsStudent] = useState(true);

  // 引入user, 有student和teacher两种状态
  const user = useSelector((state: RootState) => state.user);

  // 使用useDispatch钩子获取dispatch函数
  const dispatch = useDispatch();

  const onUserLogin = (username: string, password: string) => {
    // 调用统一登录接口，不需要对角色做出判断
    onLogin(username, password)
      .then((res: any) => {
        console.log("res为", res);
        if (res.code === 200) {
          console.log("用户登录成功");
          // 说明成功，调用messageApi表示目前登录状态ok
          messageApi.open({ type: "success", content: res.message });
          // 保存用户信息
          dispatch(updateUser({ isLogin: true, data: res.response }));

          // 存储本地，持久化
          setLocalData("user", res.response);
          setLocalData("role", isStudent ? "student" : "teacher");
          setLocalData("isLogin", true);
          // 需要跳转到用户信息页
          navigate("/userprofile");
          // 同时关闭登录框
          setIsOpen(false);
        } else {
          messageApi.open({ type: "error", content: res.message });
        }
      })
      .catch((err: any) => {
        console.error(err.message);
        messageApi.open({ type: "error", content: err.message });
      });
  };

  const onUserRegister = (username: string, password: string) => {
    // 调用注册接口，需要对角色做出判断
    if (isStudent) {
      onStudentRegister(username, password)
        .then((res: any) => {
          console.log("res.code为", res.code);
          if (res.code === 200) {
            console.log("成功了");
            // 说明成功，调用messageApi表示目前注册状态ok
            messageApi.open({ type: "success", content: res.message });
            // 回到登录tabs
            setActiveTabsKey("1");
          } else {
            messageApi.open({ type: "error", content: res.message });
          }
        })
        .catch((err: any) => {
          console.log("被拦截了");
          messageApi.open({ type: "error", content: err.message });
        });
    } else {
      onTeacherRegister(username, password)
        .then((res: any) => {
          if (res.code === 200) {
            // 说明成功，调用messageApi表示目前登录状态ok
            messageApi.open({ type: "success", content: res.message });
            // 回到登录tabs
            setActiveTabsKey("1");
          } else {
            messageApi.open({ type: "error", content: res.message });
          }
        })
        .catch((err: any) => {
          messageApi.open({ type: "error", content: err.message });
        });
    }
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
              rules={[{ required: true, message: "用户名不能为空!" }]}
            >
              <Input />
            </Form.Item>

            <Form.Item<FieldType>
              label="密码"
              name="password"
              rules={[
                { required: true, message: "密码不能为空!" },
                { min: 4, message: "密码长度不能少于4位!" },
                {
                  validator: (rule, value) => {
                    if (value && !/[0-9]/.test(value)) {
                      return Promise.reject(new Error("密码必须包含数字!"));
                    }
                    return Promise.resolve();
                  },
                },
              ]}
            >
              <Input.Password />
            </Form.Item>

            <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
              <Button
                type="primary"
                htmlType="submit"
                onClick={() =>
                  onUserLogin(
                    login.getFieldValue("username"),
                    login.getFieldValue("password")
                  )
                }
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
              rules={[{ required: true, message: "用户名不能为空!" }]}
            >
              <Input />
            </Form.Item>

            <Form.Item<FieldType>
              label="密码"
              name="password"
              rules={[
                { required: true, message: "密码不能为空!" },
                { min: 6, message: "密码长度不能少于6位!" },
                {
                  validator: (rule, value) => {
                    if (value && !/[0-9]/.test(value)) {
                      return Promise.reject(new Error("密码必须包含数字!"));
                    }
                    if (value && !/[a-zA-Z]/.test(value)) {
                      return Promise.reject(new Error("密码必须包含字母!"));
                    }
                    return Promise.resolve();
                  },
                },
              ]}
            >
              <Input.Password />
            </Form.Item>

            <Form.Item<FieldType>
              label="确认密码"
              name="remember"
              rules={[
                { required: true, message: "请输入确认密码!" },
                {
                  validator: (_) => {
                    const password = register.getFieldValue("password");
                    const confirm = register.getFieldValue("remember");
                    if (password && confirm && password !== confirm) {
                      return Promise.reject(new Error("确认密码与密码不一致!"));
                    }
                    return Promise.resolve();
                  },
                },
              ]}
            >
              <Input.Password />
            </Form.Item>

            <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
              <Button
                type="primary"
                htmlType="submit"
                onClick={() => {
                  if (
                    register.getFieldValue("password") !==
                      register.getFieldValue("remember") ||
                    register.getFieldValue("password") === null
                  ) {
                    messageApi.open({
                      type: "error",
                      content: "确认密码与密码不一致!",
                    });
                    return;
                  }
                  onUserRegister(
                    register.getFieldValue("username"),
                    register.getFieldValue("password")
                  );
                }}
              >
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
    setActiveTabsKey(key);
  };

  return (
    <>
      {contextHolder}
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
              <Tabs
                defaultActiveKey="1"
                items={items}
                onChange={onTabsChange}
                activeKey={activeTabsKey}
                animated
              />
            </span>
            {/* 除了进行登录注册的筛选外，还需要进行老师和学生的筛选 */}
            <span className="xf-user__modal-card">
              <ProCard
                bordered
                style={{ width: "100%", height: "100%" }}
                className="xf-user__modal-card-body"
                onClick={() => setIsStudent(!isStudent)}
              >
                {/* 判断之后，展示导入的图片 */}
                {isStudent ? (
                  <div className="xf-user__modal-card-body-content">
                    <img src={student} className="xf-user__modal-card-img" />
                    <span className="xf-user__modal-card-text">我是学生~</span>
                  </div>
                ) : (
                  <div className="xf-user__modal-card-body-content">
                    <img src={teacher} className="xf-user__modal-card-img" />
                    <span className="xf-user__modal-card-text">我是老师🌹</span>
                  </div>
                )}
              </ProCard>
            </span>
          </div>
        </Modal>
      </div>
    </>
  );
};

export default UserModal;
