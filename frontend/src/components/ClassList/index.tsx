import React from "react";
import "./index.less";

// 设计一个数组，从0到20
const arr = Array.from({ length: 20 }, (_, i) => i + 1);

const ClassList = () => {
    const handleDragStart = (e: React.DragEvent<HTMLElement>, item: string) => {
        // 设置拖拽数据
        e.dataTransfer.setData('text/plain', item); 
    };

    return (
        <div className="xf-class__list">
            <div className="xf-class__list-header">每日一荐</div>
            <div className="xf-class__list-content">
                <ul className="xf-class__list-content-list">
                    {arr.map((item, index) => (
                        <li id={`${index}`} draggable onDragStart={(e) => handleDragStart(e, `item-${item}`)} className="xf-class__list-content-list-item">{`item-${item}`}</li>
                    ))}
                </ul>
            </div>
        </div>
    )
}

export default ClassList