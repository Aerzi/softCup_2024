export interface ILog {
  id: number;
  userId?: number;
  userName: string;
  content?: string;
  createTime?: string;
}

export interface IUser {
  gradeLevel?: number;
  major?: string;
  id?: number;
  userUuid?: string;
  userName: string;
  password: string;
  realName?: string;
  age?: number;
  sex?: number;
  birthDay?: string;
  phone?: string;
  role?: number;
  status?: number;
  imagePath?: number;
  createTime?: string;
  modifyTime?: string;
  lastActiveTime?: string;
  deleted?: number;
}
