package com.study.assistant.StudyPal.Controller;

import com.study.assistant.StudyPal.AI.OpenAIService;
import com.study.assistant.StudyPal.dto.GenerateStudyPlanRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/study/planner")
public class StudyPlanController {

    @Autowired
    OpenAIService aiService;

    @PostMapping("/generate")
    public String generateStudyPlan(@RequestBody GenerateStudyPlanRequest request) throws Exception {
        return aiService.generateStudyPlan(request.getExam(), request.getHoursPerDay(), request.getTopics(), request.getDays());
    }
}
