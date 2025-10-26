package com.shodh.codingcontest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "test_cases")
public class TestCase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Test case input is required")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String input;
    
    @NotBlank(message = "Test case expected output is required")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String expectedOutput;
    
    @Column(nullable = false)
    private Boolean isHidden = true;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
    
    // Constructors
    public TestCase() {}
    
    public TestCase(String input, String expectedOutput, Problem problem) {
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.problem = problem;
    }
    
    public TestCase(String input, String expectedOutput, Boolean isHidden, Problem problem) {
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.isHidden = isHidden;
        this.problem = problem;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getInput() {
        return input;
    }
    
    public void setInput(String input) {
        this.input = input;
    }
    
    public String getExpectedOutput() {
        return expectedOutput;
    }
    
    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }
    
    public Boolean getIsHidden() {
        return isHidden;
    }
    
    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }
    
    public Problem getProblem() {
        return problem;
    }
    
    public void setProblem(Problem problem) {
        this.problem = problem;
    }
}

