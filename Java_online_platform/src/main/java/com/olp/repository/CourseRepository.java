package com.olp.repository;

import com.olp.model.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Task 6.4: CourseRepository 接口
 * Repository 接口用于 Course 实体的数据访问
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    
    /**
     * 通过教师 ID 查找课程
     * @param instructorId 教师 ID
     * @return List<Course>
     */
    @Query("SELECT c FROM Course c WHERE c.instructor.id = :instructorId")
    List<Course> findByInstructorId(@Param("instructorId") String instructorId);
    
    /**
     * 通过课程状态查找课程
     * @param status 课程状态
     * @return List<Course>
     */
    List<Course> findByStatus(Course.Status status);
    
    /**
     * 查找标题包含关键字的课程
     * @param keyword 关键字
     * @return List<Course>
     */
    @Query("SELECT c FROM Course c WHERE c.title LIKE %:keyword%")
    List<Course> findByTitleContaining(@Param("keyword") String keyword);
}

