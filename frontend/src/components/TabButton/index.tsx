import React, { useContext } from "react";
import "./index.less";
import { ActiveTabContext } from "../TabsInstruction";

const TabButton = ({ text, index }: {
    text: string,
    index: number
}) => {
    const { activeKey, setActiveKey } = useContext(ActiveTabContext);
  
    const handleClick = () => {
      setActiveKey(index);
    };
  
    return (
      <div
        className={"xf-tabs-instruction__button-item" }
        onClick={handleClick}
      >
        <span className={`xf-tabs-instruction__button-item-text ${activeKey === index ? 'xf-tabs-instruction__button-item--active' : ''}`}>
          {text}
        </span>
      </div>
    );
  };

export default TabButton;