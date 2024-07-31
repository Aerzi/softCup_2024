import { getLocalData } from "../../utils/Storage";
import request from "../http/http";

export const thoughtChainGenerate = ({ question, method_choice }: any) => {
  return request(
    "post",
    "student/project/thought/chain/generate",
    {
      question: question,
      method_choice: method_choice,
    },
    {
      headers: {
        Authorization:
          getLocalData("token") !== null ? getLocalData("token") : "",
      },
    }
  );
};
