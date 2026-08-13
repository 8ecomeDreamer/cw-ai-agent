package com.example.CWAIAgent.demos.web.advisor;

import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;

/**
 * 自定义拦截器
 * 非流式场景：CallAroundAdvisor，实现aroundCall方法
 * 流式场景： StreamAroundAdvisor，实现aroundStream方法
 */
public class MyCustomAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
//        1.处理请求（前置处理）
        AdvisedRequest modifiedRequest = processRequest(advisedRequest);
//        2.调用链中的下一个Advisor
        AdvisedResponse response = chain.nextAroundCall(modifiedRequest);
//        3.处理响应（后置处理）
        return processResponse(response);
    }

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
//        1.处理请求
        AdvisedRequest modified = processRequest(advisedRequest);
//        2.调用链中的下一个Advisor并处理流式响应
        return chain.nextAroundStream(modified)
                .map(response -> processResponse(response));
    }

    @Override
    public String getName() {
        return "第一个自定义拦截器";
    }

    @Override
    public int getOrder() {
        // 返回值越小，优先级越高
        return 100;
    }


    private AdvisedRequest processRequest(AdvisedRequest advisedRequest) {
        return advisedRequest;
    }

    private AdvisedResponse processResponse(AdvisedResponse advisedResponse) {
        return advisedResponse;
    }
}
