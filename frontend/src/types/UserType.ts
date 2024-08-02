// 定义UserState的类型
export interface UserState {
  role: string;
  isLogin: boolean;
  data: UserInfo;
}

export interface UserInfo {
  gradeLevel: number;
  major: string;
  id: number;
  userUuid: string;
  userName: string;
  password: string;
  realName: string;
  age: number;
  sex: number;
  birthDay: string;
  phone: string;
  role: number;
  status: number;
  imagePath: number;
  createTime: string;
  modifyTime: string;
  lastActiveTime: string;
  deleted: number;
}
