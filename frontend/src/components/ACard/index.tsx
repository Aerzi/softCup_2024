import React from "react";
import { Card, Typography, Space } from "antd";
import {
  SmileOutlined,
  FrownOutlined,
  CheckOutlined,
  CloseOutlined,
} from "@ant-design/icons";

const { Text } = Typography;

const AssessmentCard = (assessmentData: any) => {
  return (
    <Card
      title="评估结果"
      style={
        assessmentData !== null &&
        assessmentData !== undefined &&
        assessmentData.feedback
          ? { position: "fixed", bottom: "10px", right: "10px", width: "800px" }
          : {
              display: "none",
            }
      }
      extra={
        <Space>
          <CheckOutlined title="代码功能正常" />
          <CloseOutlined title="存在改进空间" />
        </Space>
      }
    >
      <div style={{ display: "flex", justifyContent: "space-between" }}>
        <div>
          <h3>反馈</h3>
          <Text>{assessmentData.feedback}</Text>
        </div>
        <div>
          <h3>评分</h3>
          <div style={{ display: "flex", alignItems: "center" }}>
            <SmileOutlined style={{ color: "#87d068" }} />
            <Text style={{ marginLeft: 10 }}>
              {assessmentData.readabilityScore}/10 可读性评分
            </Text>
          </div>
          <div style={{ display: "flex", alignItems: "center", marginTop: 10 }}>
            <FrownOutlined style={{ color: "#ffbf00" }} />
            <Text style={{ marginLeft: 10 }}>
              {assessmentData.efficiencyScore}/10 效率评分
            </Text>
          </div>
        </div>
      </div>
      <div style={{ marginTop: 20 }}>
        <h3>错误分析</h3>
        <Text>{assessmentData.errorAnalysis}</Text>
      </div>
      <div style={{ marginTop: 20 }}>
        <h3>优化建议</h3>
        <Text>{assessmentData.optimizationSuggestions}</Text>
      </div>
      {assessmentData.modifiedCode && (
        <div style={{ marginTop: 20 }}>
          <h3>优化后的代码</h3>
          <pre style={{ background: "#f5f5f5", padding: 10, borderRadius: 5 }}>
            <code>{assessmentData.modifiedCode}</code>
          </pre>
        </div>
      )}
    </Card>
  );
};

export default AssessmentCard;
