package com.example.CWAIAgent.demos.web.tools;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
@Slf4j
public class FileOperationToolTest {

    @Test
    public void testReadFile() {
        FileOperationTool tool = new FileOperationTool();
        String fileName = "测试.txt";
        String result = tool.readFile(fileName);
        log.info("result: {}", result);
        assertNotNull(result);
    }

    @Test
    public void testWriteFile() {
        FileOperationTool tool = new FileOperationTool();
        String fileName = "测试.txt";
        String content = "测试测试测试测试";
        String result = tool.writeFile(fileName, content);
        assertNotNull(result);
    }
}
