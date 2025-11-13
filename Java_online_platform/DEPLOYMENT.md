# 部署指南 (Deployment Guide)

## 问题修复总结

本次修复解决了 Spring Boot 应用启动时的关键问题：

### 主要问题

1. **UserRepository 错误**: `User` 类使用 `@MappedSuperclass` 注解，不是 JPA 实体，无法直接创建 Repository
2. **缺少 JPA 无参构造函数**: 所有实体类缺少 JPA 要求的无参构造函数，导致运行时错误

### 解决方案

#### 1. 移除 UserRepository

- 删除 `UserRepository.java`
- 重构 `UserServiceImpl` 以使用具体的 Repository（`StudentRepository`、`InstructorRepository`、`AdministratorRepository`）
- 实现跨多个 Repository 的邮箱唯一性检查和用户查找功能

#### 2. 添加 JPA 无参构造函数

为所有 14 个 JPA 实体类添加了 `protected` 无参构造函数：

**用户模块 (User)**:

- `User` (抽象基类)
- `Student`
- `Instructor`
- `Administrator`

**课程模块 (Course)**:

- `Course`
- `Enrollment`
- `CourseCategory`
- `Lesson`
- `VideoContent`

**作业模块 (Assignment)**:

- `Assignment`
- `Submission`
- `Grade`

**支付模块 (Payment)**:

- `Subscription`
- `Payment`
- `Refund`

## 快速部署

### 前提条件

- Java 11 或更高版本
- Maven 3.6+
- 8GB+ RAM 推荐

### 构建项目

```bash
cd Java_online_platform
mvn clean package
```

### 运行应用

#### 开发环境 (使用 H2 内存数据库)

```bash
mvn spring-boot:run
```

#### 生产环境 (使用 MySQL)

```bash
# 1. 配置 MySQL 数据库
# 编辑 src/main/resources/application-prod.properties

# 2. 运行应用
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### 访问应用

- **应用 URL**: http://localhost:8080
- **H2 控制台**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:olpdb`
  - Username: `sa`
  - Password: (空)

## API 端点测试

### 用户管理

```bash
# 创建学生
curl -X POST http://localhost:8080/api/users/students \
  -H "Content-Type: application/json" \
  -d '{"id":"S001","name":"John Doe","email":"john@example.com"}'

# 创建教师
curl -X POST http://localhost:8080/api/users/instructors \
  -H "Content-Type: application/json" \
  -d '{"id":"I001","name":"Dr. Smith","email":"smith@example.com"}'

# 获取所有学生
curl http://localhost:8080/api/users/students

# 通过 ID 获取用户
curl http://localhost:8080/api/users/S001
```

### 课程管理

```bash
# 创建课程
curl -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{"id":"C001","title":"Spring Boot 基础","capacity":30,"instructorId":"I001"}'

# 发布课程
curl -X POST http://localhost:8080/api/courses/C001/publish

# 开放选课
curl -X POST http://localhost:8080/api/courses/C001/open-enrollment

# 学生选课
curl -X POST http://localhost:8080/api/courses/C001/enroll \
  -H "Content-Type: application/json" \
  -d '{"studentId":"S001"}'
```

### 作业管理

```bash
# 创建作业
curl -X POST http://localhost:8080/api/assignments \
  -H "Content-Type: application/json" \
  -d '{"id":"A001","title":"第一次作业","deadline":"2025-12-31","maxScore":100,"courseId":"C001"}'

# 提交作业
curl -X POST http://localhost:8080/api/submissions/SUB001/submit

# 作业评分
curl -X POST http://localhost:8080/api/submissions/SUB001/grade \
  -H "Content-Type: application/json" \
  -d '{"score":85,"feedback":"做得很好！"}'
```

## 测试

### 运行所有测试

```bash
mvn test
```

**测试结果**: ✅ 240 tests passed, 1 skipped

### 测试覆盖

- 模型类单元测试
- 状态机转换测试
- OCL 约束验证测试
- 业务逻辑测试

## 配置文件

### application.properties (默认/开发)

```properties
spring.profiles.active=dev
spring.datasource.url=jdbc:h2:mem:olpdb
spring.jpa.hibernate.ddl-auto=create-drop
```

### application-prod.properties (生产)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/olp
spring.datasource.username=olp_user
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

## 常见问题

### 端口已被占用

```bash
# 查找并终止占用 8080 端口的进程
lsof -ti:8080 | xargs kill -9
```

### 数据库连接失败

- 检查 MySQL 服务是否运行
- 验证数据库凭据
- 确认数据库已创建

### 内存不足

增加 JVM 堆大小：

```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xmx2048m"
```

## 性能优化建议

1. **使用连接池**: HikariCP (已配置)
2. **启用缓存**: Redis 或 Ehcache
3. **数据库索引**: 为常用查询字段添加索引
4. **分页查询**: 使用 Spring Data 分页功能
5. **异步处理**: 对于耗时操作使用 `@Async`

## 监控和日志

### 日志级别配置

```properties
logging.level.root=INFO
logging.level.com.olp=DEBUG
logging.file.name=logs/application.log
```

### 健康检查

```bash
# Spring Boot Actuator (需要添加依赖)
curl http://localhost:8080/actuator/health
```

## 下一步

完成的功能：

- ✅ 基础实体模型和关系
- ✅ 状态机实现
- ✅ OCL 约束验证
- ✅ Repository 层
- ✅ Service 层
- ✅ REST API 控制器
- ✅ 全局异常处理
- ✅ 240+ 单元测试

待实现功能：

- [ ] API 文档 (Swagger/OpenAPI)
- [ ] 数据库初始化脚本
- [ ] 集成测试套件
- [ ] 用户认证和授权 (Spring Security)
- [ ] 前端应用
- [ ] 文件上传功能
- [ ] 邮件通知服务
- [ ] 性能测试和优化

## 技术支持

如遇问题，请查看：

- 应用日志: `/tmp/spring-boot.log` 或控制台输出
- 测试报告: `target/surefire-reports/`
- README.md: 完整的项目文档

## 许可证

MIT License
