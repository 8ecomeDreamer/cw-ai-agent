package com.example.CWAIAgent;

import com.example.CWAIAgent.demos.web.app.CWAiAgentApp;
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

        // 测试
        String message = "你好，你是谁？";
        String answer = dWAiAgentApp.doChat(chatId, message);
        System.out.println(answer);
        Assertions.assertNotNull(answer);

    }
}
