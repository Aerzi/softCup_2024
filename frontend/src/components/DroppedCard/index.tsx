import React, { useState } from "react";
import "./index.less";

const DroppedCard = () => {
    const [droppedItem, setDroppedItem] = useState(null);

    const handleDragOver = (e: React.DragEvent<HTMLElement>) => {
        e.preventDefault(); // 允许放置
    };

    const handleDrop = (e: React.DragEvent<HTMLElement>) => {
        e.preventDefault();
        const item = e.dataTransfer.getData('text/plain');
        // 更新状态以展示详细信息
        setDroppedItem(item);
    };

    return (
        <div className="xf-dropped__card"
            onDragOver={handleDragOver} // 添加dragover事件
            onDrop={handleDrop} // 添加drop事件
        >
            {droppedItem ? `Dropped item: ${droppedItem}` : 'Drop item here'}
        </div>
    )
}

export default DroppedCard