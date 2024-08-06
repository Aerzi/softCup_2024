export interface IDocs {
  // 用户生成ppt要求
  query: string;
  // ppt生成主题
  theme?: string;
  // ppt生成模型
  create_model?: string;
  // 业务id
  business_id?: string;
  // 作者名
  author: string;
  // 是否需要ppt演讲备注
  is_card_note: boolean;
  // 是否需要封面
  is_cover_img: boolean;
  // 语种
  language: string;
  // 是否自动配图
  is_figure: boolean;
}

export interface IChapter {
  id?: string;
  chapterTitle?: string;
  fileUrl?: string;
  fileType?: number;
  chartFlag?: boolean;
  searchFlag?: boolean;
  chapterContents?: IChapter[] | null;
}

export interface IOutLine {
  id?: string;
  title?: string;
  subTitle?: string;
  fileUrl?: string;
  fileType?: number;
  chapters?: IChapter[];
}
