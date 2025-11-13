package com.olp.service;

import com.olp.model.user.User;
import com.olp.model.user.Student;
import com.olp.model.user.Instructor;
import com.olp.model.user.Administrator;

import java.util.List;
import java.util.Optional;

/**
 * Task 6.2: UserService 接口
 * 定义用户服务的业务逻辑接口
 */
public interface UserService {
    
    /**
     * 创建学生
     * @param id 学生 ID
     * @param name 姓名
     * @param email 邮箱
     * @return Student
     */
    Student createStudent(String id, String name, String email);
    
    /**
     * 创建教师
     * @param id 教师 ID
     * @param name 姓名
     * @param email 邮箱
     * @return Instructor
     */
    Instructor createInstructor(String id, String name, String email);
    
    /**
     * 创建管理员
     * @param id 管理员 ID
     * @param name 姓名
     * @param email 邮箱
     * @return Administrator
     */
    Administrator createAdministrator(String id, String name, String email);
    
    /**
     * 通过 ID 查找用户
     * @param id 用户 ID
     * @return Optional<User>
     */
    Optional<User> findUserById(String id);
    
    /**
     * 通过邮箱查找用户
     * @param email 邮箱
     * @return Optional<User>
     */
    Optional<User> findUserByEmail(String email);
    
    /**
     * 获取所有学生
     * @return List<Student>
     */
    List<Student> getAllStudents();
    
    /**
     * 获取所有教师
     * @return List<Instructor>
     */
    List<Instructor> getAllInstructors();
}

