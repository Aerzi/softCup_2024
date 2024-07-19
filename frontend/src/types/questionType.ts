export interface IQuestion {
  name: string;
  type: string;
  description?: string;
  content: string;
  score?: number;
  difficult?: number;
  correct: string;
  createTime?: string;
  modifyTime?: string;
  classId?: number;
}
