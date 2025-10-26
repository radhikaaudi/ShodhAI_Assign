'use client';

import { Problem } from '@/types';

interface ProblemViewProps {
  problem: Problem;
}

export default function ProblemView({ problem }: ProblemViewProps) {
  return (
    <div className="p-6">
      <div className="mb-6">
        <h2 className="text-2xl font-bold text-gray-900 mb-2">{problem.title}</h2>
        <div className="flex space-x-4 text-sm text-gray-600">
          <span>Time Limit: {problem.timeLimitSeconds}s</span>
          <span>Memory Limit: {problem.memoryLimitMB}MB</span>
        </div>
      </div>

      <div className="space-y-6">
        {/* Description */}
        <div>
          <h3 className="text-lg font-semibold text-gray-900 mb-3">Description</h3>
          <div className="prose max-w-none">
            <p className="text-gray-700 whitespace-pre-wrap">{problem.description}</p>
          </div>
        </div>

        {/* Input Format */}
        {problem.inputFormat && (
          <div>
            <h3 className="text-lg font-semibold text-gray-900 mb-3">Input Format</h3>
            <div className="bg-gray-50 p-4 rounded-lg">
              <p className="text-gray-700 whitespace-pre-wrap">{problem.inputFormat}</p>
            </div>
          </div>
        )}

        {/* Output Format */}
        {problem.outputFormat && (
          <div>
            <h3 className="text-lg font-semibold text-gray-900 mb-3">Output Format</h3>
            <div className="bg-gray-50 p-4 rounded-lg">
              <p className="text-gray-700 whitespace-pre-wrap">{problem.outputFormat}</p>
            </div>
          </div>
        )}

        {/* Sample Input/Output */}
        {problem.sampleInput && problem.sampleOutput && (
          <div>
            <h3 className="text-lg font-semibold text-gray-900 mb-3">Sample Input/Output</h3>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <h4 className="font-medium text-gray-900 mb-2">Sample Input</h4>
                <div className="bg-gray-900 text-green-400 p-4 rounded-lg font-mono text-sm">
                  <pre>{problem.sampleInput}</pre>
                </div>
              </div>
              <div>
                <h4 className="font-medium text-gray-900 mb-2">Sample Output</h4>
                <div className="bg-gray-900 text-green-400 p-4 rounded-lg font-mono text-sm">
                  <pre>{problem.sampleOutput}</pre>
                </div>
              </div>
            </div>
          </div>
        )}

        {/* Constraints */}
        {problem.constraints && (
          <div>
            <h3 className="text-lg font-semibold text-gray-900 mb-3">Constraints</h3>
            <div className="bg-yellow-50 border border-yellow-200 p-4 rounded-lg">
              <p className="text-gray-700 whitespace-pre-wrap">{problem.constraints}</p>
            </div>
          </div>
        )}

        {/* Test Cases */}
        {problem.testCases && problem.testCases.length > 0 && (
          <div>
            <h3 className="text-lg font-semibold text-gray-900 mb-3">Test Cases</h3>
            <div className="space-y-4">
              {problem.testCases.map((testCase, index) => (
                <div key={testCase.id} className="border border-gray-200 rounded-lg p-4">
                  <div className="flex justify-between items-center mb-2">
                    <h4 className="font-medium text-gray-900">Test Case {index + 1}</h4>
                    {testCase.isHidden && (
                      <span className="text-xs bg-red-100 text-red-800 px-2 py-1 rounded">
                        Hidden
                      </span>
                    )}
                  </div>
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                      <h5 className="text-sm font-medium text-gray-700 mb-1">Input</h5>
                      <div className="bg-gray-50 p-3 rounded font-mono text-sm">
                        <pre>{testCase.input}</pre>
                      </div>
                    </div>
                    <div>
                      <h5 className="text-sm font-medium text-gray-700 mb-1">Expected Output</h5>
                      <div className="bg-gray-50 p-3 rounded font-mono text-sm">
                        <pre>{testCase.expectedOutput}</pre>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
