package com.shodh.codingcontest.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ContestResponse {
    
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isActive;
    private List<ProblemResponse> problems;
    
    // Constructors
    public ContestResponse() {}
    
    public ContestResponse(Long id, String name, String description, LocalDateTime startTime, LocalDateTime endTime, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActive = isActive;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public List<ProblemResponse> getProblems() {
        return problems;
    }
    
    public void setProblems(List<ProblemResponse> problems) {
        this.problems = problems;
    }
}

