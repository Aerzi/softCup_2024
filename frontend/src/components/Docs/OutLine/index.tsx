import React from "react";
import { Divider, List } from "antd";
import { IChapter, IOutLine } from "../type";
import Title from "antd/es/skeleton/Title";

const ChapterItem = ({
  chapter,
  fatherIndex,
}: {
  chapter: IChapter;
  fatherIndex: string;
}) => {
  return (
    <List.Item>
      <Divider>子标题 - {chapter?.chapterTitle}</Divider>
      {chapter?.chapterContents && (
        <ChapterList
          chapters={chapter.chapterContents}
          fatherIndex={fatherIndex}
        />
      )}
    </List.Item>
  );
};

const ChapterList = ({
  chapters,
  fatherIndex,
}: {
  chapters: IChapter[];
  fatherIndex: string;
}) => {
  return (
    <List>
      {chapters.map((chapter, index) => (
        <>
          <Divider>
            <h4 key={index}>
              章节{fatherIndex}.{index + 1}
            </h4>
          </Divider>
          <ChapterItem
            key={chapter?.id}
            chapter={chapter}
            fatherIndex={fatherIndex + "." + (index + 1)}
          />
        </>
      ))}
    </List>
  );
};

const OutlineComponent = ({ outline }: { outline: IOutLine }) => {
  return (
    <div style={{ fontFamily: "阿里妈妈数黑体 Bold", width: "100%" }}>
      <h1 style={{ marginBottom: "3px" }}>主标题 - {outline.title}</h1>
      <h2 style={{ marginBottom: "5px" }}>副标题 - {outline.subTitle}</h2>
      <Divider>
        <h3 style={{ marginBottom: "5px" }}>目录</h3>
      </Divider>
      <ChapterList chapters={outline.chapters} fatherIndex={"1"} />
    </div>
  );
};

export default OutlineComponent;
