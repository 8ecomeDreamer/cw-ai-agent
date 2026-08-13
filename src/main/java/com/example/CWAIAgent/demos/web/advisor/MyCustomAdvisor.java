package com.example.CWAIAgent.demos.web.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.model.MessageAggregator;
import reactor.core.publisher.Flux;

/**
 * 自定义拦截器
 * 非流式场景：CallAroundAdvisor，实现aroundCall方法
 * 流式场景： StreamAroundAdvisor，实现aroundStream方法
 */
@Slf4j
public class MyCustomAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
//        1.处理请求（前置处理）
        advisedRequest = this.processRequest(advisedRequest);
//        2.调用链中的下一个Advisor
        AdvisedResponse response = chain.nextAroundCall(advisedRequest);
        this.processResponse(response);
//        3.处理响应（后置处理）
        return response;
    }

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
//        1.处理请求
        advisedRequest = this.processRequest(advisedRequest);
//        2.调用链中的下一个Advisor并处理流式响应
        Flux<AdvisedResponse> advisedResponseFlux =  chain.nextAroundStream(advisedRequest);
        return (new MessageAggregator()).aggregateAdvisedResponse(advisedResponseFlux, this::processResponse);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        // 返回值越小，优先级越高
        return 0;
    }


    private AdvisedRequest processRequest(AdvisedRequest advisedRequest) {
        log.info("AI Request:", advisedRequest.userText());
        return advisedRequest;
    }

    private void processResponse(AdvisedResponse advisedResponse) {
        log.info("AI Response:", advisedResponse.response().getResult().getOutput().getText());
//        return advisedResponse;
    }
}
