import { getLocalData } from "../../utils/Storage";
import request from "../http/http";

export const onSubmit = ({
  language_id,
  source_code,
  stdin,
  questionId,
  classId,
}: any) => {
  return request(
    "post",
    "student/judge/submit",
    {
      request: {
        stdin: stdin,
        language_id: language_id,
        source_code: source_code,
      },
      questionId: questionId,
      classId: classId,
    },
    {
      headers: {
        Authorization:
          getLocalData("token") !== null ? getLocalData("token") : "",
      },
    }
  );
};
