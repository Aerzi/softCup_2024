import React, { useEffect, useState } from "react";
import {
  Layout,
  Form,
  Input,
  Button,
  Typography,
  message,
  Select,
  Switch,
  UploadProps,
  Row,
  Col,
  Space,
} from "antd";
import {
  AntDesignOutlined,
  FilePptOutlined,
  InboxOutlined,
} from "@ant-design/icons";
import { ProCard } from "@ant-design/pro-components";
import {
  getProcess,
  getThemeList,
  pptByDocs,
} from "../../../services/docsService";
import FullScreenLoading from "../../Other/Loading";
import { IDocs, IOutLine } from "../type";
import Dragger from "antd/es/upload/Dragger";
import { getLocalData } from "../../../utils/Storage";
import { json } from "react-router";
import OutlineComponent from "../OutLine";
const { Sider, Content } = Layout;

interface ITheme {
  key: string;
  name: string;
  thumbnail: string;
}

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
      a.download = "presentation.pptx"; // 指定下载文件的名称
      document.body.appendChild(a);
      a.click();
      window.URL.revokeObjectURL(url);
    })
    .catch((e) => console.error(e));
}

const PPTCreater = () => {
  const [loading, setLoading] = useState(false);

  const [form] = Form.useForm<IDocs>();
  const [formData, setFormData] = useState<FormData>(null);
  const [themeList, setThemeList] = useState([]);
  const [outline, setOutline] = useState<IOutLine>(null);
  const [sid, setSid] = useState("");

  const onGetThemeList = () => {
    setLoading(true);
    getThemeList()
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
    getProcess(sid)
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
        setLoading(true);
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
    // 保存form中的数据
    const formData = new FormData();
    formData?.append("data", JSON.stringify(values));
    setFormData(formData);
    message.success("配置保存成功");
  };

  // 上传参数
  const props: UploadProps = {
    name: "file",
    multiple: true,
    accept: ".txt,.pdf,.doc,.docx,",
    action: "http://localhost:8080/api/student/spark/ppt/doc/ppt",
    headers: {
      "Content-Type": "multipart/form-data",
      Authorization:
        getLocalData("token") !== null ? getLocalData("token") : "",
    },
    onChange(info) {
      const { status } = info.file;
      if (status !== "uploading") {
        console.log(info.file, info.fileList);
      }
      if (status === "done") {
        const { file } = info;
        formData?.append("file", file.originFileObj);
        setLoading(true);
        pptByDocs(formData)
          .then((res: any) => {
            if (res.code === 200) {
              setSid(res.response?.data?.sid);
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
      } else if (status === "error") {
        message.error(`${info.file.name} file upload failed.`);
      }
    },
    onDrop(e) {
      console.log("Dropped files", e.dataTransfer.files);
    },
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
                  保存配置
                </Button>
              </Form.Item>
            </Form>
          </ProCard>
        </Content>
        <Sider
          width={outline ? 800 : 400}
          className="site-layout-background"
          style={{
            backgroundImage:
              "linear-gradient(to right, #a8edea 0%, #fed6e3 100%)",
          }}
        >
          {!outline && (
            <ProCard title={<h3>上传文档</h3>} style={{ height: "100%" }}>
              <Dragger {...props}>
                <p className="ant-upload-drag-icon">
                  <InboxOutlined />
                </p>
                <p className="ant-upload-text">点击或者拖拽文件到此区域</p>
              </Dragger>
            </ProCard>
          )}
          {outline && (
            <ProCard
              title={<h3>智能大纲</h3>}
              style={{ height: "100%", overflow: "auto" }}
              extra={
                <Button
                  type="primary"
                  onClick={() => {
                    onGetProcess(sid);
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

export default PPTCreater;
