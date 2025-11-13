package com.olp.controller;

import com.olp.model.assignment.Assignment;
import com.olp.model.assignment.Submission;
import com.olp.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Task 6.7: AssignmentController REST API
 * 处理作业和提交相关的 HTTP 请求
 */
@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "*")
public class AssignmentController {
    
    @Autowired
    private AssignmentService assignmentService;
    
    /**
     * 获取作业详情
     * GET /api/assignments/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable String id) {
        return assignmentService.findAssignmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * 通过课程 ID 获取作业
     * GET /api/assignments/course/{courseId}
     */
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Assignment>> getAssignmentsByCourse(@PathVariable String courseId) {
        List<Assignment> assignments = assignmentService.findAssignmentsByCourse(courseId);
        return ResponseEntity.ok(assignments);
    }
    
    /**
     * 获取提交详情
     * GET /api/submissions/{id}
     */
    @GetMapping("/submissions/{id}")
    public ResponseEntity<Submission> getSubmissionById(@PathVariable String id) {
        return assignmentService.findSubmissionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * 通过作业 ID 获取提交
     * GET /api/assignments/{assignmentId}/submissions
     */
    @GetMapping("/{assignmentId}/submissions")
    public ResponseEntity<List<Submission>> getSubmissionsByAssignment(@PathVariable String assignmentId) {
        List<Submission> submissions = assignmentService.findSubmissionsByAssignment(assignmentId);
        return ResponseEntity.ok(submissions);
    }
    
    /**
     * 通过学生 ID 获取提交
     * GET /api/submissions/student/{studentId}
     */
    @GetMapping("/submissions/student/{studentId}")
    public ResponseEntity<List<Submission>> getSubmissionsByStudent(@PathVariable String studentId) {
        List<Submission> submissions = assignmentService.findSubmissionsByStudent(studentId);
        return ResponseEntity.ok(submissions);
    }
    
    /**
     * 提交作业
     * POST /api/submissions/{id}/submit
     */
    @PostMapping("/submissions/{id}/submit")
    public ResponseEntity<Submission> submitAssignment(@PathVariable String id) {
        try {
            Submission submission = assignmentService.submitAssignment(id);
            return ResponseEntity.ok(submission);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 开始自动检查
     * POST /api/submissions/{id}/start-checks
     */
    @PostMapping("/submissions/{id}/start-checks")
    public ResponseEntity<Submission> startAutoChecks(@PathVariable String id) {
        try {
            Submission submission = assignmentService.startAutoChecks(id);
            return ResponseEntity.ok(submission);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 标记检查通过
     * POST /api/submissions/{id}/checks-pass
     */
    @PostMapping("/submissions/{id}/checks-pass")
    public ResponseEntity<Submission> checksPass(@PathVariable String id) {
        try {
            Submission submission = assignmentService.checksPass(id);
            return ResponseEntity.ok(submission);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 标记检查失败
     * POST /api/submissions/{id}/checks-fail
     */
    @PostMapping("/submissions/{id}/checks-fail")
    public ResponseEntity<Submission> checksFail(@PathVariable String id) {
        try {
            Submission submission = assignmentService.checksFail(id);
            return ResponseEntity.ok(submission);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 开始评分
     * POST /api/submissions/{id}/start-grading
     */
    @PostMapping("/submissions/{id}/start-grading")
    public ResponseEntity<Submission> startGrading(@PathVariable String id) {
        try {
            Submission submission = assignmentService.startGrading(id);
            return ResponseEntity.ok(submission);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 评分
     * POST /api/submissions/{id}/grade
     * Body: {"score": 85.0, "feedback": "Good work!"}
     */
    @PostMapping("/submissions/{id}/grade")
    public ResponseEntity<Submission> gradeSubmission(@PathVariable String id, @RequestBody Map<String, Object> request) {
        try {
            double score = ((Number) request.get("score")).doubleValue();
            String feedback = (String) request.get("feedback");
            
            Submission submission = assignmentService.gradeSubmission(id, score, feedback);
            return ResponseEntity.ok(submission);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 要求重交
     * POST /api/submissions/{id}/request-resubmission
     */
    @PostMapping("/submissions/{id}/request-resubmission")
    public ResponseEntity<Submission> requestResubmission(@PathVariable String id) {
        try {
            Submission submission = assignmentService.requestResubmission(id);
            return ResponseEntity.ok(submission);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

