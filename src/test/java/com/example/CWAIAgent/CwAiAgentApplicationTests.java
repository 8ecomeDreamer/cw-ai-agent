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
    private CWAiAgentApp dWAiAgentApp;

    @Test
    void contextLoads() {
    }

    @Test
    void doChat() {

        String chatId = UUID.randomUUID().toString();
        String message = "你好，你是谁？";

        // 测试
        String answer = dWAiAgentApp.doChat(message, chatId);
        System.out.println(answer);
        Assertions.assertNotNull(answer);

    }

    @Test
    void doChatWithReport() {

        String chatId = UUID.randomUUID().toString();
        String message = "你好，你是谁？";

        // 测试
        dWAiAgentApp.doChatWithConverter(message, chatId);

    }
}
