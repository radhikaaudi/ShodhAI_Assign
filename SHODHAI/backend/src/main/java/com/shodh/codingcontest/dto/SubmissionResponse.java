package com.shodh.codingcontest.dto;

import com.shodh.codingcontest.model.SubmissionStatus;
import java.time.LocalDateTime;

public class SubmissionResponse {
    
    private Long submissionId;
    private String username;
    private SubmissionStatus status;
    private String result;
    private Integer executionTimeMs;
    private Integer memoryUsedKB;
    private String errorMessage;
    private LocalDateTime submissionTime;
    private LocalDateTime completedTime;
    
    // Constructors
    public SubmissionResponse() {}
    
    public SubmissionResponse(Long submissionId, String username, SubmissionStatus status) {
        this.submissionId = submissionId;
        this.username = username;
        this.status = status;
    }
    
    // Getters and Setters
    public Long getSubmissionId() {
        return submissionId;
    }
    
    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
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
    
    public LocalDateTime getSubmissionTime() {
        return submissionTime;
    }
    
    public void setSubmissionTime(LocalDateTime submissionTime) {
        this.submissionTime = submissionTime;
    }
    
    public LocalDateTime getCompletedTime() {
        return completedTime;
    }
    
    public void setCompletedTime(LocalDateTime completedTime) {
        this.completedTime = completedTime;
    }
}

