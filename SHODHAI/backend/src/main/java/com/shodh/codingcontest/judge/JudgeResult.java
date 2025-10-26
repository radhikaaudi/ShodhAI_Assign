package com.shodh.codingcontest.judge;

import com.shodh.codingcontest.model.SubmissionStatus;

public class JudgeResult {
    
    private SubmissionStatus status;
    private String result;
    private Integer executionTimeMs;
    private Integer memoryUsedKB;
    private String errorMessage;
    
    public JudgeResult(SubmissionStatus status, String result, Integer executionTimeMs, Integer memoryUsedKB, String errorMessage) {
        this.status = status;
        this.result = result;
        this.executionTimeMs = executionTimeMs;
        this.memoryUsedKB = memoryUsedKB;
        this.errorMessage = errorMessage;
    }
    
    // Getters and Setters
    public SubmissionStatus getStatus() {
        return status;
    }
    
    public void setStatus(SubmissionStatus status) {
        this.status = status;
    }
    
    public String getResult() {
        return result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
    public Integer getExecutionTimeMs() {
        return executionTimeMs;
    }
    
    public void setExecutionTimeMs(Integer executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }
    
    public Integer getMemoryUsedKB() {
        return memoryUsedKB;
    }
    
    public void setMemoryUsedKB(Integer memoryUsedKB) {
        this.memoryUsedKB = memoryUsedKB;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

