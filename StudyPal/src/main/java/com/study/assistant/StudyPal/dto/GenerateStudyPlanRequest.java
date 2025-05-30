package com.study.assistant.StudyPal.dto;

import lombok.Data;
public class GenerateStudyPlanRequest {
    private String exam;
    private int hoursPerDay;
    private String[] topics;
    private int days;

    public String getExam() {
        return exam;
    }

    public int getHoursPerDay() {
        return hoursPerDay;
    }

    public String[] getTopics() {
        return topics;
    }

    public int getDays() {
        return days;
    }
}
