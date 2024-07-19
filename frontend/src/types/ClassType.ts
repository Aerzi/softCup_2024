export interface IClassType {
  name: string;
  description: string;
}

// 状态类型在上述的基础上添加
export interface IClassStateType extends IClassType {
  createTime: string;
  modifyTime: string;
  status: number;
  userId: number;
}
