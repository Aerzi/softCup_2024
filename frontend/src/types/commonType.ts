export type MonacoEditorProps = {
    value?: string;
    onChange?: (value: string) => void;
    width?: string | number;
    height?: string | number;
    theme?: 'vs' | 'vs-dark' | 'hc-black' | 'hc-light'; // 主题
    select?: boolean; //开启语言选择器
    language?:
      | 'json'
      | 'java'
      | 'python'
      | 'sql'
      | 'mysql'
      | 'html'
      | 'javascript'
      | 'typescript'
      | 'xml'; // 语言
  };