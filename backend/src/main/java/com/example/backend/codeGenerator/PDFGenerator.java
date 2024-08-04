package com.example.backend.codeGenerator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;
import java.io.IOException;


public class PDFGenerator {
    public static void main(String[] args) {
        String jsonString = "{ \"result\": [ \"步骤 1: 确定项目主要目标和每一个潜在的子目标\\n\\n主要目标：开发一个基于UML的旅游团队管理系统，以提高旅游团队的管理效率和客户满意度。\\n\\n子目标：\\n1. 客户信息管理\\n2. 旅游产品管理\\n3. 订单处理\\n4. 支付系统集成\\n5. 客户服务与支持\\n6. 报表生成与分析\\n7. 系统安全与维护\", \"步骤 2: 为每个子目标提出至少两个可能的、十分详细的子目标解决方案\\n\\n子目标1: 客户信息管理\\n方案A: 开发一个全面的客户资料库，包括个人信息、旅行偏好、历史订单等，使用加密技术保护数据安全。\\n方案B: 实现一个简单的客户信息录入界面，仅包含基本信息，不涉及高级数据处理或加密措施。\\n\\n子目标2: 旅游产品管理\\n方案A: 设计一个动态的产品目录，允许管理员根据季节和需求更新旅游产品信息，包括实时库存管理和价格调整功能。\\n方案B: 创建一个静态的产品列表，需要手动更新，不具备实时库存监控或自动价格调整功能。\\n\\n（由于篇幅限制，以下子目标只列出方案标题）\\n\\n子目标3: 订单处理\\n方案A: 自动化订单流程管理系统\\n方案B: 手动订单输入和处理系统\\n\\n子目标4: 支付系统集成\\n方案A: 多支付网关集成\\n方案B: 单一支付方式处理\\n\\n子目标5: 客户服务与支持\\n方案A: AI聊天机器人与人工客服相结合\\n方案B: 仅人工客服系统\\n\\n子目标6: 报表生成与分析\\n方案A: 自动化智能报表系统\\n方案B: 手动数据输入和报表生成\\n\\n子目标7: 系统安全与维护\\n方案A: 定期自动安全审计和更新\\n方案B: 按需手动安全检查和系统更新\", \"步骤 3: 评估每一个子目标解决方案会带来的结果\\n\\n子目标1:\\n效果卓越 - 方案A\\n效果一般 - 方案B\\n\\n子目标2:\\n效果卓越 - 方案A\\n效果不佳 - 方案B\\n\\n（同理评估其他子目标的解决方案）\", \"步骤 4: 选择最优异的解决方案，结合主要目标为项目生成具有时序逻辑的详尽开发流程\\n\\n选定解决方案：客户信息管理-方案A，旅游产品管理-方案A，订单处理-方案A，支付系统集成-方案A，客户服务与支持-方案A，报表生成与分析-方案A，系统安全与维护-方案A。\\n\\n开发流程：\\n1. 需求分析和规划阶段：详细收集用户需求，规划系统架构和功能模块。\\n2. 设计阶段：使用UML工具设计系统类图、用例图、活动图等。\\n3. 实施阶段：按照设计文档开发系统，集成数据库和第三方服务。\\n4. 测试阶段：进行单元测试、集成测试和系统测试，确保软件质量。\\n5. 部署阶段：在服务器上部署应用，进行压力测试和安全检查。\\n6. 运维阶段：监控系统性能，定期进行数据备份和系统更新。\", \"步骤 5: 总结开发流程，着重指出难点\\n\\n开发流程总结：本项目通过精心设计和选择合适的技术方案，实现了一个高效、安全的旅游团队管理系统。项目难点主要包括高度定制化的客户信息管理、动态产品目录的实现以及支付系统的多网关集成。通过团队合作和技术创新，这些难点被逐一克服，最终交付了一个满足业务需求和用户期望的系统。\" ], \"tree_structure\": { \"name\": \"指导概览\", \"children\": [ { \"name\": \"步骤 1: 确定项目主要目标和每一个潜在的子目标\", \"children\": [ { \"name\": \"主要目标：开发一个基于UML的旅游团队管理系统，以提高旅游团队的管理效率和客户满意度。\", \"children\": [] }, { \"name\": \"子目标：\", \"children\": [ { \"name\": \"1. 客户信息管理\", \"children\": [] }, { \"name\": \"2. 旅游产品管理\", \"children\": [] }, { \"name\": \"3. 订单处理\", \"children\": [] }, { \"name\": \"4. 支付系统集成\", \"children\": [] }, { \"name\": \"5. 客户服务与支持\", \"children\": [] }, { \"name\": \"6. 报表生成与分析\", \"children\": [] }, { \"name\": \"7. 系统安全与维护\", \"children\": [] } ] } ] }, { \"name\": \"步骤 2: 为每个子目标提出至少两个可能的、十分详细的子目标解决方案\", \"children\": [ { \"name\": \"子目标1: 客户信息管理\", \"children\": [ { \"name\": \"方案A: 开发一个全面的客户资料库，包括个人信息、旅行偏好、历史订单等，使用加密技术保护数据安全。\", \"children\": [] }, { \"name\": \"方案B: 实现一个简单的客户信息录入界面，仅包含基本信息，不涉及高级数据处理或加密措施。\", \"children\": [] } ] }, { \"name\": \"子目标2: 旅游产品管理\", \"children\": [ { \"name\": \"方案A: 设计一个动态的产品目录，允许管理员根据季节和需求更新旅游产品信息，包括实时库存管理和价格调整功能。\", \"children\": [] }, { \"name\": \"方案B: 创建一个静态的产品列表，需要手动更新，不具备实时库存监控或自动价格调整功能。\", \"children\": [] }, { \"name\": \"（由于篇幅限制，以下子目标只列出方案标题）\", \"children\": [] } ] }, { \"name\": \"子目标3: 订单处理\", \"children\": [ { \"name\": \"方案A: 自动化订单流程管理系统\", \"children\": [] }, { \"name\": \"方案B: 手动订单输入和处理系统\", \"children\": [] } ] }, { \"name\": \"子目标4: 支付系统集成\", \"children\": [ { \"name\": \"方案A: 多支付网关集成\", \"children\": [] }, { \"name\": \"方案B: 单一支付方式处理\", \"children\": [] } ] }, { \"name\": \"子目标5: 客户服务与支持\", \"children\": [ { \"name\": \"方案A: AI聊天机器人与人工客服相结合\", \"children\": [] }, { \"name\": \"方案B: 仅人工客服系统\", \"children\": [] } ] }, { \"name\": \"子目标6: 报表生成与分析\", \"children\": [ { \"name\": \"方案A: 自动化智能报表系统\", \"children\": [] }, { \"name\": \"方案B: 手动数据输入和报表生成\", \"children\": [] } ] }, { \"name\": \"子目标7: 系统安全与维护\", \"children\": [ { \"name\": \"方案A: 定期自动安全审计和更新\", \"children\": [] }, { \"name\": \"方案B: 按需手动安全检查和系统更新\", \"children\": [] } ] } ] }, { \"name\": \"步骤 3: 评估每一个子目标解决方案会带来的结果\", \"children\": [ { \"name\": \"子目标1:\", \"children\": [ { \"name\": \"效果卓越 - 方案A\", \"children\": [] }, { \"name\": \"效果一般 - 方案B\", \"children\": [] } ] }, { \"name\": \"子目标2:\", \"children\": [ { \"name\": \"效果卓越 - 方案A\", \"children\": [] }, { \"name\": \"效果不佳 - 方案B\", \"children\": [] }, { \"name\": \"（同理评估其他子目标的解决方案）\", \"children\": [] } ] } ] }, { \"name\": \"步骤 4: 选择最优异的解决方案，结合主要目标为项目生成具有时序逻辑的详尽开发流程\", \"children\": [ { \"name\": \"选定解决方案：客户信息管理-方案A，旅游产品管理-方案A，订单处理-方案A，支付系统集成-方案A，客户服务与支持-方案A，报表生成与分析-方案A，系统安全与维护-方案A。\", \"children\": [] }, { \"name\": \"开发流程：\", \"children\": [ { \"name\": \"1. 需求分析和规划阶段：详细收集用户需求，规划系统架构和功能模块。\", \"children\": [] }, { \"name\": \"2. 设计阶段：使用UML工具设计系统类图、用例图、活动图等。\", \"children\": [] }, { \"name\": \"3. 实施阶段：按照设计文档开发系统，集成数据库和第三方服务。\", \"children\": [] }, { \"name\": \"4. 测试阶段：进行单元测试、集成测试和系统测试，确保软件质量。\", \"children\": [] }, { \"name\": \"5. 部署阶段：在服务器上部署应用，进行压力测试和安全检查。\", \"children\": [] }, { \"name\": \"6. 运维阶段：监控系统性能，定期进行数据备份和系统更新。\", \"children\": [] } ] } ] }, { \"name\": \"步骤 5: 总结开发流程，着重指出难点\", \"children\": [ { \"name\": \"开发流程总结：本项目通过精心设计和选择合适的技术方案，实现了一个高效、安全的旅游团队管理系统。项目难点主要包括高度定制化的客户信息管理、动态产品目录的实现以及支付系统的多网关集成。通过团队合作和技术创新，这些难点被逐一克服，最终交付了一个满足业务需求和用户期望的系统。\", \"children\": [] } ] } ] } }";

        // 获取用户桌面路径
        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + "/Desktop/";

        // 创建PDF写入器，并指定输出路径
        try {
            PdfWriter writer = new PdfWriter(desktopPath + "output.pdf");
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // 加载支持中文的字体
            String fontPath = "src/main/resources/fonts/simsun.ttc,1"; // 替换为系统中存在的字体路径
            PdfFont font = null;
            try {
                font = PdfFontFactory.createFont(fontPath, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            document.setFont(font);

            // 解析JSON数据
            JSONObject jsonObj = JSON.parseObject(jsonString);

            // 添加result内容
            JSONArray resultArray = jsonObj.getJSONArray("result");
            System.out.println(resultArray);
            for (int i = 0; i < resultArray.size(); i++) {
                document.add(new Paragraph(resultArray.getString(i)));
                System.out.println(resultArray.getString(i));
            }

            // 关闭文档
            document.close();
            System.out.println("PDF文件已保存到桌面！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 递归添加树形结构内容
    private static void addTreeStructure(Document document, JSONObject node, int level) {
        String indentation = "  ".repeat(level);  // 根据层级增加缩进
        document.add(new Paragraph(indentation + node.getString("name")));

        JSONArray children = node.getJSONArray("children");
        for (int i = 0; i < children.size(); i++) {
            JSONObject child = children.getJSONObject(i);
            addTreeStructure(document, child, level + 1);
        }
    }
}
