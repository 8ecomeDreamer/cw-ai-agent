package com.example.CWAIAgent;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.protocol.Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CwAiAgentApplication {

//    public static void main(String[] args) {
//        SpringApplication.run(CwAiAgentApplication.class, args);
//    }


    public static GenerationResult callWithMessage() throws ApiException, NoApiKeyException, InputRequiredException {
        // 以下为华北2（北京）地域的URL，调用时请将{WorkspaceId}替换为真实的业务空间ID，各地域的URL不同。
        Generation gen = new Generation(Protocol.HTTP.getValue(), "https://llm-c8ub6u22sryrqibo.cn-beijing.maas.aliyuncs.com/api/v1");
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content("You are a helpful assistant.")
                .build();
        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content("你是谁？")
                .build();
        GenerationParam param = GenerationParam.builder()
                // 若没有配置环境变量，请用阿里云百炼API Key将下行替换为：.apiKey("sk-xxx")
                // 各地域的API Key不同。获取API Key：https://help.aliyun.com/zh/model-studio/get-api-key
                .apiKey(System.getenv("BAILIAN_API_KEY"))
                // qwen3.7-max、qwen3.7-max-2026-05-20和qwen3.6-max-preview仅支持文本接口，qwen3.8-max、qwen3.7-max-2026-06-08支持多模态接口，Qwen3.6、Qwen3.5系列需要使用多模态接口，直接替换模型会导致报错
                .model("qwen-plus")
                .messages(Arrays.asList(systemMsg, userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();
        return gen.call(param);
    }
    public static void main(String[] args) {
        try {
            GenerationResult result = callWithMessage();
            System.out.println(result.getOutput().getChoices().get(0).getMessage().getContent());
            // 如需查看完整响应，请取消下列注释
            // System.out.println(JsonUtils.toJson(result));
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.err.println("错误信息："+e.getMessage());
            System.out.println("请参考文档：https://help.aliyun.com/zh/model-studio/developer-reference/error-code");
        }
    }

}
