import React, { useState } from "react";
import "./index.less";
import { Button, Form, Input, message, Modal, Tabs } from "antd";
import type { TabsProps } from "antd";
import { ProCard } from "@ant-design/pro-components";
import student from "../../../assets/images/student.svg";
import teacher from "../../../assets/images/teacher.svg";
import { useDispatch } from "react-redux";
import { updateUser } from "../../../stores/slices/userSlice";
import {
  onLogin,
  onStudentRegister,
  onTeacherRegister,
} from "../../../services/userService";
import { setLocalData } from "../../../utils/Storage";
import { useNavigate } from "react-router";
import FullScreenLoading from "../../Other/Loading";

// 定义登录表单类型
type FieldType = {
  username?: string;
  password?: string;
  remember?: string;
};

const UserModal = ({
  isOpen,
  setIsOpen,
}: {
  isOpen: boolean;
  setIsOpen: (arg0: boolean) => void;
}) => {
  const navigate = useNavigate();
  // 全局挂载messageApi
  const [messageApi, contextHolder] = message.useMessage();

  // loading
  const [loading, setLoading] = useState(false);

  const [login] = Form.useForm();
  const [register] = Form.useForm();

  // tabs激活页管理
  const [activeTabsKey, setActiveTabsKey] = useState("1");

  const [isStudent, setIsStudent] = useState(true);

  // 使用useDispatch钩子获取dispatch函数
  const dispatch = useDispatch();

  // 用户登录
  const onUserLogin = (username: string, password: string) => {
    setLoading(true);
    messageApi.open({
      type: "loading",
      content: "Action in progress..",
      duration: 0,
    });

    onLogin(username, password)
      .then((res: any) => {
        if (res.code === 200) {
          // 说明成功，调用messageApi表示目前登录状态ok
          messageApi.open({ type: "success", content: res.message });
          // 保存用户信息
          dispatch(updateUser({ isLogin: true, data: res.response }));
          if (username === "admin123") {
            // 说明是管理员
            setLocalData("role", "admin");
          } else {
            setLocalData("role", isStudent ? "student" : "teacher");
          }

          setLocalData("user", res.response);
          setLocalData("isLogin", true);

          setIsOpen(false);

          setTimeout(() => {
            if (username === "admin123") {
              navigate("/admin");
            } else {
              navigate("/");
            }
            setLoading(false);
            messageApi.destroy();
          }, 100);
        } else {
          messageApi.open({ type: "error", content: res.message });
        }
      })
      .catch((err: any) => {
        console.error(err.message);
        messageApi.open({ type: "error", content: err.message });
      });
  };

  // 用户注册
  const onUserRegister = (username: string, password: string) => {
    // 调用注册接口，需要对角色做出判断
    if (isStudent) {
      onStudentRegister(username, password)
        .then((res: any) => {
          if (res.code === 200) {
            // 说明成功，调用messageApi表示目前注册状态ok
            messageApi.open({ type: "success", content: res.message });
            // 回到登录tabs
            setTimeout(() => {
              setActiveTabsKey("1");
            }, 100);
          } else {
            messageApi.open({ type: "error", content: res.message });
          }
        })
        .catch((err: any) => {
          messageApi.open({ type: "error", content: err.message });
        });
    } else {
      onTeacherRegister(username, password)
        .then((res: any) => {
          if (res.code === 200) {
            // 说明成功，调用messageApi表示目前登录状态ok
            messageApi.open({ type: "success", content: res.message });
            /// 回到登录tabs
            setTimeout(() => {
              setActiveTabsKey("1");
            }, 100);
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
      {loading && <FullScreenLoading spinning={loading} />}
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
