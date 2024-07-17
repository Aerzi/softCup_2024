import React, { useEffect, useRef, useState } from "react";
import * as echarts from "echarts";

const GradientStacked = () => {
  const chartRef = useRef(null);
  let myChart: echarts.ECharts = null;

  useEffect(() => {
    // 在组件挂载后初始化echarts实例
    if (chartRef.current !== null) {
      myChart = echarts.init(chartRef.current);
    }

    // 定义option，这里应该是你的完整option配置...
    const option = {
      color: ["#80FFA5", "#00DDFF", "#37A2FF", "#FF0087", "#FFBF00"],
      title: {
        text: "Gradient Stacked Area Chart",
      },
      tooltip: {
        trigger: "axis",
        axisPointer: {
          type: "cross",
          label: {
            backgroundColor: "#6a7985",
          },
        },
      },
      legend: {
        data: ["Line 1"],
      },
      toolbox: {
        feature: {
          saveAsImage: {},
        },
      },
      grid: {
        left: "3%",
        right: "4%",
        bottom: "3%",
        containLabel: true,
      },
      xAxis: [
        {
          type: "category",
          boundaryGap: false,
          data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
        },
      ],
      yAxis: [
        {
          type: "value",
        },
      ],
      series: [
        {
          name: "Line 1",
          type: "line",
          stack: "Total",
          smooth: true,
          lineStyle: {
            width: 0,
          },
          showSymbol: false,
          areaStyle: {
            opacity: 0.8,
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {
                offset: 0,
                color: "rgb(128, 255, 165)",
              },
              {
                offset: 1,
                color: "rgb(1, 191, 236)",
              },
            ]),
          },
          emphasis: {
            focus: "series",
          },
          data: [140, 232, 101, 264, 90, 340, 250],
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

export default GradientStacked;
