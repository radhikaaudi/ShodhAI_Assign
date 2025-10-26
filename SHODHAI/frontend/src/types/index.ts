export interface Contest {
  id: number;
  name: string;
  description: string;
  startTime: string;
  endTime: string;
  isActive: boolean;
  problems: Problem[];
}

export interface Problem {
  id: number;
  title: string;
  description: string;
  inputFormat?: string;
  outputFormat?: string;
  sampleInput?: string;
  sampleOutput?: string;
  constraints?: string;
  timeLimitSeconds: number;
  memoryLimitMB: number;
  testCases: TestCase[];
}

export interface TestCase {
  id: number;
  input: string;
  expectedOutput: string;
  isHidden: boolean;
}

export interface SubmissionRequest {
  contestId: number;
  problemId: number;
  username: string;
  code: string;
  language: string;
}

export interface SubmissionResponse {
  submissionId: number;
  username: string;
  status: SubmissionStatus;
  result?: string;
  executionTimeMs?: number;
  memoryUsedKB?: number;
  errorMessage?: string;
  submissionTime: string;
  completedTime?: string;
}

export type SubmissionStatus = 
  | 'PENDING'
  | 'RUNNING'
  | 'ACCEPTED'
  | 'WRONG_ANSWER'
  | 'TIME_LIMIT_EXCEEDED'
  | 'MEMORY_LIMIT_EXCEEDED'
  | 'RUNTIME_ERROR'
  | 'COMPILATION_ERROR';

export interface LeaderboardEntry {
  username: string;
  totalAccepted: number;
  totalSubmissions: number;
  lastAcceptedTime?: string;
  totalScore: number;
}
