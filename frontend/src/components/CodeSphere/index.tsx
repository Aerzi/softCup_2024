import React, { useState } from "react";
import MonacoEditor from "react-monaco-editor";
import "./index.less";
import { MonacoEditorProps } from "../../types/commonType";
import { ProCard } from "@ant-design/pro-components";
import { Space, Select, Button, message } from "antd";
import { onSubmit } from "../../services/judgeService";
import { encodeBase64 } from "../../utils/StrToBase64";
import { languageIdMap, list } from "./data";

const CodeSphere = (props: MonacoEditorProps) => {
  const {
    width = "100%",
    height = "600px",
    value,
    theme = "vs",
    select = false,
    language = "python",
    onChange,
  } = props;

  const [languageValue, setLanguageValue] = useState(language);
  const [sourceCode, setSourceCode] = useState(value || "");

  const options: any = {
    selectOnLineNumbers: true,
    roundedSelection: false,
    readOnly: false, // 是否只读  取值 true | false
    cursorStyle: "line",
    automaticLayout: false, // 自动布局
  };

  const extraSelect = (
    <Space>
      <Select
        disabled={!select}
        popupMatchSelectWidth={false}
        defaultValue={languageValue}
        onChange={(e) => {
          setLanguageValue(e);
        }}
        options={list}
      />
      <Button
        type="primary"
        onClick={() =>
          handleSubmit(encodeBase64(sourceCode), parseInt(languageValue))
        }
      >
        提交
      </Button>
    </Space>
  );

  const handleSubmit = (code: string, lang: number) => {
    const languageId = languageIdMap[lang] || 28; // 默认为 Python
    const stdin = ""; // 标准输入，根据需要进行设置

    onSubmit({
      language_id: languageId,
      source_code: code,
      stdin,
      questionId: 4,
      classId: 1,
    })
      .then((response: any) => {
        if (response.code === 200) {
          message.success("提交成功");
          // 假设 response 格式如下
        } else {
          message.error("提交失败: " + response.message);
        }
      })
      .catch((error) => {
        message.error("提交失败: " + error.message);
      });
  };

  return (
    <div className="xf-code-sphere__page">
      <ProCard
        title={extraSelect}
        style={{ width: "100%", height: "100%" }}
        className="card-theme-light"
      >
        <MonacoEditor
          {...props}
          width={width}
          height={height}
          value={sourceCode}
          theme={theme}
          language={languageValue}
          options={options}
          onChange={(newValue) => {
            setLanguageValue(languageValue);
            setSourceCode(newValue);
            onChange?.(newValue);
          }}
        />
      </ProCard>
    </div>
  );
};

export default CodeSphere;
