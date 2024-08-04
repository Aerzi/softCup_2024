import { ICode } from "../../components/Code/type";
import { post } from "../config/config";

export const submitAnswer = ({
  language_id,
  source_code,
  stdin,
  questionId,
  classId,
  codeType,
}: ICode) => {
  return post("student/judge/submit", {
    request: {
      stdin: stdin,
      language_id: language_id,
      source_code: source_code,
    },
    questionId: questionId,
    classId: classId,
    codeType: codeType,
  });
};
