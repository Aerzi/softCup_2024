import { Liquid } from '@ant-design/plots';
import React from 'react';


const LiquidChart = () => {
  const config = {
    percent: 0.3,
    style: {
      outlineBorder: 4,
      outlineDistance: 8,
      waveLength: 128,
    },
  };
  return <Liquid {...config} />;
};

export default LiquidChart;