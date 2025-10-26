'use client';

import { useState, useEffect } from 'react';
import { useParams } from 'next/navigation';
import { Contest, Problem, SubmissionRequest } from '@/types';
import { apiService } from '@/lib/api';
import CodeEditor from '@/components/CodeEditor';
import ProblemView from '@/components/ProblemView';
import Leaderboard from '@/components/Leaderboard';
import SubmissionStatus from '@/components/SubmissionStatus';

export default function ContestPage() {
  const params = useParams();
  const contestId = parseInt(params.contestId as string);
  
  const [contest, setContest] = useState<Contest | null>(null);
  const [selectedProblem, setSelectedProblem] = useState<Problem | null>(null);
  const [problemCode, setProblemCode] = useState<Map<number, string>>(new Map());
  const [problemSubmissionId, setProblemSubmissionId] = useState<Map<number, number>>(new Map());
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [submissionError, setSubmissionError] = useState<string | null>(null);
  const [language, setLanguage] = useState<string>('java');
  
  const defaultCode = 'import java.util.Scanner;\n\npublic class Solution {\n    public static void main(String[] args) {\n        // TODO: Write your code here\n        \n    }\n}';
  
  // Get code for current problem
  const code = selectedProblem ? (problemCode.get(selectedProblem.id) || defaultCode) : defaultCode;

  useEffect(() => {
    const fetchContest = async () => {
      try {
        const contestData = await apiService.getContest(contestId);
        setContest(contestData);
        if (contestData.problems.length > 0) {
          setSelectedProblem(contestData.problems[0]);
        }
      } catch (err) {
        setError(err instanceof Error ? err.message : 'Failed to fetch contest');
      } finally {
        setLoading(false);
      }
    };

    fetchContest();
  }, [contestId]);

  const handleCodeChange = (newCode: string) => {
    if (selectedProblem) {
      setProblemCode(prev => new Map(prev).set(selectedProblem.id, newCode));
    }
  };
  
  const handleProblemChange = (problem: Problem) => {
    setSelectedProblem(problem);
  };

  const handleSubmit = async () => {
    if (!selectedProblem || !code.trim()) {
      setSubmissionError('Please select a problem and write some code');
      return;
    }

    const username = localStorage.getItem('username');
    if (!username) {
      setSubmissionError('Username not found. Please join the contest again.');
      return;
    }

    const submission: SubmissionRequest = {
      contestId,
      problemId: selectedProblem.id,
      username,
      code,
      language
    };

    try {
      setSubmissionError(null);
      const response = await apiService.submitCode(submission);
      setProblemSubmissionId(prev => new Map(prev).set(selectedProblem.id, response.submissionId));
    } catch (err) {
      setSubmissionError(err instanceof Error ? err.message : 'Failed to submit code');
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto mb-4"></div>
          <p className="text-gray-600">Loading contest...</p>
        </div>
      </div>
    );
  }

  if (error || !contest) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="text-center">
          <p className="text-red-600 mb-4">{error || 'Contest not found'}</p>
          <button
            onClick={() => window.location.href = '/'}
            className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700"
          >
            Back to Home
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow-sm border-b">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center py-4">
            <div>
              <h1 className="text-2xl font-bold text-gray-900">{contest.name}</h1>
              <p className="text-gray-600">{contest.description}</p>
            </div>
            <div className="text-right">
              <p className="text-sm text-gray-500">Contest ID: {contest.id}</p>
              <p className="text-sm text-gray-500">User: {localStorage.getItem('username')}</p>
            </div>
          </div>
        </div>
      </header>

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Left Column - Problems */}
          <div className="lg:col-span-1">
            <div className="bg-white rounded-lg shadow-sm border">
              <div className="p-4 border-b">
                <h2 className="text-lg font-semibold text-gray-900">Problems</h2>
              </div>
              <div className="p-4">
                <div className="space-y-2">
                  {contest.problems.map((problem) => (
                    <button
                      key={problem.id}
                      onClick={() => handleProblemChange(problem)}
                      className={`w-full text-left p-3 rounded-lg border transition-colors ${
                        selectedProblem?.id === problem.id
                          ? 'bg-blue-50 border-blue-200 text-blue-900'
                          : 'bg-gray-50 border-gray-200 text-gray-700 hover:bg-gray-100'
                      }`}
                    >
                      <div className="font-medium">{problem.title}</div>
                      <div className="text-sm text-gray-500">
                        Time Limit: {problem.timeLimitSeconds}s | Memory: {problem.memoryLimitMB}MB
                      </div>
                    </button>
                  ))}
                </div>
              </div>
            </div>

            {/* Leaderboard */}
            <div className="mt-6">
              <Leaderboard contestId={contestId} />
            </div>
          </div>

          {/* Right Column - Problem View and Code Editor */}
          <div className="lg:col-span-2">
            {selectedProblem ? (
              <>
                {/* Problem View */}
                <div className="bg-white rounded-lg shadow-sm border mb-6">
                  <ProblemView problem={selectedProblem} />
                </div>

                {/* Code Editor */}
                <div className="bg-white rounded-lg shadow-sm border">
                  <div className="p-4 border-b">
                    <h3 className="text-lg font-semibold text-gray-900">Code Editor</h3>
                  </div>
                  <div className="p-4">
                    {/* Language Selection */}
                    <div className="mb-4">
                      <label htmlFor="language" className="block text-sm font-medium text-gray-700 mb-2">
                        Programming Language:
                      </label>
                      <select
                        id="language"
                        value={language}
                        onChange={(e) => setLanguage(e.target.value)}
                        className="block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500 sm:text-sm px-3 py-2 border"
                      >
                        <option value="java">Java</option>
                        <option value="cpp" disabled>C++ (Coming Soon)</option>
                        <option value="python" disabled>Python (Coming Soon)</option>
                      </select>
                    </div>

                    {/* Error Display */}
                    {submissionError && (
                      <div className="mb-4 p-3 bg-red-50 border border-red-200 rounded-lg">
                        <p className="text-sm text-red-600">{submissionError}</p>
                      </div>
                    )}

                    <CodeEditor
                      value={code}
                      onChange={handleCodeChange}
                      language={language}
                    />
                    <div className="mt-4 flex justify-between items-center">
                      <div className="text-sm text-gray-500">
                        Language: {language.charAt(0).toUpperCase() + language.slice(1)}
                      </div>
                      <button
                        onClick={handleSubmit}
                        className="bg-green-600 text-white px-6 py-2 rounded-lg font-medium hover:bg-green-700 focus:ring-2 focus:ring-green-500 focus:ring-offset-2"
                      >
                        Submit Code
                      </button>
                    </div>
                  </div>
                </div>

                {/* Submission Status */}
                {selectedProblem && problemSubmissionId.get(selectedProblem.id) && (
                  <div className="mt-6">
                    <SubmissionStatus submissionId={problemSubmissionId.get(selectedProblem.id)!} />
                  </div>
                )}
              </>
            ) : (
              <div className="bg-white rounded-lg shadow-sm border p-8 text-center">
                <p className="text-gray-500">Select a problem to start coding</p>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
