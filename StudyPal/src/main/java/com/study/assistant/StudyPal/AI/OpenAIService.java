package com.study.assistant.StudyPal.AI;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OpenAIService {
    @Value("${openai.api.key}")
    private String apiKey;
    private final OkHttpClient client = new OkHttpClient();

    public String generateSummary(String topic, String level) throws Exception {
        String prompt = String.format("Summarize the topic \"%s\" for %s-level students in <200 words.", topic, level);
        return callOpenAI(prompt);
    }

    public String generateMCQs(String topic, String level, int count) throws IOException {
        String prompt = String.format("Generate %d multiple-choice questions (MCQs) on the topic \"%s\" for %s-level students. Include 4 options for each question and clearly mention the correct answer.", count, topic, level);
        return callOpenAI(prompt);
    }

    public String generateStudyPlan(String exam, int hoursPerDay, String[] topics, int days) throws Exception {
        StringBuilder topicList = new StringBuilder();
        for (String t : topics) {
            topicList.append("- ").append(t).append("\n");
        }

        String prompt = String.format("Create a %d-day daily study plan for the %s exam. The student can study %d hours per day. Focus on the following topics:\n%s", days, exam, hoursPerDay, topicList);
        return callOpenAI(prompt);
    }

    public String evaluateQuiz(String[] questions, String[] userAnswers, String[] correctAnswers) throws Exception {
        StringBuilder input = new StringBuilder("Evaluate the following quiz:\n");
        for (int i = 0; i < questions.length; i++) {
            input.append(String.format("Q%d: %s\nYour Answer: %s\nCorrect Answer: %s\n", i + 1, questions[i], userAnswers[i], correctAnswers[i]));
        }
        input.append("\nProvide total score, percentage, and one-line feedback on performance.");
        return callOpenAI(input.toString());
    }


    private String callOpenAI(String prompt) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        String requestBody = "{\n" +
                "  \"model\": \"deepseek/deepseek-r1:free\",\n" +
                "  \"messages\": [\n" +
                "    {\"role\": \"user\", \"content\": \"" + prompt + "\"}\n" +
                "  ]\n" +
                "}";

        Request request = new Request.Builder()
                .url("https://openrouter.ai/api/v1/chat/completions")
                .post(RequestBody.create(requestBody, mediaType))
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new RuntimeException("API call failed");
            String raw = response.body().string();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(raw);
            return root.path("choices").get(0).path("message").path("content").asText().trim();
        }
    }
}
