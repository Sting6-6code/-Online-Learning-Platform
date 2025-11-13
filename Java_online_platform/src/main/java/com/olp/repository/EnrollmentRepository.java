package com.olp.repository;

import com.olp.model.course.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Task 6.4: EnrollmentRepository 接口
 * Repository 接口用于 Enrollment 实体的数据访问
 */
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, String> {
    
    /**
     * 通过学生 ID 查找选课记录
     * @param studentId 学生 ID
     * @return List<Enrollment>
     */
    @Query("SELECT e FROM Enrollment e WHERE e.student.id = :studentId")
    List<Enrollment> findByStudentId(@Param("studentId") String studentId);
    
    /**
     * 通过课程 ID 查找选课记录
     * @param courseId 课程 ID
     * @return List<Enrollment>
     */
    @Query("SELECT e FROM Enrollment e WHERE e.course.id = :courseId")
    List<Enrollment> findByCourseId(@Param("courseId") String courseId);
    
    /**
     * 通过学生 ID 和课程 ID 查找选课记录
     * @param studentId 学生 ID
     * @param courseId 课程 ID
     * @return List<Enrollment>
     */
    @Query("SELECT e FROM Enrollment e WHERE e.student.id = :studentId AND e.course.id = :courseId")
    List<Enrollment> findByStudentIdAndCourseId(@Param("studentId") String studentId, @Param("courseId") String courseId);
}

