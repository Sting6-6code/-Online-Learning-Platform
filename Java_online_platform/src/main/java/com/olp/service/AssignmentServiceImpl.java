package com.olp.service;

import com.olp.model.assignment.Assignment;
import com.olp.model.assignment.Submission;
import com.olp.repository.AssignmentRepository;
import com.olp.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Task 6.6: AssignmentServiceImpl 实现类
 * 实现作业和提交服务的业务逻辑
 */
@Service
@Transactional
public class AssignmentServiceImpl implements AssignmentService {
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    @Autowired
    private SubmissionRepository submissionRepository;
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Assignment> findAssignmentById(String id) {
        return assignmentRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Assignment> findAssignmentsByCourse(String courseId) {
        return assignmentRepository.findByCourseId(courseId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Submission> findSubmissionById(String id) {
        return submissionRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Submission> findSubmissionsByAssignment(String assignmentId) {
        return submissionRepository.findByAssignmentId(assignmentId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Submission> findSubmissionsByStudent(String studentId) {
        return submissionRepository.findByStudentId(studentId);
    }
    
    @Override
    public Submission submitAssignment(String submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found: " + submissionId));
        
        boolean submitted = submission.submit();
        if (!submitted) {
            throw new IllegalStateException("Failed to submit assignment. Check status and deadline.");
        }
        
        return submissionRepository.save(submission);
    }
    
    @Override
    public Submission startAutoChecks(String submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found: " + submissionId));
        
        boolean started = submission.startAutoChecks();
        if (!started) {
            throw new IllegalStateException("Failed to start auto checks. Submission must be in Submitted status.");
        }
        
        return submissionRepository.save(submission);
    }
    
    @Override
    public Submission checksPass(String submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found: " + submissionId));
        
        boolean passed = submission.checksPass();
        if (!passed) {
            throw new IllegalStateException("Failed to mark checks as passed. Submission must be under check.");
        }
        
        return submissionRepository.save(submission);
    }
    
    @Override
    public Submission checksFail(String submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found: " + submissionId));
        
        boolean failed = submission.checksFail();
        if (!failed) {
            throw new IllegalStateException("Failed to mark checks as failed. Submission must be under check.");
        }
        
        return submissionRepository.save(submission);
    }
    
    @Override
    public Submission startGrading(String submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found: " + submissionId));
        
        boolean started = submission.startGrading();
        if (!started) {
            throw new IllegalStateException("Failed to start grading. Checks must pass first.");
        }
        
        return submissionRepository.save(submission);
    }
    
    @Override
    public Submission gradeSubmission(String submissionId, double score, String feedback) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found: " + submissionId));
        
        boolean graded = submission.grade(score, feedback);
        if (!graded) {
            throw new IllegalStateException("Failed to grade submission. Check score range and status.");
        }
        
        return submissionRepository.save(submission);
    }
    
    @Override
    public Submission requestResubmission(String submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found: " + submissionId));
        
        boolean requested = submission.requestResubmission();
        if (!requested) {
            throw new IllegalStateException("Failed to request resubmission. Submission must be graded or returned.");
        }
        
        return submissionRepository.save(submission);
    }
}

