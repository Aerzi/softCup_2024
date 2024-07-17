import { Select, Space } from 'antd';
import React,{ useState } from 'react';
import MonacoEditor from 'react-monaco-editor';
import './index.less';
import { MonacoEditorProps } from '../../types/commonType';
import { ProCard } from '@ant-design/pro-components';

 
const CodeSphere = (props: MonacoEditorProps) => {
  const {
    width = '100%',
    height = '600px',
    value,
    theme = 'vs',
    select = false,
    language = 'typescript',
    onChange,
  } = props;
 
  const [languageValue, setLanguageValue] = useState(language);
 
  const options: any = {
    selectOnLineNumbers: true,
    roundedSelection: false,
    readOnly: false, // //是否只读  取值 true | false
    cursorStyle: 'line',
    automaticLayout: false, // 自动布局
  };
 
  const extraSelect = (
    <Space>
      <Select
        disabled={!select}
        bordered={false}
        popupMatchSelectWidth={120}
        defaultValue={languageValue}
        onChange={(e) => setLanguageValue(e)}
        options={[
          { label: 'json', value: 'json' },
          { label: 'java', value: 'java' },
          { label: 'python', value: 'python' },
          { label: 'sql', value: 'sql' },
          { label: 'mysql', value: 'mysql' },
          { label: 'html', value: 'html' },
          { label: 'javascript', value: 'javascript' },
          { label: 'typescript', value: 'typescript' },
          { label: 'xml', value: 'xml' },
        ]}
      />
    </Space>
  );
 
  return (
    <div className='xf-code-sphere__page'>
        <ProCard
            title={extraSelect}
            style={{ width: '100%', height: '100%', }}
            className="card-theme-light"
        >
            <MonacoEditor
                {...props}
                width={width}
                height={height}
                value={value ?? `// ${languageValue}`}
                theme={theme}
                language={languageValue}
                options={options}
                onChange={(newValue) => onChange?.(newValue)}
            />
        </ProCard>
    </div>
  );
};

export default CodeSphere;