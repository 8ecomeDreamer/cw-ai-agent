package com.example.CWAIAgent.demos.web.controller;

import com.example.CWAIAgent.demos.web.app.CWAiAgentApp;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class AiController {

    @Resource
    private CWAiAgentApp cwAiAgentApp;

    @Resource
    private ToolCallback[] alltools;

    @Resource
    private ChatModel chatModel;

    @GetMapping("/chat")
    public String chat(String message, String ChatId) {
        return cwAiAgentApp.doChat(message, ChatId);
    }

    @GetMapping(value = "/chat/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatSSE(@RequestParam("message") String message, @RequestParam("chatId") String chatId) {
        return cwAiAgentApp.doChatByStream(message, chatId);
    }
}
