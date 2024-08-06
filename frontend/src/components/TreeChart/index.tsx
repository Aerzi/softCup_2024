import React, { useEffect, useRef, useCallback } from "react";
import * as echarts from "echarts";
import { Button } from "antd";

const TreeChart = ({ data }: any) => {
  const chartRef = useRef(null);
  let myChart: echarts.ECharts = null;

  const handleFullScreen = useCallback(() => {
    if (myChart) {
      myChart.dispatchAction({ type: "togglefullscreen" });
    }
  }, [myChart]);

  useEffect(() => {
    if (chartRef.current !== null) {
      myChart = echarts.init(chartRef.current);
    }

    // 定义option
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
          top: "2%",
          bottom: "8%",
          symbol: "emptyCircle",
          orient: "horizontal",
          expandAndCollapse: true,
          label: {
            position: "right",
            rotate: 0,
            verticalAlign: "middle",
            align: "right",
            fontSize: 9,
          },
          leaves: {
            label: {
              position: "left",
              rotate: 0,
              verticalAlign: "middle",
              align: "left",
            },
          },
          animationDurationUpdate: 750,
        },
      ],
      legend: {
        top: "top",
        orient: "horizontal",
        selectedMode: true,
        data: ["tree"],
      },
    };

    // 使用刚初始化的实例设置图表选项
    myChart.setOption(option);

    // 监听图例的点击事件
    myChart.on("legendselectchanged", (params) => {
      handleFullScreen();
    });

    // 组件卸载时的逻辑，例如销毁echarts实例
    return () => {
      if (myChart !== null) {
        myChart.dispose();
      }
    };
  }, [data, handleFullScreen]);

  useEffect(() => {
    if (myChart) {
      myChart.resize();
    }
  }, [chartRef]); // 当chartRef变化时，重新调整图表大小

  return (
    <div style={{ width: "1000px", height: "680px", position: "relative" }}>
      <div ref={chartRef} style={{ width: "100%", height: "100%" }}></div>
    </div>
  );
};

export default TreeChart;
