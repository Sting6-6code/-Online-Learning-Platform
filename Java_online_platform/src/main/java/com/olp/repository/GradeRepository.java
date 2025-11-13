package com.olp.repository;

import com.olp.model.assignment.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Task 6.6: GradeRepository 接口
 * Repository 接口用于 Grade 实体的数据访问
 */
@Repository
public interface GradeRepository extends JpaRepository<Grade, String> {
    // Grade 通常通过 Submission 访问，因此不需要额外的查询方法
}

