package com.olp.repository;

import com.olp.model.assignment.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Task 6.6: AssignmentRepository 接口
 * Repository 接口用于 Assignment 实体的数据访问
 */
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, String> {
    
    /**
     * 通过课程 ID 查找作业
     * @param courseId 课程 ID
     * @return List<Assignment>
     */
    @Query("SELECT a FROM Assignment a WHERE a.course.id = :courseId")
    List<Assignment> findByCourseId(@Param("courseId") String courseId);
}

