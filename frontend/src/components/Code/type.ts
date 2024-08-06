export interface ICode {
  language_id: number;
  source_code?: string;
  stdin?: string;
  questionId: number;
  classId: number;
  codeType: string;
}

export interface IAnswer {
  feedback?: null;
  modified_code?: null;
  error_analysis?: string;
  optimization_suggestions?: string;
}
