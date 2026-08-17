package com.example.CWAIAgent;

import com.example.CWAIAgent.demos.web.app.CWAiAgentApp;
import com.example.CWAIAgent.demos.web.entity.ChatReport;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;

import java.util.UUID;

@SpringBootTest
@ActiveProfiles("local")
class CwAiAgentApplicationTests {

    @Resource
    private CWAiAgentApp cwAiAgentApp;

    @Test
    void contextLoads() {
    }

    @Test
    void doChat() {

        String chatId = UUID.randomUUID().toString();
        String message = "你好，你是谁？";

        // 测试
        String answer = cwAiAgentApp.doChat(message, chatId);
        System.out.println(answer);
        Assertions.assertNotNull(answer);

    }

    @Test
    void doChatWithReport() {

        String chatId = UUID.randomUUID().toString();
        String message = "你好，你是谁？";

        // 测试
        cwAiAgentApp.doChatWithConverter(message, chatId);

    }


    @Test
    void doChatWithMemory() {

        String chatId = UUID.randomUUID().toString();
        String message = "记忆存储？";

        // 测试
        cwAiAgentApp.doChat(message, chatId);

    }

    @Test
    void doChatWithRag() {

        String chatId = UUID.randomUUID().toString();
        String message = "啊梅是谁？";

        // 测试
        cwAiAgentApp.doChatWithRag(message, chatId);
//        dWAiAgentApp.doChat(message, chatId);
    }

    @Test
    void doChatWithConverter() {
    }

    @Test
    void doChatWithTools() {
        // 将联网搜索和生成图片和生成pdf功能结合在一起
        testMessage("帮我搜索一下周星驰喜剧之王的文案，再配一张它的照片，生成在pdf中。");
    }


    private void testMessage(String message) {
        // 生成一个随机的聊天ID
        String chatId = UUID.randomUUID().toString();
        // 使用AI代理处理输入的消息，并获取回答
        String answer = cwAiAgentApp.doChatWithTools(message, chatId);
        // 断言回答不为空，确保AI返回了有效响应
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithMcp() {
        String chatId = UUID.randomUUID().toString();
        // 测试地图 MCP
        String message = "请帮我找到周星驰的家";
        String answer = cwAiAgentApp.doChatWithMcp(message, chatId);
        System.out.println(answer);
    }
}


