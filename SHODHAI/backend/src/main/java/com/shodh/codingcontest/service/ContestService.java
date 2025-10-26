package com.shodh.codingcontest.service;

import com.shodh.codingcontest.dto.ContestResponse;
import com.shodh.codingcontest.dto.LeaderboardEntry;
import com.shodh.codingcontest.dto.ProblemResponse;
import com.shodh.codingcontest.dto.TestCaseResponse;
import com.shodh.codingcontest.model.Contest;
import com.shodh.codingcontest.model.Problem;
import com.shodh.codingcontest.model.Submission;
import com.shodh.codingcontest.model.TestCase;
import com.shodh.codingcontest.repository.ContestRepository;
import com.shodh.codingcontest.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContestService {
    
    @Autowired
    private ContestRepository contestRepository;
    
    @Autowired
    private SubmissionRepository submissionRepository;
    
    public Optional<Contest> getActiveContestById(Long contestId) {
        Optional<Contest> contest = contestRepository.findActiveContestById(contestId);
        if (contest.isPresent()) {
            // Force load test cases for all problems
            contest.get().getProblems().forEach(problem -> problem.getTestCases().size());
        }
        return contest;
    }
    
    public List<Contest> getAllActiveContests() {
        return contestRepository.findActiveContests();
    }
    
    public ContestResponse convertToResponse(Contest contest) {
        ContestResponse response = new ContestResponse(
            contest.getId(),
            contest.getName(),
            contest.getDescription(),
            contest.getStartTime(),
            contest.getEndTime(),
            contest.getIsActive()
        );
        
        List<ProblemResponse> problemResponses = contest.getProblems().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        response.setProblems(problemResponses);
        
        return response;
    }
    
    public ProblemResponse convertToResponse(Problem problem) {
        ProblemResponse response = new ProblemResponse(
            problem.getId(),
            problem.getTitle(),
            problem.getDescription()
        );
        
        response.setInputFormat(problem.getInputFormat());
        response.setOutputFormat(problem.getOutputFormat());
        response.setSampleInput(problem.getSampleInput());
        response.setSampleOutput(problem.getSampleOutput());
        response.setConstraints(problem.getConstraints());
        response.setTimeLimitSeconds(problem.getTimeLimitSeconds());
        response.setMemoryLimitMB(problem.getMemoryLimitMB());
        
        List<TestCaseResponse> testCaseResponses = problem.getTestCases().stream()
                .filter(testCase -> !testCase.getIsHidden()) // Only show non-hidden test cases
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        response.setTestCases(testCaseResponses);
        
        return response;
    }
    
    public TestCaseResponse convertToResponse(TestCase testCase) {
        return new TestCaseResponse(
            testCase.getId(),
            testCase.getInput(),
            testCase.getExpectedOutput(),
            testCase.getIsHidden()
        );
    }
    
    public List<LeaderboardEntry> getLeaderboard(Long contestId) {
        List<String> usernames = submissionRepository.findDistinctUsernamesByContestId(contestId);
        
        return usernames.stream()
                .map(username -> {
                    List<Submission> userSubmissions = submissionRepository.findUserSubmissionsInContest(contestId, username);
                    List<Submission> acceptedSubmissions = userSubmissions.stream()
                            .filter(submission -> submission.getStatus().toString().equals("ACCEPTED"))
                            .collect(Collectors.toList());
                    
                    int totalAccepted = (int) acceptedSubmissions.stream()
                            .map(Submission::getProblem)
                            .map(Problem::getId)
                            .distinct()
                            .count();
                    
                    int totalSubmissions = userSubmissions.size();
                    
                    return new LeaderboardEntry(
                        username,
                        totalAccepted,
                        totalSubmissions,
                        acceptedSubmissions.stream()
                                .map(Submission::getSubmissionTime)
                                .max(java.time.LocalDateTime::compareTo)
                                .orElse(null)
                    );
                })
                .sorted((a, b) -> {
                    // Sort by total accepted (descending), then by last accepted time (ascending)
                    int scoreComparison = Integer.compare(b.getTotalAccepted(), a.getTotalAccepted());
                    if (scoreComparison != 0) {
                        return scoreComparison;
                    }
                    
                    if (a.getLastAcceptedTime() == null && b.getLastAcceptedTime() == null) {
                        return 0;
                    }
                    if (a.getLastAcceptedTime() == null) {
                        return 1;
                    }
                    if (b.getLastAcceptedTime() == null) {
                        return -1;
                    }
                    
                    return a.getLastAcceptedTime().compareTo(b.getLastAcceptedTime());
                })
                .collect(Collectors.toList());
    }
}

