import React, { useState } from "react";
import NavHeader from "../../components/NavHeader";
import "./index.less";
import CodeSphere from "../../components/CodeSphere";
import { ProCard } from "@ant-design/pro-components";
import LinkLoader from "../../components/LinkLoader";
import ChatBox from "../../components/ChatBox";

const SoftwareDevelopment = () => {
  const [code, setCode] = useState('');
  const [isLinkLoading, setIsLinkLoading] = useState(false);

  const handleEditorChange = (newValue: string) => {
    setCode(newValue);
  };

  return (
    <>
      <div className="xf-soft__page">
        <header className="xf-soft__header">
          <NavHeader />
        </header>
        <main className="xf-soft__main">
          <div className="xf-soft__main-content">
            <div className="xf-soft__main-content-left">
              <ProCard bordered boxShadow className="xf-soft__main--content-left-card">
                <LinkLoader isLinkLoading={isLinkLoading} setIsLinkLoading={setIsLinkLoading} />
              </ProCard>
            </div>
            <div className="xf-soft__main-content-right">
              {
                isLinkLoading ? <ChatBox /> : <CodeSphere
                  value={code}
                  language={"javascript" || "json" || "typescript" || "mysql" || "html" || "xml" || "python"} 
                  onChange={handleEditorChange}
                  select={true}
                />
              }

            </div>
          </div>
        </main>
        <footer className="xf-soft__footer"></footer>
      </div>
      <div className="xf-soft__mask"></div>
    </>
  );
};

export default SoftwareDevelopment;
