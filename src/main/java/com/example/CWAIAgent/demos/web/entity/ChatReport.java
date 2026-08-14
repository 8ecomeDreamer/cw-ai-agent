package com.example.CWAIAgent.demos.web.entity;

import lombok.Data;

import java.util.List;

/**
 * 结构化输出类
 */
@Data
public class ChatReport {

    private String title;
    private List<String> suggestions;
}
