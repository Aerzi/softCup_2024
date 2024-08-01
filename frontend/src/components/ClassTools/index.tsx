import React from "react";
import "./index.less";
import { AlertTwoTone, BellTwoTone, CopyTwoTone, FundTwoTone, PieChartTwoTone, SmileTwoTone } from "@ant-design/icons";

const ClassTools = () => {
    return (
        <div className="xf-class-tools__page">
            <div className="xf-class-tools__content">
                <div className="xf-class-tools__content-header">
                    <h3 className="xf-class-tools__content-title">智能管理--与AI共行</h3>
                    <p className="xf-class-tools__content-subtitle">Classroom Teaching Toolsets</p>
                </div>
                <div className="xf-class-tools__content-main">
                    <div className="xf-class-tools__content-main-card-list">
                        <div className="xf-class-tools__content-main-card">
                            {/* 这部分应该选择双色icon */}
                            <FundTwoTone className="xf-class-tools__content-main-card-icon" twoToneColor="rgb(106,123,252)" />
                            <div style={{margin: "0 15px"}}>
                                <h4 className="xf-class-tools__content-main-card-title">课堂灵活组织</h4>
                                <h6 className="xf-class-tools__content-main-card-content">教学课堂是一种集成化班级协同空间，支持实验、作业、视频、考试、毕设等十余种线上线下管理工具。</h6>
                            </div>
                        </div>
                        <div className="xf-class-tools__content-main-card">
                        <CopyTwoTone className="xf-class-tools__content-main-card-icon" twoToneColor="rgb(106,123,252)" />
                        <div style={{margin: "0 15px"}}>
                            <h4 className="xf-class-tools__content-main-card-title">作业自动管理</h4>
                            <h6 className="xf-class-tools__content-main-card-content">教学课堂是一种集成化班级协同空间，支持实验、作业、视频、考试、毕设等十余种线上线下管理工具。</h6>
                        </div>
                        </div>
                        <div className="xf-class-tools__content-main-card">
                        <SmileTwoTone className="xf-class-tools__content-main-card-icon" twoToneColor="rgb(106,123,252)" />
                        <div style={{margin: "0 15px"}}>
                                <h4 className="xf-class-tools__content-main-card-title">分组协同开发</h4>
                                <h6 className="xf-class-tools__content-main-card-content">教学课堂是一种集成化班级协同空间，支持实验、作业、视频、考试、毕设等十余种线上线下管理工具。</h6>
                            </div>
                        </div>
                        <div className="xf-class-tools__content-main-card">
                        <BellTwoTone className="xf-class-tools__content-main-card-icon" twoToneColor="rgb(106,123,252)" />
                        <div style={{margin: "0 15px"}}>
                                <h4 className="xf-class-tools__content-main-card-title">模拟竞赛实景</h4>
                                <h6 className="xf-class-tools__content-main-card-content">教学课堂是一种集成化班级协同空间，支持实验、作业、视频、考试、毕设等十余种线上线下管理工具。</h6>
                            </div>
                        </div>
                        <div className="xf-class-tools__content-main-card">
                        <PieChartTwoTone className="xf-class-tools__content-main-card-icon" twoToneColor="rgb(106,123,252)" />
                        <div style={{margin: "0 15px"}}>
                                <h4 className="xf-class-tools__content-main-card-title">结果导向统计</h4>
                                <h6 className="xf-class-tools__content-main-card-content">教学课堂是一种集成化班级协同空间，支持实验、作业、视频、考试、毕设等十余种线上线下管理工具。</h6>
                            </div>
                        </div>
                        <div className="xf-class-tools__content-main-card">
                        <AlertTwoTone className="xf-class-tools__content-main-card-icon" twoToneColor="rgb(106,123,252)" />
                        <div style={{margin: "0 15px"}}>
                                <h4 className="xf-class-tools__content-main-card-title">AI伴您同行</h4>
                                <h6 className="xf-class-tools__content-main-card-content">教学课堂是一种集成化班级协同空间，支持实验、作业、视频、考试、毕设等十余种线上线下管理工具。</h6>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ClassTools;