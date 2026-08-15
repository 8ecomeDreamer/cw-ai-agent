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
        String chatId = UUID.randomUUID().toString();
        String answer = cwAiAgentApp.doChatWithTools(message, chatId);
        Assertions.assertNotNull(answer);
    }

}
