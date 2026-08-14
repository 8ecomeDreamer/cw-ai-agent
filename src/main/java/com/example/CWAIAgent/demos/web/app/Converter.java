package com.example.CWAIAgent.demos.web.app;

import com.example.CWAIAgent.demos.web.entity.RecordChatReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

/**
 * 学习结构化输出的内容
 */
@Component
@Slf4j
public class Converter {

    public void useBeanOutputConverter(String message, String chatId, ChatClient chatClient) {

        log.info("方式1：使用高阶Fluent式api输出结果：");
        this.beanOutputConverter1(message, chatId, chatClient);
        log.info("方式2：使用底层api输出结果：");
        this.beanOutputConverter2(message, chatId, chatClient);


    }

    private void beanOutputConverter1(String message, String chatId, ChatClient chatClient) {

        RecordChatReport loveReport = chatClient
        .prompt()
        .user(message)
        .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
        .call()
        .entity(RecordChatReport.class);
        log.info("beanOutputConverter1: {}", loveReport);

    }


    private void beanOutputConverter2(String message, String chatId, ChatClient chatClient) {

        // 1.创建BeanOutputConverter实例
        BeanOutputConverter<RecordChatReport> beanOutputConverter = new BeanOutputConverter<>(RecordChatReport.class);

        // 2.定义模板
        String format = beanOutputConverter.getFormat();
        String template = """
            title：{chatId}
            suggestions： {message}
        """;

        PromptTemplate promptTemplate = new PromptTemplate(template, Map.of("chatId", chatId, "message", message));
        Prompt prompt = promptTemplate.create();

        // todo: 底层api调用报错 格式对不上
        // 3. 调用 ChatModel 并获取 Generation
        ChatResponse response = chatClient.prompt(prompt).user(message).call().chatResponse();
        if (response != null) {
            Generation generation = response.getResult(); // 获取 Generation 对象
            RecordChatReport recordChatReport = beanOutputConverter.convert(generation.getOutput().getText());
            log.info("beanOutputConverter2: {}", recordChatReport);
        }

        log.info("beanOutputConverter2: {}", "找不到beanOutputConverter2结果");

    }

}
