package com.shodh.codingcontest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SubmissionRequest {
    
    @NotNull(message = "Contest ID is required")
    private Long contestId;
    
    @NotNull(message = "Problem ID is required")
    private Long problemId;
    
    @NotBlank(message = "Username is required")
    private String username;
    
    @NotBlank(message = "Code is required")
    private String code;
    
    @NotBlank(message = "Language is required")
    private String language;
    
    // Constructors
    public SubmissionRequest() {}
    
    public SubmissionRequest(Long contestId, Long problemId, String username, String code, String language) {
        this.contestId = contestId;
        this.problemId = problemId;
        this.username = username;
        this.code = code;
        this.language = language;
    }
    
    // Getters and Setters
    public Long getContestId() {
        return contestId;
    }
    
    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }
    
    public Long getProblemId() {
        return problemId;
    }
    
    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
}

