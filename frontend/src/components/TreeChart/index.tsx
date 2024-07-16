import React, { useEffect, useRef, useState } from "react";
import * as echarts from "echarts";
import data from "./data.json";

const TreeChart = () => {
  const chartRef = useRef(null);
  let myChart: echarts.ECharts = null;

  useEffect(() => {
    // 在组件挂载后初始化echarts实例
    if (chartRef.current !== null) {
      myChart = echarts.init(chartRef.current);
    }

    // 定义option，这里应该是你的完整option配置...
    const option = {
      tooltip: {
        trigger: "item",
        triggerOn: "mousemove",
      },
      series: [
        {
          type: "tree",
          data: [data],
          left: "2%",
          right: "2%",
          top: "8%",
          bottom: "20%",
          symbol: "emptyCircle",
          orient: "vertical",
          expandAndCollapse: true,
          label: {
            position: "top",
            rotate: -90,
            verticalAlign: "middle",
            align: "right",
            fontSize: 9,
          },
          leaves: {
            label: {
              position: "bottom",
              rotate: -90,
              verticalAlign: "middle",
              align: "left",
            },
          },
          animationDurationUpdate: 750,
        },
      ],
    };

    // 使用刚初始化的实例设置图表选项
    if (myChart !== null) {
      myChart.setOption(option);
    }

    // 组件卸载时的逻辑，例如销毁echarts实例
    return () => {
      if (myChart !== null) {
        myChart.dispose();
      }
    };
  }, []); // 空依赖数组确保这个effect只在组件挂载时运行一次

  return <div ref={chartRef} style={{ width: "100%", height: "100%" }}></div>;
};

export default TreeChart;
