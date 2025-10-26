package com.shodh.codingcontest.service;

import com.shodh.codingcontest.dto.SubmissionRequest;
import com.shodh.codingcontest.dto.SubmissionResponse;
import com.shodh.codingcontest.judge.CodeJudgeService;
import com.shodh.codingcontest.judge.JudgeResult;
import com.shodh.codingcontest.model.*;
import com.shodh.codingcontest.repository.ContestRepository;
import com.shodh.codingcontest.repository.ProblemRepository;
import com.shodh.codingcontest.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SubmissionService {
    
    @Autowired
    private SubmissionRepository submissionRepository;
    
    @Autowired
    private ContestRepository contestRepository;
    
    @Autowired
    private ProblemRepository problemRepository;
    
    @Autowired
    private CodeJudgeService codeJudgeService;
    
    public Submission createSubmission(SubmissionRequest request) {
        Contest contest = contestRepository.findById(request.getContestId())
                .orElseThrow(() -> new RuntimeException("Contest not found"));
        
        Problem problem = problemRepository.findById(request.getProblemId())
                .orElseThrow(() -> new RuntimeException("Problem not found"));
        
        Submission submission = new Submission(
            request.getUsername(),
            request.getCode(),
            request.getLanguage(),
            contest,
            problem
        );
        
        return submissionRepository.save(submission);
    }
    
    public Optional<Submission> getSubmissionById(Long submissionId) {
        return submissionRepository.findById(submissionId);
    }
    
    public SubmissionResponse convertToResponse(Submission submission) {
        SubmissionResponse response = new SubmissionResponse(
            submission.getId(),
            submission.getUsername(),
            submission.getStatus()
        );
        
        response.setResult(submission.getResult());
        response.setExecutionTimeMs(submission.getExecutionTimeMs());
        response.setMemoryUsedKB(submission.getMemoryUsedKB());
        response.setErrorMessage(submission.getErrorMessage());
        response.setSubmissionTime(submission.getSubmissionTime());
        response.setCompletedTime(submission.getCompletedTime());
        
        return response;
    }
    
    @Async
    public void processSubmissionAsync(Long submissionId) {
        // Fetch submission with proper eager loading to avoid session issues
        Submission submission = submissionRepository.findByIdWithProblemAndTestCases(submissionId).orElse(null);
        
        if (submission == null) {
            return;
        }
        
        try {
            // Update status to RUNNING
            submission.setStatus(SubmissionStatus.RUNNING);
            submissionRepository.save(submission);
            
            // Fetch test cases eagerly before async processing
            List<TestCase> testCases = submission.getProblem().getTestCases();
            
            // Process the submission using the code judge
            JudgeResult result = codeJudgeService.judgeSubmission(submission, testCases);
            
            // Update submission with results
            submission.setStatus(result.getStatus());
            submission.setResult(result.getResult());
            submission.setExecutionTimeMs(result.getExecutionTimeMs());
            submission.setMemoryUsedKB(result.getMemoryUsedKB());
            submission.setErrorMessage(result.getErrorMessage());
            submission.markCompleted();
            
            submissionRepository.save(submission);
            
        } catch (Exception e) {
            // Handle any errors during processing
            submission.setStatus(SubmissionStatus.RUNTIME_ERROR);
            submission.setErrorMessage("Internal error: " + e.getMessage());
            submission.markCompleted();
            submissionRepository.save(submission);
        }
    }
}

