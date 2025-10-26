package com.shodh.codingcontest.judge;

public class TestResult {
    
    private boolean passed;
    private long executionTimeMs;
    private int memoryUsedKB;
    private String errorMessage;
    
    public TestResult(boolean passed, long executionTimeMs, int memoryUsedKB, String errorMessage) {
        this.passed = passed;
        this.executionTimeMs = executionTimeMs;
        this.memoryUsedKB = memoryUsedKB;
        this.errorMessage = errorMessage;
    }
    
    // Getters and Setters
    public boolean isPassed() {
        return passed;
    }
    
    public void setPassed(boolean passed) {
        this.passed = passed;
    }
    
    public long getExecutionTimeMs() {
        return executionTimeMs;
    }
    
    public void setExecutionTimeMs(long executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }
    
    public int getMemoryUsedKB() {
        return memoryUsedKB;
    }
    
    public void setMemoryUsedKB(int memoryUsedKB) {
        this.memoryUsedKB = memoryUsedKB;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

