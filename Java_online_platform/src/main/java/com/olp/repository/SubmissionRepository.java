package com.olp.repository;

import com.olp.model.assignment.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Task 6.6: SubmissionRepository 接口
 * Repository 接口用于 Submission 实体的数据访问
 */
@Repository
public interface SubmissionRepository extends JpaRepository<Submission, String> {
    
    /**
     * 通过作业 ID 查找提交
     * @param assignmentId 作业 ID
     * @return List<Submission>
     */
    @Query("SELECT s FROM Submission s WHERE s.assignment.id = :assignmentId")
    List<Submission> findByAssignmentId(@Param("assignmentId") String assignmentId);
    
    /**
     * 通过学生 ID 查找提交
     * @param studentId 学生 ID
     * @return List<Submission>
     */
    @Query("SELECT s FROM Submission s WHERE s.student.id = :studentId")
    List<Submission> findByStudentId(@Param("studentId") String studentId);
    
    /**
     * 通过学生 ID 和作业 ID 查找提交
     * @param studentId 学生 ID
     * @param assignmentId 作业 ID
     * @return List<Submission>
     */
    @Query("SELECT s FROM Submission s WHERE s.student.id = :studentId AND s.assignment.id = :assignmentId")
    List<Submission> findByStudentIdAndAssignmentId(@Param("studentId") String studentId, @Param("assignmentId") String assignmentId);
}

