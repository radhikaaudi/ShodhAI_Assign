package com.shodh.codingcontest.dto;

import java.util.List;

public class ProblemResponse {
    
    private Long id;
    private String title;
    private String description;
    private String inputFormat;
    private String outputFormat;
    private String sampleInput;
    private String sampleOutput;
    private String constraints;
    private Integer timeLimitSeconds;
    private Integer memoryLimitMB;
    private List<TestCaseResponse> testCases;
    
    // Constructors
    public ProblemResponse() {}
    
    public ProblemResponse(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getInputFormat() {
        return inputFormat;
    }
    
    public void setInputFormat(String inputFormat) {
        this.inputFormat = inputFormat;
    }
    
    public String getOutputFormat() {
        return outputFormat;
    }
    
    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }
    
    public String getSampleInput() {
        return sampleInput;
    }
    
    public void setSampleInput(String sampleInput) {
        this.sampleInput = sampleInput;
    }
    
    public String getSampleOutput() {
        return sampleOutput;
    }
    
    public void setSampleOutput(String sampleOutput) {
        this.sampleOutput = sampleOutput;
    }
    
    public String getConstraints() {
        return constraints;
    }
    
    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }
    
    public Integer getTimeLimitSeconds() {
        return timeLimitSeconds;
    }
    
    public void setTimeLimitSeconds(Integer timeLimitSeconds) {
        this.timeLimitSeconds = timeLimitSeconds;
    }
    
    public Integer getMemoryLimitMB() {
        return memoryLimitMB;
    }
    
    public void setMemoryLimitMB(Integer memoryLimitMB) {
        this.memoryLimitMB = memoryLimitMB;
    }
    
    public List<TestCaseResponse> getTestCases() {
        return testCases;
    }
    
    public void setTestCases(List<TestCaseResponse> testCases) {
        this.testCases = testCases;
    }
}

