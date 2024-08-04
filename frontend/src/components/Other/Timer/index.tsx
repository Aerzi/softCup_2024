import React, { useState, useEffect } from "react";
import { format } from "date-fns"; // 导入 date-fns 库用于格式化日期
import { Typography } from "antd";
const { Title } = Typography;

const Timer = () => {
  const [startTime, setStartTime] = useState<number | null>(null);
  const [elapsedTime, setElapsedTime] = useState<string>("00:00:00");

  useEffect(() => {
    // 当组件挂载时开始计时
    const handleStart = () => {
      setStartTime(Date.now());
    };
    handleStart();
  }, []);

  useEffect(() => {
    if (startTime) {
      // 如果已经开始计时，设置一个定时器来更新时间
      const interval = setInterval(() => {
        const elapsed = Date.now() - startTime;
        const formattedTime = format(new Date(elapsed), "mm:ss");
        setElapsedTime(formattedTime);
      }, 1000);

      // 清除定时器
      return () => clearInterval(interval);
    }
  }, [startTime]);

  return (
    <div style={{ textAlign: "center", color: "white" }}>
      <Title
        level={5}
        style={{ color: "#999", height: "20px", fontSize: "0.8rem" }}
      >
        {elapsedTime}
      </Title>
    </div>
  );
};

export default Timer;
