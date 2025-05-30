package com.study.assistant.StudyPal.Controller;

import com.study.assistant.StudyPal.AI.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private OpenAIService aiService;

    @PostMapping("/generate")
    public String generateNotes(@RequestParam String topic, @RequestParam String level) throws Exception {
        return aiService.generateSummary(topic, level);
    }
}
