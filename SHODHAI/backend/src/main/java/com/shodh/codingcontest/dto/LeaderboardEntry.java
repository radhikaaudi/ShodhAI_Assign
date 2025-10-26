package com.shodh.codingcontest.dto;

import java.time.LocalDateTime;

public class LeaderboardEntry {
    
    private String username;
    private Integer totalAccepted;
    private Integer totalSubmissions;
    private LocalDateTime lastAcceptedTime;
    private Integer totalScore;
    
    // Constructors
    public LeaderboardEntry() {}
    
    public LeaderboardEntry(String username, Integer totalAccepted, Integer totalSubmissions, LocalDateTime lastAcceptedTime) {
        this.username = username;
        this.totalAccepted = totalAccepted;
        this.totalSubmissions = totalSubmissions;
        this.lastAcceptedTime = lastAcceptedTime;
        this.totalScore = totalAccepted; // Simple scoring: number of accepted problems
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public Integer getTotalAccepted() {
        return totalAccepted;
    }
    
    public void setTotalAccepted(Integer totalAccepted) {
        this.totalAccepted = totalAccepted;
    }
    
    public Integer getTotalSubmissions() {
        return totalSubmissions;
    }
    
    public void setTotalSubmissions(Integer totalSubmissions) {
        this.totalSubmissions = totalSubmissions;
    }
    
    public LocalDateTime getLastAcceptedTime() {
        return lastAcceptedTime;
    }
    
    public void setLastAcceptedTime(LocalDateTime lastAcceptedTime) {
        this.lastAcceptedTime = lastAcceptedTime;
    }
    
    public Integer getTotalScore() {
        return totalScore;
    }
    
    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }
}

