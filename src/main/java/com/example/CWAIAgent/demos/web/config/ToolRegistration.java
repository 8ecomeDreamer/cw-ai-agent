package com.example.CWAIAgent.demos.web.config;

import com.example.CWAIAgent.demos.web.tools.*;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolRegistration {

    @Value("${web-search-api-key}")
    private String searchApiKey;

/**
 * 创建并返回所有工具回调的数组
 * 该方法初始化了多种工具实例，并将它们转换为工具回调数组
 *
 * @return 包含所有工具回调的数组，用于后续的工具调用处理
 */
    @Bean
    public ToolCallback[] allTools() {
    // 创建文件操作工具实例
        FileOperationTool fileOperationTool = new FileOperationTool();
    // 创建网络搜索工具实例，需要传入搜索API密钥
        WebSearchTool webSearchTool = new WebSearchTool(searchApiKey);
    // 创建网页抓取工具实例
        WebScrapingTool webScrapingTool = new WebScrapingTool();
    // 创建资源下载工具实例
        ResourceDownloadTool resourceDownloadTool = new ResourceDownloadTool();
    // 创建终端操作工具实例
        TerminalOperationTool terminalOperationTool = new TerminalOperationTool();
    // 创建PDF生成工具实例
        PDFGenerationTool pdfGenerationTool = new PDFGenerationTool();
    // 将所有工具实例转换为工具回调数组并返回
        return ToolCallbacks.from(
                fileOperationTool,
                webSearchTool,
                webScrapingTool,
                resourceDownloadTool,
                terminalOperationTool,
                pdfGenerationTool
        );
    }
}
