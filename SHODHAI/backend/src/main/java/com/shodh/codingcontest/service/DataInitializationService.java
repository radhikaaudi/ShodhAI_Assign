package com.shodh.codingcontest.service;

import com.shodh.codingcontest.model.*;
import com.shodh.codingcontest.repository.ContestRepository;
import com.shodh.codingcontest.repository.ProblemRepository;
import com.shodh.codingcontest.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class DataInitializationService implements CommandLineRunner {
    
    @Autowired
    private ContestRepository contestRepository;
    
    @Autowired
    private ProblemRepository problemRepository;
    
    @Autowired
    private TestCaseRepository testCaseRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Only initialize if no contests exist
        if (contestRepository.count() == 0) {
            initializeSampleData();
        }
    }
    
    private void initializeSampleData() {
        // Create sample contest
        Contest contest = new Contest(
            "Shodh AI Coding Challenge",
            "Welcome to the Shodh AI Coding Challenge! Solve these problems to test your programming skills.",
            LocalDateTime.now().minusHours(1),
            LocalDateTime.now().plusHours(23)
        );
        contest = contestRepository.save(contest);
        
        // Problem 1: Sum of Two Numbers
        Problem problem1 = new Problem(
            "Sum of Two Numbers",
            "Write a program that takes two integers as input and outputs their sum.",
            contest
        );
        problem1.setInputFormat("Two integers separated by a space");
        problem1.setOutputFormat("A single integer representing the sum");
        problem1.setSampleInput("5 3");
        problem1.setSampleOutput("8");
        problem1.setConstraints("Both numbers will be between -1000 and 1000");
        problem1.setTimeLimitSeconds(2);
        problem1.setMemoryLimitMB(256);
        problem1 = problemRepository.save(problem1);
        
        // Test cases for Problem 1
        TestCase testCase1_1 = new TestCase("5 3", "8", false, problem1);
        TestCase testCase1_2 = new TestCase("10 20", "30", true, problem1);
        TestCase testCase1_3 = new TestCase("-5 3", "-2", true, problem1);
        TestCase testCase1_4 = new TestCase("0 0", "0", true, problem1);
        
        testCaseRepository.saveAll(Arrays.asList(testCase1_1, testCase1_2, testCase1_3, testCase1_4));
        
        // Problem 2: Factorial
        Problem problem2 = new Problem(
            "Factorial",
            "Write a program that calculates the factorial of a given number. Read input using Scanner and print ONLY the result (no extra text). Example: For input '5', output should be '120' and nothing else.",
            contest
        );
        problem2.setInputFormat("A single positive integer n (read the number directly, no prompts)");
        problem2.setOutputFormat("The factorial of n (print ONLY the number, no extra text)");
        problem2.setSampleInput("5");
        problem2.setSampleOutput("120");
        problem2.setConstraints("1 ≤ n ≤ 10");
        problem2.setTimeLimitSeconds(2);
        problem2.setMemoryLimitMB(256);
        problem2 = problemRepository.save(problem2);
        
        // Test cases for Problem 2
        TestCase testCase2_1 = new TestCase("5", "120", false, problem2);
        TestCase testCase2_2 = new TestCase("3", "6", true, problem2);
        TestCase testCase2_3 = new TestCase("1", "1", true, problem2);
        TestCase testCase2_4 = new TestCase("7", "5040", true, problem2);
        
        testCaseRepository.saveAll(Arrays.asList(testCase2_1, testCase2_2, testCase2_3, testCase2_4));
        
        // Problem 3: Fibonacci Sequence
        Problem problem3 = new Problem(
            "Fibonacci Sequence",
            "Write a program that prints the nth Fibonacci number.",
            contest
        );
        problem3.setInputFormat("A single positive integer n");
        problem3.setOutputFormat("The nth Fibonacci number");
        problem3.setSampleInput("6");
        problem3.setSampleOutput("8");
        problem3.setConstraints("1 ≤ n ≤ 20");
        problem3.setTimeLimitSeconds(2);
        problem3.setMemoryLimitMB(256);
        problem3 = problemRepository.save(problem3);
        
        // Test cases for Problem 3
        TestCase testCase3_1 = new TestCase("6", "8", false, problem3);
        TestCase testCase3_2 = new TestCase("1", "1", true, problem3);
        TestCase testCase3_3 = new TestCase("2", "1", true, problem3);
        TestCase testCase3_4 = new TestCase("10", "55", true, problem3);
        
        testCaseRepository.saveAll(Arrays.asList(testCase3_1, testCase3_2, testCase3_3, testCase3_4));
        
        System.out.println("Sample data initialized successfully!");
        System.out.println("Contest ID: " + contest.getId());
        System.out.println("Problems: " + problemRepository.count());
        System.out.println("Test Cases: " + testCaseRepository.count());
    }
}

