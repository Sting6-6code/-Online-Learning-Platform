package com.olp.repository;

import com.olp.model.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Task 6.1: StudentRepository 接口
 * Repository 接口用于 Student 实体的数据访问
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    
    /**
     * 通过邮箱查找学生
     * @param email 学生邮箱
     * @return Optional<Student>
     */
    Optional<Student> findByEmail(String email);
}

