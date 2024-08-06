import React, { useEffect, useState } from "react";
import {
  Layout,
  Form,
  Input,
  Button,
  message,
  Select,
  Switch,
  Row,
  Col,
  Space,
} from "antd";
import { AntDesignOutlined, FilePptOutlined } from "@ant-design/icons";
import { ProCard } from "@ant-design/pro-components";
import {
  generateOutline,
  generatePPTByTeacher,
  getTeacherProcess,
  getTeacherThemeList,
} from "../../../../services/docsService";
import FullScreenLoading from "../../../Other/Loading";
import { IDocs, IOutLine } from "../../type";

import OutlineComponent from "../../OutLine";
const { Sider, Content } = Layout;

interface ITheme {
  key: string;
  name: string;
  thumbnail: string;
}

// 下载函数
function downloadPPTX(fileUrl: string | URL | Request) {
  fetch(fileUrl)
    .then((response) => {
      if (response.ok) {
        return response.blob();
      }
      throw new Error("Network response was not ok.");
    })
    .then((blob) => {
      const url = URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = "presentation.pptx";
      document.body.appendChild(a);
      a.click();
      window.URL.revokeObjectURL(url);
    })
    .catch((e) => console.error(e));
}

const TeacherPPTCreater = () => {
  const [loading, setLoading] = useState(false);

  const [form] = Form.useForm<IDocs>();
  const [themeList, setThemeList] = useState([]);
  const [outline, setOutline] = useState<IOutLine>(null);
  const [outlineSid, setOutlineSid] = useState("");

  // 获取主题列表
  const onGetThemeList = () => {
    setLoading(true);
    getTeacherThemeList()
      .then((res: any) => {
        if (res.code === 200) {
          message.success(res.message);
          setThemeList(res.response?.data);
        } else {
          message.error(res.message);
        }
      })
      .catch((err: any) => {
        message.error(err.message);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const onGetProcess = (sid: string) => {
    setLoading(true);
    getTeacherProcess(sid)
      .then((res: any) => {
        if (res.code === 200) {
          message.success(res.message);
          console.log(res.response);
        } else {
          message.error(res.message);
        }
      })
      .catch((err: any) => {
        message.error(err.message);
      });
  };

  const onGeneratePPT = (sid: string) => {
    setLoading(true);
    generatePPTByTeacher(sid).then((res: any) => {
      if (res.code === 200) {
        message.success(res.message);
        onGetProcess(res.response?.data?.sid);
      } else {
        message.error(res.message);
      }
    });
  };

  useEffect(() => {
    onGetThemeList();

    // 建立websocket连接
    console.log("webSocket");
    var socket = new WebSocket("ws://localhost:8888/api/ppt/progress");

    // 连接成功时的回调函数
    socket.onopen = function (event) {
      console.log("WebSocket 连接已建立");
      // 可以在这里发送消息给服务器
      socket.send(JSON.stringify({ message: "Hello, server!" }));
    };

    // 接收到服务器消息时的回调函数
    socket.onmessage = function (event) {
      const jsonData = JSON.parse(event?.data) ? JSON.parse(event?.data) : null;
      console.log("收到消息：", jsonData);
      if (jsonData?.data?.process !== 100) {
        // setLoading(true);
      } else {
        setLoading(false);
      }
      if (jsonData?.data?.pptUrl) {
        downloadPPTX(jsonData?.data?.pptUrl);
      }
    };

    // 连接关闭时的回调函数
    socket.onclose = function (event) {
      console.log("WebSocket 连接已关闭");
    };

    // 连接发生错误时的回调函数
    socket.onerror = function (error) {
      console.error("WebSocket 发生错误：", error);
    };

    return () => {
      socket.close();
    };
  }, []);

  const onFinish = (values: IDocs) => {
    setLoading(true);
    generateOutline(values)
      .then((res: any) => {
        if (res.code === 200) {
          message.success(res.message);
          setOutlineSid(res.response?.data?.sid);
          setOutline(res.response?.data?.outline);
        } else {
          message.error(res.message);
        }
      })
      .catch((err) => {
        message.error(err.message);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  return (
    <>
      {loading && <FullScreenLoading spinning={loading} tip={"正在生成中"} />}
      <Layout
        style={{
          position: "relative",
          width: "100%",
          height: "100%",
          minHeight: "630px",
          maxHeight: "120vh",
        }}
      >
        <Content>
          <ProCard
            title={<h2>PPT生成</h2>}
            style={{
              backgroundImage:
                "linear-gradient(to right, #a8edea 0%, #fed6e3 100%)",
              height: "100%",
              overflow: "auto",
            }}
          >
            <Form form={form} onFinish={onFinish}>
              <Form.Item
                label="PPT要求"
                labelAlign="left"
                name="query"
                rules={[{ required: true, message: "请输入用户生成PPT要求!" }]}
              >
                <Input.TextArea />
              </Form.Item>
              <Form.Item
                label="作者名"
                name="author"
                rules={[{ required: true, message: "请输入作者名!" }]}
              >
                <Input />
              </Form.Item>
              <Form.Item
                label="生成模型"
                name="create_model"
                rules={[{ required: true, message: "请选择模型!" }]}
              >
                <Select
                  options={[
                    { label: "自动", value: "auto" },
                    { label: "话题生成", value: "topic" },
                    { label: "文本生成", value: "text" },
                  ]}
                />
              </Form.Item>
              <Row gutter={16}>
                <Col span={12}>
                  <Form.Item
                    label="PPT演讲备注"
                    name="is_card_note"
                    labelCol={{ span: 8 }}
                    wrapperCol={{ span: 16 }}
                    valuePropName="checked"
                  >
                    <Switch checkedChildren="开启" unCheckedChildren="关闭" />
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item
                    label="添加封面"
                    name="is_cover_img"
                    labelCol={{ span: 8 }}
                    wrapperCol={{ span: 16 }}
                    valuePropName="checked"
                  >
                    <Switch checkedChildren="添加" unCheckedChildren="取消" />
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item
                    label="自动配图"
                    name="is_figure"
                    labelCol={{ span: 8 }}
                    wrapperCol={{ span: 16 }}
                    valuePropName="checked"
                  >
                    <Switch checkedChildren="开启" unCheckedChildren="关闭" />
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item label="语种" name="language">
                    <Select defaultValue="cn">
                      <Select.Option value="cn">中文</Select.Option>
                      <Select.Option value="en">英文</Select.Option>
                      <Select.Option value="ru">俄语</Select.Option>
                      <Select.Option value="ja">日语</Select.Option>
                      <Select.Option value="ko">韩语</Select.Option>
                      <Select.Option value="fr">法语</Select.Option>
                      <Select.Option value="de">德语</Select.Option>
                    </Select>
                  </Form.Item>
                </Col>
              </Row>
              <Form.Item
                name={"theme"}
                style={{ width: "100%", height: "100%", overflow: "auto" }}
              >
                {themeList?.map((theme: ITheme) => {
                  return (
                    <Space
                      style={
                        theme?.key === form.getFieldValue("theme")
                          ? {
                              backgroundColor: "#fff",
                              width: "160px",
                              padding: "0 5px",
                              margin: "10px",
                              cursor: "pointer",
                            }
                          : {
                              backgroundColor: "#fff",
                              width: "160px",
                              padding: "0 5px",
                              margin: "10px",
                              cursor: "pointer",
                            }
                      }
                      title={theme?.name}
                      key={theme?.key}
                    >
                      <img
                        src={theme?.thumbnail}
                        alt=""
                        style={{ width: "150px" }}
                        onClick={() => {
                          form.setFieldValue("theme", theme?.key);
                          message.success("设置主题成功" + theme?.key);
                        }}
                      />
                    </Space>
                  );
                })}
              </Form.Item>
              <Form.Item
                style={{
                  width: "100%",
                  display: "flex",
                  justifyContent: "center",
                }}
              >
                <Button
                  type="primary"
                  htmlType="submit"
                  size="large"
                  block
                  style={{
                    width: "400px",
                    backgroundImage:
                      "linear-gradient(135deg, #6253e1, #04befe)",
                  }}
                  icon={<AntDesignOutlined />}
                >
                  生成备课大纲
                </Button>
              </Form.Item>
            </Form>
          </ProCard>
        </Content>
        <Sider
          width={outline ? 800 : 0}
          className="site-layout-background"
          style={{
            backgroundImage:
              "linear-gradient(to right, #a8edea 0%, #fed6e3 100%)",
          }}
        >
          {outline && (
            <ProCard
              title={<h3>智能大纲</h3>}
              style={{ height: "100%", overflow: "auto" }}
              extra={
                <Button
                  type="primary"
                  onClick={() => {
                    onGeneratePPT(outlineSid);
                  }}
                  style={{
                    background: "linear-gradient(135deg, #00B8D9, #00BFA5);",
                  }}
                  icon={<FilePptOutlined />}
                >
                  生成PPT
                </Button>
              }
            >
              <OutlineComponent outline={outline} />
            </ProCard>
          )}
        </Sider>
      </Layout>
    </>
  );
};

export default TeacherPPTCreater;
