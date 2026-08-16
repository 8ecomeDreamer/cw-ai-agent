package com.example.CWAIAgent.demos.web.app;

import com.example.CWAIAgent.demos.web.advisor.MyCustomAdvisor;
import com.example.CWAIAgent.demos.web.chatmemory.FileBasedChatMemory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Component
@Slf4j
public class CWAiAgentApp {

    private final ChatClient chatClient;
    @Resource
    private Converter converter;

//    @Resource
//    private RagCloudAdvisor ragCloudAdvisor;

    @Resource
    private VectorStore cwAiAgentAppVectorStore;

    @Resource
    private ToolCallback[] allTools;
    @Resource
    private ToolCallbackProvider toolCallbackProvider;

    private static final String SYSTEM_PROMPT = "你是《少林足球》主角阿星（五师兄，周星驰饰演），身怀大力金刚腿。\n" +
            "你是少林寺俗家弟子，毕生心愿让少林功夫被世人看见；你并不是足球队教练。\n" +
            "吴孟达饰演的明锋，外号黄金右脚，早年被反派强雄陷害踢假球、右腿被打断，落魄酗酒，后续由他担任少林足球队主教练。\n" +
            "\n" +
            "你的性格乐天、执着、无厘头、热血纯粹，说话简短，时常说出电影原版台词梗。\n" +
            "用户是你的搭档，全程陪同你走完整部电影剧情：\n" +
            "前期你靠回收废品谋生、偶遇落魄的明锋、说服他组队踢球；挨个寻找生活落魄的五位同门师兄弟，组建少林足球队，一路闯关，最终对战强雄手下嗑药的魔鬼队。\n" +
            "严格遵从原版电影流程推进剧情，不要自创人物；说话风格贴合阿星淳朴搞笑的人设。\n" +
            "牢记座右铭：做人如果没有梦想，那跟咸鱼有什么分别？\n";

    public CWAiAgentApp(ChatModel dashScopeChatModel) {

        // 添加对话记忆持久化
        String fileDir = System.getProperty("user.dir") + "/chat-memory";
        // 创建带有记忆功能的ChatClient进行对话
//        ChatMemory chatMemory = new InMemoryChatMemory(fileDir);
        ChatMemory chatMemory = new FileBasedChatMemory(fileDir);
        ChatClient.Builder chatClientBuilder = ChatClient.builder(dashScopeChatModel);
        chatClient = chatClientBuilder
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory)
//                        new MyCustomAdvisor()
//                         自定义重读拦截器，按需启动
//                        new MyCustomReReadingAdvisor()
                )
                .build();
    }



    public String doChat(String message, String chatId) {
        ChatResponse response = chatClient
                .prompt() // 初始化对话构建器
                .user(message) // 用户信息
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId) // 会话隔离,每个chatId提供不同的conversation_id
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10)) // 为每个会话提供最多10条记忆
                .call() // 触发模型调用
                .chatResponse(); // 获取响应结果
        String content = null;
        if (response != null) {
            content = response.getResult().getOutput().getText();
        }
        log.info("content: {}", content);
        return content;
    }


    /**
     * @param message 用户输入的需求
     * @param chatId  会话id,用于会话隔离
     *
     * 可以将结果返回不同类型的converter
     * 常用converter: BeanOutputConverter、ListOutPutConverter、MapOutputConverter
     */
    public void doChatWithConverter(String message, String chatId) {

        // 将结果转换为bean类
        log.info("1.输出格式1：BeanOutputConverter ");
        converter.useBeanOutputConverter(message, chatId, chatClient);

        // 将结果转换为map todo
        log.info("2.输出格式1：MapOutputConverter ");

        // 将结果转换为List todo
        log.info("3.输出格式1：ListOutputConverter ");

    }

    public String doChatWithRag(String message, String chatId) {
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                // 开启日志，便于观察效果
                .advisors(new MyCustomAdvisor())
                // 应用增强检索服务（云知识库服务）
                .advisors(new QuestionAnswerAdvisor(cwAiAgentAppVectorStore))
                .call()
                .chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }


    public String doChatWithTools(String message, String chatId) {
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                // 开启日志，便于观察效果
                .advisors(new MyCustomAdvisor())
                .tools(allTools)
                .call()
                .chatResponse();
        String content = response.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }


    public String doChatWithMcp(String message, String chatId) {
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                // 开启日志，便于观察效果
                .advisors(new MyCustomAdvisor())
                .tools(toolCallbackProvider)
                .call()
                .chatResponse();
        String content = response.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }


}





