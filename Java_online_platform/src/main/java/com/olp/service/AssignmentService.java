package com.olp.service;

import com.olp.model.assignment.Assignment;
import com.olp.model.assignment.Submission;
import com.olp.model.assignment.Grade;

import java.util.List;
import java.util.Optional;

/**
 * Task 6.6: AssignmentService 接口
 * 定义作业和提交服务的业务逻辑接口
 */
public interface AssignmentService {
    
    /**
     * 通过 ID 查找作业
     * @param id 作业 ID
     * @return Optional<Assignment>
     */
    Optional<Assignment> findAssignmentById(String id);
    
    /**
     * 通过课程 ID 查找作业
     * @param courseId 课程 ID
     * @return List<Assignment>
     */
    List<Assignment> findAssignmentsByCourse(String courseId);
    
    /**
     * 通过 ID 查找提交
     * @param id 提交 ID
     * @return Optional<Submission>
     */
    Optional<Submission> findSubmissionById(String id);
    
    /**
     * 通过作业 ID 查找提交
     * @param assignmentId 作业 ID
     * @return List<Submission>
     */
    List<Submission> findSubmissionsByAssignment(String assignmentId);
    
    /**
     * 通过学生 ID 查找提交
     * @param studentId 学生 ID
     * @return List<Submission>
     */
    List<Submission> findSubmissionsByStudent(String studentId);
    
    /**
     * 提交作业
     * @param submissionId 提交 ID
     * @return Submission
     */
    Submission submitAssignment(String submissionId);
    
    /**
     * 开始自动检查
     * @param submissionId 提交 ID
     * @return Submission
     */
    Submission startAutoChecks(String submissionId);
    
    /**
     * 标记检查通过
     * @param submissionId 提交 ID
     * @return Submission
     */
    Submission checksPass(String submissionId);
    
    /**
     * 标记检查失败
     * @param submissionId 提交 ID
     * @return Submission
     */
    Submission checksFail(String submissionId);
    
    /**
     * 开始评分
     * @param submissionId 提交 ID
     * @return Submission
     */
    Submission startGrading(String submissionId);
    
    /**
     * 评分
     * @param submissionId 提交 ID
     * @param score 分数
     * @param feedback 反馈
     * @return Submission
     */
    Submission gradeSubmission(String submissionId, double score, String feedback);
    
    /**
     * 要求重交
     * @param submissionId 提交 ID
     * @return Submission
     */
    Submission requestResubmission(String submissionId);
}

