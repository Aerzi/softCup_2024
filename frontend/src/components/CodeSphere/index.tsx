import React, { useState } from "react";
import MonacoEditor from "react-monaco-editor";
import "./index.less";
import { MonacoEditorProps } from "../../types/commonType";
import { ProCard } from "@ant-design/pro-components";
import { Space, Select, Button, message } from "antd";
import { onSubmit } from "../../services/judgeService";
import AssessmentCard from "../ACard";
import { Position } from "monaco-editor";

const CodeSphere = (props: MonacoEditorProps) => {
  function encodeBase64(str: string) {
    const encoder = new TextEncoder();
    const data = encoder.encode(str);
    let result = "";
    for (let i = 0; i < data.length; i++) {
      result += String.fromCharCode(data[i]);
    }
    return btoa(result);
  }
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
  const [assessmentData, setAssessmentData] = useState(null);

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
        bordered={false}
        popupMatchSelectWidth={false}
        defaultValue={languageValue}
        onChange={(e) => {
          setLanguageValue(e);
        }}
        options={[
          {
            label: "Bosque (latest)",
            value: "bosque",
          },
          {
            label: "C3 (latest)",
            value: "c3",
          },
          {
            label: "C (Clang 10.0.1)",
            value: "c",
          },
          {
            label: "C++ (Clang 10.0.1)",
            value: "cpp",
          },
          {
            label: "C (Clang 9.0.1)",
            value: "clang9",
          },
          {
            label: "C++ (Clang 9.0.1)",
            value: "clang9cpp",
          },
          {
            label: "C# (Mono 6.12.0.122)",
            value: "csharp",
          },
          {
            label: "C# (.NET Core SDK 3.1.406)",
            value: "csharpcore",
          },
          {
            label: "C# (.NET Core SDK 7.0.400)",
            value: "csharpcore7",
          },
          {
            label: "C++ Test (Clang 10.0.1, Google Test 1.8.1)",
            value: "cpptest",
          },
          {
            label: "C++ Test (GCC 8.4.0, Google Test 1.8.1)",
            value: "gcccpptest",
          },
          {
            label: "C# Test (.NET Core SDK 3.1.406, NUnit 3.12.0)",
            value: "csharptest",
          },
          {
            label: "F# (.NET Core SDK 3.1.406)",
            value: "fsharp",
          },
          {
            label: "Java (OpenJDK 14.0.1)",
            value: "java",
          },
          {
            label:
              "Java Test (OpenJDK 14.0.1, JUnit Platform Console Standalone 1.6.2)",
            value: "javatest",
          },
          {
            label: "MPI (OpenRTE 3.1.3) with C (GCC 8.4.0)",
            value: "mpi-c",
          },
          {
            label: "MPI (OpenRTE 3.1.3) with C++ (GCC 8.4.0)",
            value: "mpi-cpp",
          },
          {
            label: "MPI (OpenRTE 3.1.3) with Python (3.7.7)",
            value: "mpi-python",
          },
          {
            label: "Multi-file program",
            value: "multifile",
          },
          {
            label: "Nim (stable)",
            value: "nim",
          },
          {
            label: "Python 2.7 (PyPy 7.3.12)",
            value: "python2",
          },
          {
            label: "Python 3.10 (PyPy 7.3.12)",
            value: "python3",
          },
          {
            label: "Python 3.9 (PyPy 7.3.12)",
            value: "python39",
          },
          {
            label: "Python for ML (3.11.2)",
            value: "python-ml",
          },
          {
            label: "Python for ML (3.7.7)",
            value: "python-ml37",
          },
          {
            label: "Visual Basic.Net (vbnc 0.0.0.5943)",
            value: "vbnet",
          },
        ]}
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
    const languageIdMap = {
      bosque: 11,
      c3: 3,
      c: 1,
      cpp: 2,
      clang9: 13,
      clang9cpp: 14,
      csharp: 22,
      csharpcore: 21,
      csharpcore7: 29,
      cpptest: 15,
      gcccpptest: 12,
      csharptest: 23,
      fsharp: 24,
      java: 4,
      javatest: 5,
      mpiC: 6,
      mpiCpp: 7,
      mpiPython: 8,
      multifile: 89,
      nim: 9,
      python2: 26,
      python3: 28,
      python39: 27,
      pythonMl: 25,
      pythonMl37: 10,
      vbnet: 20,
    } as any;

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
          const assessmentData = {
            feedback: response.response.feedback,
            readabilityScore: "8",
            efficiencyScore: "7",
            modifiedCode: response.response.modified_code,
            errorAnalysis: response.response.error_analysis,
            optimizationSuggestions: response.response.optimization_suggestions,
          };
          setAssessmentData(assessmentData);
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
      <AssessmentCard assessmentData={assessmentData} />
    </div>
  );
};

export default CodeSphere;
