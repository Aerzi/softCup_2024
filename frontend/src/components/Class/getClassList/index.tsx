import React, { Dispatch, SetStateAction, useEffect, useState } from "react";
import { message, Select } from "antd";
import moment from "moment"; // 用于时间格式化
import { IClassRoom } from "../type";
import { getClassList } from "../../../services/classService";

const { Option } = Select;

const ClassSelect = ({
  id,
  onChange,
}: {
  id: number;
  onChange: (id: number) => void;
}) => {
  const [classList, setClassList] = useState<IClassRoom[]>([]);
  const onGetClassList = () => {
    getClassList()
      .then((res: any) => {
        if (res.code === 200) {
          setClassList(res.response);
        } else {
          message.error(res.message);
        }
      })
      .catch((err) => {
        message.error(err.message);
      });
  };
  useEffect(() => {
    onGetClassList();
  }, []);

  // 将课堂数据转换为Select组件的options格式
  const courseOptions = classList.map((classItem) => (
    <Option key={classItem.id} value={classItem.id}>
      {classItem.name} - {classItem.description}
    </Option>
  ));

  const handleChange = (value: number) => {
    // 处理选中课程的逻辑
    onChange(value);
  };

  return (
    <Select
      style={{ width: "200px" }}
      placeholder="选择一个课堂"
      value={id === 0 ? "请选择一个课堂" : id}
      onChange={handleChange}
    >
      {courseOptions}
    </Select>
  );
};

export default ClassSelect;
