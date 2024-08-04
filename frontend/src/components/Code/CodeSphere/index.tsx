import React, { useState } from "react";
import MonacoEditor from "react-monaco-editor";
import "./index.less";
import { MonacoEditorProps } from "../../../types/commonType";
import { Space, Select, Button, message } from "antd";
import { submitAnswer } from "../../../services/judgeService";
import { encodeBase64 } from "../../../utils/StrToBase64";
import { languageIdMap, list, options } from "./data";
import { IAnswer } from "../type";
import FullScreenLoading from "../../Other/Loading";

const CodeSphere = ({
  classId,
  questionId,
  onAnswer,
  ...props
}: MonacoEditorProps & {
  classId: number;
  questionId: number;
  onAnswer: (res: IAnswer) => void;
}) => {
  const {
    width = "1000px",
    height = "680px",
    value,
    theme = "vs-dark",
    select = false,
    language = "python3",
    onChange,
  } = props;

  const [languageValue, setLanguageValue] = useState(language);
  const [sourceCode, setSourceCode] = useState(value || "");
  const [loading, setLoading] = useState(false);

  const findKeyByValue = (value: number): string | null => {
    for (const [key, val] of Object.entries(languageIdMap)) {
      if (val === value) {
        return key;
      }
    }
    return null; // 如果没有找到对应的 key，返回 null
  };

  const handleSubmit = (code: string, lang: number) => {
    const languageId = languageIdMap[lang] || 28;
    const stdin = ""; // 标准输入，根据需要进行设置
    setLoading(true);
    submitAnswer({
      language_id: languageId,
      codeType: findKeyByValue(languageId),
      source_code: code,
      stdin: encodeBase64(stdin),
      questionId: questionId,
      classId: classId,
    })
      .then((response: any) => {
        if (response.code === 200) {
          message.success("提交成功");
          onAnswer?.(response.response);
        } else {
          message.error("提交失败: " + response.message);
        }
      })
      .catch((error) => {
        message.error("提交失败: " + error.message);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  return (
    <>
      {loading && <FullScreenLoading spinning={loading} tip="正在判题..." />}
      <div className="xf-code-sphere__page">
        <Space style={{ marginBottom: "10px", height: "40px" }}>
          <Select
            disabled={!select}
            popupMatchSelectWidth={false}
            defaultValue={languageValue}
            onChange={(e) => {
              setLanguageValue(e);
            }}
            options={list}
            style={{ backgroundColor: "white", borderRadius: "5px" }}
          />
          <Button
            type="primary"
            style={{
              position: "absolute",
              right: "10px",
              top: "5px",
              height: "30px",
            }}
            onClick={() => {
              console.log(sourceCode);
              handleSubmit(encodeBase64(sourceCode), parseInt(languageValue));
            }}
          >
            提交代码
          </Button>
        </Space>
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
      </div>
    </>
  );
};

export default CodeSphere;
