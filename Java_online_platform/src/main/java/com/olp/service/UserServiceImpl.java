package com.olp.service;

import com.olp.model.user.Administrator;
import com.olp.model.user.Instructor;
import com.olp.model.user.Student;
import com.olp.model.user.User;
import com.olp.repository.AdministratorRepository;
import com.olp.repository.InstructorRepository;
import com.olp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Task 6.2: UserServiceImpl 实现类
 * 实现用户服务的业务逻辑
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private InstructorRepository instructorRepository;
    
    @Autowired
    private AdministratorRepository administratorRepository;
    
    /**
     * 检查邮箱是否已存在于任何用户类型中
     */
    private boolean emailExists(String email) {
        return studentRepository.findByEmail(email).isPresent() ||
               instructorRepository.findByEmail(email).isPresent() ||
               administratorRepository.findByEmail(email).isPresent();
    }
    
    @Override
    public Student createStudent(String id, String name, String email) {
        // 验证邮箱是否已存在
        if (emailExists(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
        
        Student student = new Student(id, name, email);
        return studentRepository.save(student);
    }
    
    @Override
    public Instructor createInstructor(String id, String name, String email) {
        // 验证邮箱是否已存在
        if (emailExists(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
        
        Instructor instructor = new Instructor(id, name, email);
        return instructorRepository.save(instructor);
    }
    
    @Override
    public Administrator createAdministrator(String id, String name, String email) {
        // 验证邮箱是否已存在
        if (emailExists(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
        
        Administrator administrator = new Administrator(id, name, email);
        return administratorRepository.save(administrator);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserById(String id) {
        // 按顺序查找学生、教师、管理员
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return Optional.of(student.get());
        }
        
        Optional<Instructor> instructor = instructorRepository.findById(id);
        if (instructor.isPresent()) {
            return Optional.of(instructor.get());
        }
        
        Optional<Administrator> admin = administratorRepository.findById(id);
        return admin.map(administrator -> administrator);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) {
        // 按顺序查找学生、教师、管理员
        Optional<Student> student = studentRepository.findByEmail(email);
        if (student.isPresent()) {
            return Optional.of(student.get());
        }
        
        Optional<Instructor> instructor = instructorRepository.findByEmail(email);
        if (instructor.isPresent()) {
            return Optional.of(instructor.get());
        }
        
        Optional<Administrator> admin = administratorRepository.findByEmail(email);
        return admin.map(administrator -> administrator);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }
}

