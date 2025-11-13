package com.olp.repository;

import com.olp.model.user.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Task 6.1: InstructorRepository 接口
 * Repository 接口用于 Instructor 实体的数据访问
 */
@Repository
public interface InstructorRepository extends JpaRepository<Instructor, String> {
    
    /**
     * 通过邮箱查找教师
     * @param email 教师邮箱
     * @return Optional<Instructor>
     */
    Optional<Instructor> findByEmail(String email);
}

