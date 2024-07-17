import React, { useEffect, useState } from 'react';
import './index.less';




const DescJob = ({ classNum }: {
    classNum: number
}) => {
    // 使用state来控制数字的显示
    const [num, setNum] = useState(0);

    useEffect(() => {
        // 当classNum变化时，更新state
        let currentNum = num;
        const interval = setInterval(() => {
            if (currentNum < classNum) {
                currentNum++;
                setNum(currentNum);
            } else {
                clearInterval(interval);
            }
        }, 50); // 每50毫秒增加1，可以根据需要调整时间间隔

        return () => {
            clearInterval(interval); // 清除定时器
        };
    }, [classNum]); // 依赖数组中的classNum变化

    return (
        <div className='xf-desc-job__page'>
            <div className='xf-desc-job__content'>
                <p className='xf-desc-job__content-text'>{num}</p>
            </div>
        </div>
    );

};

export default DescJob;