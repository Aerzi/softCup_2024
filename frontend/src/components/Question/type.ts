export interface IQuestion {
  id?: number;
  name?: string;
  classId: number;
  // 表示具体问题内容
  description?: string;
  // 输入/输出
  format?: string;
  example?: string;
  // 根据difficult来取等级
  difficult?: number;
  tips?: string;
  createTime?: string;
  modifyTime?: string;
}
