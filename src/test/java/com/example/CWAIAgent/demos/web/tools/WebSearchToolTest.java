package com.example.CWAIAgent.demos.web.tools;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
class WebSearchToolTest {

    @Value("${web-search-api-key}")
    private String apiKey;

    @Test
    public void testSearchWeb() {
        WebSearchTool webSearchTool = new WebSearchTool(apiKey);
        String result = webSearchTool.searchWeb("帮我介绍一下周星驰");
        System.out.println(result);
        assertNotNull(result);
    }


}