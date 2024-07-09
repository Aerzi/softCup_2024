import React, { createContext, useState } from "react";
import "./index.less";
import { Button } from "antd";
import TabsContentBox from "../TabsContentBox";
import TabButton from "../TabButton";

 // 父组件创建一个 Context 对象来管理button的状态
export const ActiveTabContext = createContext({
  activeKey: 0,
  setActiveKey: (activeKey: number) => {},
});

const TabsInstruction = () => {
  const [activeKey, setActiveKey] = useState(0);

  return (
    <>
      <div className="xf-tabs-instruction">
        <div className="xf-tabs-instruction__button-list">
          <ActiveTabContext.Provider value={{ activeKey, setActiveKey }}>
            {/* 子组件在这里进行管理 */}
            <TabButton text={"学习规划"} index={0} />
            <TabButton text={"教学课堂"} index={1} />
            <TabButton text={"模拟竞赛"} index={2} />
          </ActiveTabContext.Provider>
        </div>
      </div>
      <div className="xf-tabs-instruction__content">
        <TabsContentBox activeKey={activeKey} />
      </div>
    </>
  );
};

export default TabsInstruction;
