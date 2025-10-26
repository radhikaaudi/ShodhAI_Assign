package com.shodh.codingcontest.judge;

import com.shodh.codingcontest.model.Submission;
import com.shodh.codingcontest.model.SubmissionStatus;
import com.shodh.codingcontest.model.TestCase;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CodeJudgeService {
    
    private static final String DOCKER_IMAGE = "java-judge:latest";
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir") + java.io.File.separator + "judge";
    
    public JudgeResult judgeSubmission(Submission submission) {
        List<TestCase> testCases = submission.getProblem().getTestCases();
        return judgeSubmission(submission, testCases);
    }
    
    public JudgeResult judgeSubmission(Submission submission, List<TestCase> testCases) {
        try {
            // Create temporary directory for this submission
            Path submissionDir = createSubmissionDirectory(submission);
            
            // Write the user's code to a file
            Path codeFile = writeCodeToFile(submissionDir, submission);
            
            // Run tests and collect results
            JudgeResult result = runTests(submissionDir, codeFile, testCases, submission);
            
            // Clean up temporary files
            cleanup(submissionDir);
            
            return result;
            
        } catch (Exception e) {
            return new JudgeResult(
                SubmissionStatus.RUNTIME_ERROR,
                "Judge error: " + e.getMessage(),
                0,
                0,
                e.getMessage()
            );
        }
    }
    
    private Path createSubmissionDirectory(Submission submission) throws IOException {
        Path submissionDir = Paths.get(TEMP_DIR, "submission_" + submission.getId());
        Files.createDirectories(submissionDir);
        return submissionDir;
    }
    
    private Path writeCodeToFile(Path submissionDir, Submission submission) throws IOException {
        String fileName = "Solution.java";
        Path codeFile = submissionDir.resolve(fileName);
        
        // Wrap user code in a proper Java class structure
        String wrappedCode = wrapJavaCode(submission.getCode());
        Files.write(codeFile, wrappedCode.getBytes());
        
        return codeFile;
    }
    
    private String wrapJavaCode(String userCode) {
        // Simple wrapper to ensure the code is in a proper class
        if (userCode.contains("public class Solution")) {
            return userCode;
        }
        
        return "public class Solution {\n" +
               "    public static void main(String[] args) {\n" +
               "        " + userCode.replace("\n", "\n        ") + "\n" +
               "    }\n" +
               "}";
    }
    
    private JudgeResult runTests(Path submissionDir, Path codeFile, List<TestCase> testCases, Submission submission) {
        try {
            // Convert Windows path to Unix-style path for Docker
            String dockerPath = convertToDockerPath(submissionDir.toString());
            
            // Compile the Java code using Docker
            Process compileProcess = new ProcessBuilder(
                "docker", "run", "--rm",
                "-v", dockerPath + ":/workspace",
                "-w", "/workspace",
                DOCKER_IMAGE,
                "javac", "Solution.java"
            ).start();
            
            int compileExitCode = compileProcess.waitFor();
            if (compileExitCode != 0) {
                String errorOutput = readProcessOutput(compileProcess.getErrorStream());
                return new JudgeResult(
                    SubmissionStatus.COMPILATION_ERROR,
                    "Compilation failed",
                    0,
                    0,
                    errorOutput
                );
            }
            
            // Run tests
            int totalTests = testCases.size();
            int passedTests = 0;
            int totalExecutionTime = 0;
            int maxMemoryUsed = 0;
            
            for (TestCase testCase : testCases) {
                TestResult testResult = runSingleTest(submissionDir, testCase, submission);
                totalExecutionTime += testResult.getExecutionTimeMs();
                maxMemoryUsed = Math.max(maxMemoryUsed, testResult.getMemoryUsedKB());
                
                if (testResult.isPassed()) {
                    passedTests++;
                } else {
                    // First failed test determines the result
                    return new JudgeResult(
                        SubmissionStatus.WRONG_ANSWER,
                        String.format("Test case failed: %d/%d passed", passedTests, totalTests),
                        totalExecutionTime,
                        maxMemoryUsed,
                        testResult.getErrorMessage()
                    );
                }
            }
            
            // All tests passed
            return new JudgeResult(
                SubmissionStatus.ACCEPTED,
                String.format("All test cases passed: %d/%d", passedTests, totalTests),
                totalExecutionTime,
                maxMemoryUsed,
                null
            );
            
        } catch (Exception e) {
            return new JudgeResult(
                SubmissionStatus.RUNTIME_ERROR,
                "Runtime error during execution",
                0,
                0,
                e.getMessage()
            );
        }
    }
    
    private TestResult runSingleTest(Path submissionDir, TestCase testCase, Submission submission) {
        try {
            long startTime = System.currentTimeMillis();
            
            // Convert Windows path to Unix-style path for Docker
            String dockerPath = convertToDockerPath(submissionDir.toString());
            
            // Run the Java code using Docker
            Process runProcess = new ProcessBuilder(
                "docker", "run", "--rm",
                "-v", dockerPath + ":/workspace",
                "-w", "/workspace",
                "--memory", submission.getProblem().getMemoryLimitMB() + "m",
                "--cpus", "1",
                DOCKER_IMAGE,
                "java", "Solution"
            ).start();
            
            // Write test input to the process
            try (OutputStreamWriter writer = new OutputStreamWriter(runProcess.getOutputStream())) {
                writer.write(testCase.getInput());
                writer.flush();
            }
            
            // Wait for process to complete with timeout
            boolean finished = runProcess.waitFor(submission.getProblem().getTimeLimitSeconds() + 5, TimeUnit.SECONDS);
            
            long executionTime = System.currentTimeMillis() - startTime;
            
            if (!finished) {
                runProcess.destroyForcibly();
                return new TestResult(false, executionTime, 0, "Time limit exceeded");
            }
            
            int exitCode = runProcess.exitValue();
            String output = readProcessOutput(runProcess.getInputStream());
            String errorOutput = readProcessOutput(runProcess.getErrorStream());
            
            if (exitCode != 0) {
                return new TestResult(false, executionTime, 0, "Runtime error: " + errorOutput);
            }
            
            // Compare output with expected output
            boolean passed = output.trim().equals(testCase.getExpectedOutput().trim());
            
            return new TestResult(passed, executionTime, 0, passed ? null : "Wrong answer");
            
        } catch (Exception e) {
            return new TestResult(false, 0, 0, "Test execution error: " + e.getMessage());
        }
    }
    
    private String readProcessOutput(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        return output.toString();
    }
    
    private String convertToDockerPath(String windowsPath) {
        // Convert Windows path to Unix-style path for Docker on Windows
        // On Windows with Docker Desktop, we need to convert paths
        // Example: C:\Users\kedia\Temp -> /c/Users/kedia/Temp
        String path = windowsPath.replace("\\", "/");
        
        // Handle drive letters - convert C: to /c
        for (char drive = 'A'; drive <= 'Z'; drive++) {
            String upper = drive + ":";
            String lower = "/" + Character.toLowerCase(drive);
            if (path.startsWith(upper)) {
                path = path.replace(upper, lower);
            }
        }
        
        return path;
    }
    
    private void cleanup(Path submissionDir) {
        try {
            Files.walk(submissionDir)
                    .sorted((a, b) -> b.compareTo(a)) // Delete files before directories
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            // Log error but continue cleanup
                            System.err.println("Failed to delete: " + path + " - " + e.getMessage());
                        }
                    });
        } catch (IOException e) {
            System.err.println("Cleanup failed: " + e.getMessage());
        }
    }
}

