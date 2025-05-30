package com.study.assistant.StudyPal.Controller;

import com.study.assistant.StudyPal.AI.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/mcq")
public class MCQController {

    @Autowired
    OpenAIService aiService;

    @PostMapping("/generate")
    public String generateMcq(@RequestParam String topic,@RequestParam String level,@RequestParam int count) throws IOException {
        return aiService.generateMCQs(topic, level, count);
    }
}
