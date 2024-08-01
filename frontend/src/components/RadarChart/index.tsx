import { Radar } from "@ant-design/plots";
import React from "react";

const data = [
  {
    name: "编程语言掌握度",
    star: 70,
  },
  {
    name: "算法与数据结构",
    star: 60,
  },
  {
    name: "操作系统知识",
    star: 50,
  },
  {
    name: "网络编程",
    star: 40,
  },
  {
    name: "数据库管理",
    star: 50,
  },
  {
    name: "软件开发流程",
    star: 70,
  },
  {
    name: "软件工程",
    star: 60,
  },
];

const RadarChart = () => {
  const config = {
    data: data.map((d) => ({ ...d, star: Math.sqrt(d.star) })),
    xField: "name",
    yField: "star",
    area: {
      style: {
        fillOpacity: 0.2,
      },
    },
    scale: {
      x: {
        padding: 0.5,
        align: 0,
      },
      y: {
        nice: true,
      },
    },
    axis: {
      x: {
        title: false,
        grid: true,
      },
      y: {
        gridAreaFill: "rgba(0, 0, 0, 0.04)",
        label: false,
        title: false,
      },
    },
  };
  return <Radar {...config} />;
};

export default RadarChart;
