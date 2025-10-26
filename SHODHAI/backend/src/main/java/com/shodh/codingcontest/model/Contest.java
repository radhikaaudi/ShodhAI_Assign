package com.shodh.codingcontest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contests")
public class Contest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Contest name is required")
    @Column(nullable = false)
    private String name;
    
    @NotBlank(message = "Contest description is required")
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @NotNull(message = "Start time is required")
    @Column(nullable = false)
    private LocalDateTime startTime;
    
    @NotNull(message = "End time is required")
    @Column(nullable = false)
    private LocalDateTime endTime;
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Problem> problems = new ArrayList<>();
    
    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Submission> submissions = new ArrayList<>();
    
    // Constructors
    public Contest() {}
    
    public Contest(String name, String description, LocalDateTime startTime, LocalDateTime endTime) {
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
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
    
    public List<Problem> getProblems() {
        return problems;
    }
    
    public void setProblems(List<Problem> problems) {
        this.problems = problems;
    }
    
    public List<Submission> getSubmissions() {
        return submissions;
    }
    
    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }
    
    // Helper methods
    public boolean isContestActive() {
        LocalDateTime now = LocalDateTime.now();
        return isActive && now.isAfter(startTime) && now.isBefore(endTime);
    }
    
    public void addProblem(Problem problem) {
        problems.add(problem);
        problem.setContest(this);
    }
    
    public void addSubmission(Submission submission) {
        submissions.add(submission);
        submission.setContest(this);
    }
}

