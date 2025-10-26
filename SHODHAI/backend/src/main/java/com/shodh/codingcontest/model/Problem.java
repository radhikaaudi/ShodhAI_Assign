package com.shodh.codingcontest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "problems")
public class Problem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Problem title is required")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "Problem description is required")
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(columnDefinition = "TEXT")
    private String inputFormat;
    
    @Column(columnDefinition = "TEXT")
    private String outputFormat;
    
    @Column(columnDefinition = "TEXT")
    private String sampleInput;
    
    @Column(columnDefinition = "TEXT")
    private String sampleOutput;
    
    @Column(columnDefinition = "TEXT")
    private String constraints;
    
    @NotNull(message = "Time limit is required")
    @Column(nullable = false)
    private Integer timeLimitSeconds = 2;
    
    @NotNull(message = "Memory limit is required")
    @Column(nullable = false)
    private Integer memoryLimitMB = 256;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id", nullable = false)
    private Contest contest;
    
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TestCase> testCases = new ArrayList<>();
    
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Submission> submissions = new ArrayList<>();
    
    // Constructors
    public Problem() {}
    
    public Problem(String title, String description, Contest contest) {
        this.title = title;
        this.description = description;
        this.contest = contest;
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
    
    public Contest getContest() {
        return contest;
    }
    
    public void setContest(Contest contest) {
        this.contest = contest;
    }
    
    public List<TestCase> getTestCases() {
        return testCases;
    }
    
    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
    }
    
    public List<Submission> getSubmissions() {
        return submissions;
    }
    
    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }
    
    // Helper methods
    public void addTestCase(TestCase testCase) {
        testCases.add(testCase);
        testCase.setProblem(this);
    }
    
    public void addSubmission(Submission submission) {
        submissions.add(submission);
        submission.setProblem(this);
    }
}

