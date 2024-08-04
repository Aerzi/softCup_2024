import React, { useState } from "react";
import { Typography, Space, Button, message, Modal } from "antd";
import { IFile } from "../../File/type";
import { selectById } from "../../../services/fileService";
import { ProCard } from "@ant-design/pro-components";
const { Text, Paragraph } = Typography;

const FilePreviewer = ({ id }: { id: number }) => {
  const [file, setFile] = useState<IFile | null>(null);
  const [visible, setVisible] = useState(false);
  const [loading, setLoading] = useState<boolean>(true);

  // 根据id来获取文件信息
  const onSelectById = () => {
    setLoading(true);
    selectById(id)
      .then((res: any) => {
        if (res.code === 200) {
          setFile(res.response);
          console.log("文件类型", file?.type);
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

  const preview = () => {
    switch (file?.type) {
      case "text/plain":
        return (
          <Paragraph>
            <Text>`${file?.filePath}`</Text>
          </Paragraph>
        );
      case "PDF":
        return (
          <embed
            src={file?.filePath}
            type="application/pdf"
            width="1200px"
            height="600px"
            style={{ margin: "0 auto" }}
          />
        );
      case "image/jpeg":
      case "image/png":
      case "image/gif":
        return (
          <img src={file?.filePath} alt="image" style={{ maxWidth: "100%" }} />
        );
      case "video/mp4":
      case "video/avi":
        return (
          <video controls style={{ width: "100%" }}>
            <source src={file?.filePath} type={file?.type} />
            您的浏览器不支持视频标签。
          </video>
        );
      default:
        return (
          <div>
            <Text>文件类型不支持直接预览</Text>
            <Button href={file?.filePath} target="_blank" download>
              下载文件
            </Button>
          </div>
        );
    }
  };

  return (
    <>
      <a
        onClick={() => {
          setVisible(true);
          onSelectById();
        }}
      >
        预览
      </a>
      <Modal
        title="文件预览"
        width={1300}
        height={600}
        open={visible}
        loading={loading}
        onCancel={() => setVisible(false)}
        footer={null}
        className="xf-user__modal"
      >
        <ProCard style={{ width: "100%", height: "100%", margin: "0 auto" }}>
          <Space direction="vertical">{preview()}</Space>
        </ProCard>
      </Modal>
    </>
  );
};

export default FilePreviewer;
