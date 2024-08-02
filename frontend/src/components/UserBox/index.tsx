import React, { useEffect, useState } from "react";
import "./index.less";
import {
  Descriptions,
  Badge,
  Button,
  Progress,
  Space,
  Input,
  Radio,
  DatePicker,
  message,
} from "antd";
import { RootState } from "../../stores/redux/store";
import { useDispatch, useSelector } from "react-redux";
import { updateUser } from "../../stores/slices/userSlice";
import {
  onEditStudentInfo,
  onEditTeacherInfo,
  onGetStudentInfo,
  onGetTeacherInfo,
} from "../../services/userService";
import moment from "moment";

const UserBox = () => {
  const [userProcess, setUserProcess] = useState(0);
  // 10个key，每有一个key不是空就加10

  let flag = false;
  useEffect(() => {
    // 这里可以添加获取用户信息的逻辑

    const onGetUserInfo = () => {
      if (user.role === "student") {
        onGetStudentInfo()
          .then((res: any) => {
            dispatch(updateUser({ data: res.response }));
          })
          .catch((err: any) => {
            console.log(err);
          });
      } else {
        onGetTeacherInfo()
          .then((res: any) => {
            dispatch(updateUser({ data: res.response }));
            console.log(res.response);
            setUserData(res.response);
          })
          .catch((err: any) => {
            console.log(err);
          });
      }
    };
    if (!flag) {
      flag = true;
      onGetUserInfo();
    }
  }, []);

  const [isEditing, setIsEditing] = useState(false);
  // 引入状态管理来管理用户数据
  const user = useSelector((state: RootState) => state.user);
  // 使用useDispatch钩子获取dispatch函数
  const dispatch = useDispatch();

  const [userData, setUserData] = useState(user.data);

  const handleSave = () => {
    // 远端修改数据
    if (user.role === "student") {
      onEditStudentInfo(userData)
        .then((res: any) => {
          if (res.code === 200) {
            message.success(res.message);
          }
        })
        .catch((err: any) => {
          console.log(err);
        });
    } else {
      onEditTeacherInfo(userData)
        .then((res: any) => {
          if (res.code === 200) {
            message.success(res.message);
          }
        })
        .catch((err: any) => {
          console.log(err);
        });
    }
  };

  const changeUserProcessItem = () => {
    let count = 0;
    if (userData?.userName !== null) {
      count += 10;
    }
    if (userData?.age !== null) {
      count += 10;
    }
    if (userData?.sex !== null) {
      count += 10;
    }
    if (userData?.birthDay !== null) {
      count += 10;
    }
    if (userData?.phone !== null) {
      count += 10;
    }
    if (userData?.major !== null) {
      count += 10;
    }
    if (userData?.gradeLevel !== null) {
      count += 10;
    }
    if (userData?.createTime !== null) {
      count += 10;
    }
    if (userData?.status !== null) {
      count += 10;
    }
    if (userData?.realName !== null) {
      count += 10;
    }
    setUserProcess(count);
  };

  useEffect(() => {
    changeUserProcessItem();
  }, [userData]);

  return (
    <div>
      <Descriptions
        bordered
        title={
          <Space style={{ width: "300px" }}>
            <p>个人信息完善程度</p>
            <Progress style={{ width: "200px" }} percent={userProcess} />
          </Space>
        }
        size={"default"}
        extra={
          <Button
            type="primary"
            onClick={() => {
              // 点击这个按钮就是在切换编辑状态
              if (isEditing) {
                handleSave();
              }
              setIsEditing(!isEditing);
            }}
          >
            {isEditing ? "保存" : "编辑"}
          </Button>
        }
      >
        <Descriptions.Item key="1" label="用户名">
          {isEditing ? (
            <Input
              type="text"
              defaultValue={userData.userName}
              onChange={(e) => {
                setUserData({ ...userData, userName: e.target.value });
              }}
            />
          ) : (
            userData?.userName || ""
          )}
        </Descriptions.Item>
        <Descriptions.Item key="2" label="年龄">
          {isEditing ? (
            <Input
              type="text"
              defaultValue={userData.age}
              onChange={(e) => {
                setUserData({ ...userData, age: parseInt(e.target.value) });
              }}
            />
          ) : (
            userData?.age || ""
          )}
        </Descriptions.Item>
        <Descriptions.Item key="3" label="加入时间">
          {userData?.createTime || ""}
        </Descriptions.Item>
        <Descriptions.Item key="4" label="姓名">
          {isEditing ? (
            <Input
              type="text"
              defaultValue={userData.realName}
              onChange={(e) => {
                setUserData({ ...userData, realName: e.target.value });
              }}
            />
          ) : (
            userData?.realName || ""
          )}
        </Descriptions.Item>
        <Descriptions.Item key="5" label="性别">
          {isEditing ? (
            <Radio.Group
              defaultValue={userData.sex}
              onChange={(e) =>
                setUserData({ ...userData, sex: e.target.value })
              }
            >
              <Radio value={1}>男</Radio>
              <Radio value={2}>女</Radio>
            </Radio.Group>
          ) : (
            (userData?.sex === 1 ? "男" : "女") || ""
          )}
        </Descriptions.Item>
        <Descriptions.Item key="6" label="生日">
          {isEditing ? (
            <DatePicker
              defaultValue={
                userData?.birthDay ? moment(userData?.birthDay) : undefined
              }
              onChange={(_, dateString) => {
                setUserData({ ...userData, birthDay: dateString.toString() });
              }}
            />
          ) : (
            moment(userData?.birthDay).format("YYYY-MM-DD") || "未设置"
          )}
        </Descriptions.Item>
        <Descriptions.Item key="7" label="手机号">
          {isEditing ? (
            <Input
              type="text"
              defaultValue={userData.phone}
              onChange={(e) => {
                setUserData({ ...userData, phone: e.target.value });
              }}
            />
          ) : (
            userData?.phone || ""
          )}
        </Descriptions.Item>
        <Descriptions.Item key="8" label="专业">
          {isEditing ? (
            <Input
              type="text"
              defaultValue={userData.major}
              onChange={(e) => {
                setUserData({ ...userData, major: e.target.value });
              }}
            />
          ) : (
            userData?.major || ""
          )}
        </Descriptions.Item>
        <Descriptions.Item key="9" label="年级">
          {isEditing ? (
            <Input
              type="number"
              min="1"
              max="4"
              defaultValue={userData.gradeLevel}
              onChange={(e) => {
                setUserData({
                  ...userData,
                  gradeLevel: parseInt(e.target.value),
                });
              }}
            />
          ) : (
            userData?.gradeLevel || ""
          )}
        </Descriptions.Item>
        <Descriptions.Item key="10" label="当前状态">
          <Badge status="success" text="在线" />
        </Descriptions.Item>
      </Descriptions>
      <br />
      <br />
    </div>
  );
};

export default UserBox;
