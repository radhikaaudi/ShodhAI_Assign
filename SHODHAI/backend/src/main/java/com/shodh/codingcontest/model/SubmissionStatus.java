package com.shodh.codingcontest.model;

public enum SubmissionStatus {
    PENDING("Pending"),
    RUNNING("Running"),
    ACCEPTED("Accepted"),
    WRONG_ANSWER("Wrong Answer"),
    TIME_LIMIT_EXCEEDED("Time Limit Exceeded"),
    MEMORY_LIMIT_EXCEEDED("Memory Limit Exceeded"),
    RUNTIME_ERROR("Runtime Error"),
    COMPILATION_ERROR("Compilation Error");
    
    private final String displayName;
    
    SubmissionStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}

