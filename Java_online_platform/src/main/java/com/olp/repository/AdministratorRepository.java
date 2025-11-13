package com.olp.repository;

import com.olp.model.user.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Task 6.1: AdministratorRepository 接口
 * Repository 接口用于 Administrator 实体的数据访问
 */
@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, String> {
    
    /**
     * 通过邮箱查找管理员
     * @param email 管理员邮箱
     * @return Optional<Administrator>
     */
    Optional<Administrator> findByEmail(String email);
}

