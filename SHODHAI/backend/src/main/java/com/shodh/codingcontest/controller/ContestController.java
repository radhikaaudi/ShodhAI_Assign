package com.shodh.codingcontest.controller;

import com.shodh.codingcontest.dto.ContestResponse;
import com.shodh.codingcontest.dto.LeaderboardEntry;
import com.shodh.codingcontest.dto.SubmissionRequest;
import com.shodh.codingcontest.dto.SubmissionResponse;
import com.shodh.codingcontest.model.Contest;
import com.shodh.codingcontest.model.Submission;
import com.shodh.codingcontest.service.ContestService;
import com.shodh.codingcontest.service.SubmissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ContestController {
    
    @Autowired
    private ContestService contestService;
    
    @Autowired
    private SubmissionService submissionService;
    
    @GetMapping("/contests/{contestId}")
    public ResponseEntity<ContestResponse> getContest(@PathVariable Long contestId) {
        Optional<Contest> contest = contestService.getActiveContestById(contestId);
        
        if (contest.isPresent()) {
            ContestResponse response = contestService.convertToResponse(contest.get());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/submissions")
    public ResponseEntity<SubmissionResponse> submitCode(@Valid @RequestBody SubmissionRequest request) {
        try {
            Submission submission = submissionService.createSubmission(request);
            SubmissionResponse response = submissionService.convertToResponse(submission);
            
            // Process submission asynchronously
            submissionService.processSubmissionAsync(submission.getId());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/submissions/{submissionId}")
    public ResponseEntity<SubmissionResponse> getSubmission(@PathVariable Long submissionId) {
        Optional<Submission> submission = submissionService.getSubmissionById(submissionId);
        
        if (submission.isPresent()) {
            SubmissionResponse response = submissionService.convertToResponse(submission.get());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/contests/{contestId}/leaderboard")
    public ResponseEntity<List<LeaderboardEntry>> getLeaderboard(@PathVariable Long contestId) {
        List<LeaderboardEntry> leaderboard = contestService.getLeaderboard(contestId);
        return ResponseEntity.ok(leaderboard);
    }
    
    @GetMapping("/contests")
    public ResponseEntity<List<ContestResponse>> getAllContests() {
        List<Contest> contests = contestService.getAllActiveContests();
        List<ContestResponse> responses = contests.stream()
                .map(contestService::convertToResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }
}

