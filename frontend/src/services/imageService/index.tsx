import { post } from "../config/config";

export const generateImage = ({
  width,
  height,
  content,
}: {
  width: number;
  height: number;
  content: string;
}) => {
  return post("student/spark/img/generate", {
    width: width,
    height: height,
    content: content,
  });
};
