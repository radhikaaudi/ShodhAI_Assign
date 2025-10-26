package com.shodh.codingcontest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
public class Submission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Username is required")
    @Column(nullable = false)
    private String username;
    
    @NotBlank(message = "Code is required")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String code;
    
    @NotBlank(message = "Language is required")
    @Column(nullable = false)
    private String language;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubmissionStatus status = SubmissionStatus.PENDING;
    
    @Column(columnDefinition = "TEXT")
    private String result;
    
    @Column
    private Integer executionTimeMs;
    
    @Column
    private Integer memoryUsedKB;
    
    @Column
    private String errorMessage;
    
    @NotNull(message = "Submission time is required")
    @Column(nullable = false)
    private LocalDateTime submissionTime = LocalDateTime.now();
    
    @Column
    private LocalDateTime completedTime;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id", nullable = false)
    private Contest contest;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
    
    // Constructors
    public Submission() {}
    
    public Submission(String username, String code, String language, Contest contest, Problem problem) {
        this.username = username;
        this.code = code;
        this.language = language;
        this.contest = contest;
        this.problem = problem;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public Contest getContest() {
        return contest;
    }
    
    public void setContest(Contest contest) {
        this.contest = contest;
    }
    
    public Problem getProblem() {
        return problem;
    }
    
    public void setProblem(Problem problem) {
        this.problem = problem;
    }
    
    // Helper methods
    public boolean isCompleted() {
        return status == SubmissionStatus.ACCEPTED || 
               status == SubmissionStatus.WRONG_ANSWER || 
               status == SubmissionStatus.TIME_LIMIT_EXCEEDED || 
               status == SubmissionStatus.MEMORY_LIMIT_EXCEEDED || 
               status == SubmissionStatus.RUNTIME_ERROR || 
               status == SubmissionStatus.COMPILATION_ERROR;
    }
    
    public void markCompleted() {
        this.completedTime = LocalDateTime.now();
    }
}

