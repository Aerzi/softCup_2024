import { post } from "../config/config";

// 思维链
export const thoughtChainGenerate = ({ question, method_choice }: any) => {
  return post("student/project/thought/chain/generate", {
    question: question,
    method_choice: method_choice,
  });
};

export const downloadPDF = (result: string) => {
  return post("student/project/pdf/save", {
    result: result,
  });
};
