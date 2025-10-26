'use client';

import { SubmissionResponse, SubmissionStatus } from '@/types';
import { useSubmissionPolling } from '@/hooks/usePolling';

interface SubmissionStatusProps {
  submissionId: number;
}

export default function SubmissionStatus({ submissionId }: SubmissionStatusProps) {
  const { submission, loading, error } = useSubmissionPolling(submissionId);

  const getStatusColor = (status: SubmissionStatus) => {
    switch (status) {
      case 'ACCEPTED':
        return 'text-green-600 bg-green-50 border-green-200';
      case 'WRONG_ANSWER':
        return 'text-red-600 bg-red-50 border-red-200';
      case 'TIME_LIMIT_EXCEEDED':
        return 'text-yellow-600 bg-yellow-50 border-yellow-200';
      case 'MEMORY_LIMIT_EXCEEDED':
        return 'text-orange-600 bg-orange-50 border-orange-200';
      case 'RUNTIME_ERROR':
        return 'text-red-500 bg-red-50 border-red-200';
      case 'COMPILATION_ERROR':
        return 'text-purple-600 bg-purple-50 border-purple-200';
      case 'RUNNING':
        return 'text-blue-600 bg-blue-50 border-blue-200';
      case 'PENDING':
        return 'text-gray-600 bg-gray-50 border-gray-200';
      default:
        return 'text-gray-600 bg-gray-50 border-gray-200';
    }
  };

  const getStatusIcon = (status: SubmissionStatus) => {
    switch (status) {
      case 'ACCEPTED':
        return '✓';
      case 'WRONG_ANSWER':
        return '✗';
      case 'TIME_LIMIT_EXCEEDED':
        return '⏱';
      case 'MEMORY_LIMIT_EXCEEDED':
        return '💾';
      case 'RUNTIME_ERROR':
        return '⚠';
      case 'COMPILATION_ERROR':
        return '🔧';
      case 'RUNNING':
        return '⟳';
      case 'PENDING':
        return '⏳';
      default:
        return '?';
    }
  };

  if (loading && !submission) {
    return (
      <div className="bg-white rounded-lg shadow-sm border">
        <div className="p-4">
          <div className="animate-pulse">
            <div className="h-4 bg-gray-200 rounded w-1/4 mb-2"></div>
            <div className="h-3 bg-gray-200 rounded w-1/2"></div>
          </div>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="bg-white rounded-lg shadow-sm border">
        <div className="p-4">
          <p className="text-red-600 text-sm">{error}</p>
        </div>
      </div>
    );
  }

  if (!submission) {
    return null;
  }

  const isCompleted = submission.status !== 'PENDING' && submission.status !== 'RUNNING';

  return (
    <div className="bg-white rounded-lg shadow-sm border">
      <div className="p-4">
        <div className="flex items-center justify-between mb-3">
          <h3 className="text-lg font-semibold text-gray-900">Submission Status</h3>
          <div className={`px-3 py-1 rounded-full text-sm font-medium border ${getStatusColor(submission.status)}`}>
            <span className="mr-1">{getStatusIcon(submission.status)}</span>
            {submission.status.replace('_', ' ')}
          </div>
        </div>

        <div className="space-y-2 text-sm">
          <div className="flex justify-between">
            <span className="text-gray-600">Submission ID:</span>
            <span className="font-mono text-gray-900">#{submission.submissionId}</span>
          </div>
          
          <div className="flex justify-between">
            <span className="text-gray-600">Username:</span>
            <span className="text-gray-900">{submission.username}</span>
          </div>

          <div className="flex justify-between">
            <span className="text-gray-600">Submitted:</span>
            <span className="text-gray-900">
              {new Date(submission.submissionTime).toLocaleString()}
            </span>
          </div>

          {submission.executionTimeMs && (
            <div className="flex justify-between">
              <span className="text-gray-600">Execution Time:</span>
              <span className="text-gray-900">{submission.executionTimeMs}ms</span>
            </div>
          )}

          {submission.memoryUsedKB && (
            <div className="flex justify-between">
              <span className="text-gray-600">Memory Used:</span>
              <span className="text-gray-900">{submission.memoryUsedKB}KB</span>
            </div>
          )}

          {submission.completedTime && (
            <div className="flex justify-between">
              <span className="text-gray-600">Completed:</span>
              <span className="text-gray-900">
                {new Date(submission.completedTime).toLocaleString()}
              </span>
            </div>
          )}
        </div>

        {submission.result && (
          <div className="mt-4 p-3 bg-gray-50 rounded-lg">
            <h4 className="font-medium text-gray-900 mb-2">Result:</h4>
            <p className="text-sm text-gray-700">{submission.result}</p>
          </div>
        )}

        {submission.errorMessage && (
          <div className="mt-4 p-3 bg-red-50 border border-red-200 rounded-lg">
            <h4 className="font-medium text-red-900 mb-2">Error:</h4>
            <p className="text-sm text-red-700 font-mono">{submission.errorMessage}</p>
          </div>
        )}

        {!isCompleted && (
          <div className="mt-4 flex items-center text-blue-600">
            <div className="animate-spin rounded-full h-4 w-4 border-b-2 border-blue-600 mr-2"></div>
            <span className="text-sm">Processing submission...</span>
          </div>
        )}
      </div>
    </div>
  );
}
