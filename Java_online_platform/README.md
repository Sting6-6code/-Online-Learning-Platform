# Online Learning Platform - Spring Boot Backend

基于 Spring Boot 的在线学习平台后端 API 系统，提供完整的课程管理、作业提交、成绩评定和订阅支付功能。

## 🚀 技术栈

- **框架**: Spring Boot 2.7.18
- **语言**: Java 11
- **构建工具**: Maven 3.6+
- **数据库**: H2 (开发) / MySQL (生产)
- **ORM**: Spring Data JPA / Hibernate
- **测试**: JUnit 5, Spring Boot Test
- **架构模式**: 三层架构 (Controller → Service → Repository)

## ✨ 核心特性

- ✅ **完整的用户管理系统**: 支持学生、教师、管理员三种角色
- ✅ **课程生命周期管理**: 从草稿、发布、选课到开课、结课的完整流程
- ✅ **作业提交工作流**: 包含自动检查、评分、重交等完整状态机
- ✅ **订阅支付系统**: 支持试用、月付、年付和退款功能
- ✅ **OCL 约束验证**: 12+ 业务规则自动验证
- ✅ **RESTful API**: 40+ 标准化 REST 端点
- ✅ **统一异常处理**: 全局异常捕获和标准错误响应
- ✅ **测试覆盖**: 240+ 单元测试，测试覆盖率高

## 📁 项目结构

```
Java_online_platform/
├── src/main/java/com/olp/
│   ├── OnlineLearningPlatformApplication.java  # Spring Boot 主类
│   ├── model/                                  # 领域模型层 (15个实体类)
│   │   ├── user/                               # User, Student, Instructor, Administrator
│   │   ├── course/                             # Course, Enrollment, Lesson, VideoContent, CourseCategory
│   │   ├── assignment/                         # Assignment, Submission, Grade
│   │   └── payment/                            # Payment, Subscription, Refund
│   ├── repository/                             # 数据访问层 (10个Repository)
│   │   ├── UserRepository, StudentRepository, InstructorRepository, AdministratorRepository
│   │   ├── CourseRepository, EnrollmentRepository
│   │   ├── AssignmentRepository, SubmissionRepository, GradeRepository
│   │   └── PaymentRepository, SubscriptionRepository, RefundRepository
│   ├── service/                                # 业务逻辑层 (8个Service)
│   │   ├── UserService & UserServiceImpl
│   │   ├── CourseService & CourseServiceImpl
│   │   ├── AssignmentService & AssignmentServiceImpl
│   │   └── PaymentService & PaymentServiceImpl
│   ├── controller/                             # REST API 层 (5个Controller)
│   │   ├── UserController                      # 用户管理 API
│   │   ├── CourseController                    # 课程管理 API
│   │   ├── AssignmentController                # 作业管理 API
│   │   ├── PaymentController                   # 支付管理 API
│   │   └── GlobalExceptionHandler              # 全局异常处理
│   ├── config/                                 # 配置类
│   │   └── CorsConfig                          # CORS 跨域配置
│   └── util/                                   # 工具类
│       └── Utils                               # 时间、ID生成等工具方法
├── src/main/resources/
│   ├── application.properties                  # 主配置文件
│   ├── application-dev.properties              # 开发环境配置 (H2)
│   └── application-prod.properties             # 生产环境配置 (MySQL)
├── src/test/java/com/olp/                      # 测试代码 (240+测试用例)
│   ├── model/                                  # 模型单元测试
│   └── util/                                   # 工具类测试
├── pom.xml                                     # Maven 配置
├── tasks.md                                    # 任务清单 (63个已完成任务)
└── README.md                                   # 本文件
```

## 🛠️ 快速开始

### 前置要求

- JDK 11+
- Maven 3.6+

### 运行项目

```bash
# 克隆项目
cd Java_online_platform

# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run

# 或者打包后运行
mvn clean package
java -jar target/online-learning-platform-1.0.0.jar
```

### 访问应用

- **API 基础 URL**: http://localhost:8080
- **H2 控制台**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:olpdb`
  - Username: `sa`
  - Password: (空)

## 📝 API 文档

### 用户管理 API (`/api/users`)

| 方法 | 端点                        | 说明             |
| ---- | --------------------------- | ---------------- |
| GET  | `/api/users/{id}`           | 获取用户信息     |
| GET  | `/api/users/email/{email}`  | 通过邮箱查找用户 |
| GET  | `/api/users/students`       | 获取所有学生     |
| GET  | `/api/users/instructors`    | 获取所有教师     |
| POST | `/api/users/students`       | 创建学生账号     |
| POST | `/api/users/instructors`    | 创建教师账号     |
| POST | `/api/users/administrators` | 创建管理员账号   |

**示例 - 创建学生**:

```bash
curl -X POST http://localhost:8080/api/users/students \
  -H "Content-Type: application/json" \
  -d '{
    "id": "S001",
    "name": "张三",
    "email": "zhangsan@example.com"
  }'
```

### 课程管理 API (`/api/courses`)

| 方法 | 端点                                     | 说明           |
| ---- | ---------------------------------------- | -------------- |
| GET  | `/api/courses`                           | 获取所有课程   |
| GET  | `/api/courses/{id}`                      | 获取课程详情   |
| GET  | `/api/courses/instructor/{instructorId}` | 获取教师的课程 |
| GET  | `/api/courses/status/{status}`           | 按状态筛选课程 |
| POST | `/api/courses`                           | 创建新课程     |
| POST | `/api/courses/{id}/publish`              | 发布课程       |
| POST | `/api/courses/{id}/open-enrollment`      | 开放选课       |
| POST | `/api/courses/{id}/enroll`               | 学生选课       |
| POST | `/api/courses/{id}/start`                | 开始课程       |
| POST | `/api/courses/{id}/complete`             | 完成课程       |
| POST | `/api/courses/{id}/cancel`               | 取消课程       |

**课程状态流转**:

```
Draft → Published → EnrollmentOpen → InProgress → Completed
                         ↓
                    Waitlisted → InProgress
```

**示例 - 创建课程**:

```bash
curl -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{
    "id": "C001",
    "title": "Java 高级编程",
    "capacity": 50,
    "instructorId": "I001"
  }'
```

### 作业管理 API (`/api/assignments`)

| 方法 | 端点                                          | 说明               |
| ---- | --------------------------------------------- | ------------------ |
| GET  | `/api/assignments/{id}`                       | 获取作业详情       |
| GET  | `/api/assignments/course/{courseId}`          | 获取课程的所有作业 |
| GET  | `/api/submissions/{id}`                       | 获取提交详情       |
| GET  | `/api/assignments/{assignmentId}/submissions` | 获取作业的所有提交 |
| GET  | `/api/submissions/student/{studentId}`        | 获取学生的所有提交 |
| POST | `/api/submissions/{id}/submit`                | 提交作业           |
| POST | `/api/submissions/{id}/start-checks`          | 开始自动检查       |
| POST | `/api/submissions/{id}/checks-pass`           | 标记检查通过       |
| POST | `/api/submissions/{id}/checks-fail`           | 标记检查失败       |
| POST | `/api/submissions/{id}/start-grading`         | 开始评分           |
| POST | `/api/submissions/{id}/grade`                 | 评分               |
| POST | `/api/submissions/{id}/request-resubmission`  | 要求重交           |

**提交状态流转**:

```
Created → Submitted → UnderCheck → {Grading → Graded}
                           ↓        {Returned → ResubmissionRequested}
                      checksFail
```

**示例 - 评分**:

```bash
curl -X POST http://localhost:8080/api/submissions/SUB001/grade \
  -H "Content-Type: application/json" \
  -d '{
    "score": 85.0,
    "feedback": "做得不错，逻辑清晰！"
  }'
```

### 支付管理 API (`/api/payments`)

| 方法 | 端点                                                    | 说明               |
| ---- | ------------------------------------------------------- | ------------------ |
| GET  | `/api/payments/subscriptions`                           | 获取所有订阅       |
| GET  | `/api/payments/subscriptions/{id}`                      | 获取订阅详情       |
| GET  | `/api/payments/subscriptions/status/{status}`           | 按状态筛选订阅     |
| GET  | `/api/payments/{id}`                                    | 获取支付详情       |
| GET  | `/api/payments/subscriptions/{subscriptionId}/payments` | 获取订阅的所有支付 |
| POST | `/api/payments/{id}/succeed`                            | 标记支付成功       |
| POST | `/api/payments/{id}/fail`                               | 标记支付失败       |
| POST | `/api/payments/{id}/refund`                             | 发起退款           |
| POST | `/api/payments/subscriptions/{id}/charge-success`       | 订阅计费成功       |
| POST | `/api/payments/subscriptions/{id}/charge-fail`          | 订阅计费失败       |
| POST | `/api/payments/subscriptions/{id}/cancel`               | 取消订阅           |
| POST | `/api/payments/subscriptions/{id}/grace-expire`         | 宽限期到期         |

**订阅状态流转**:

```
Trial → Active → {Cancelled}
         ↓       {PastDue → Suspended}
    chargeSuccess
```

**示例 - 发起退款**:

```bash
curl -X POST http://localhost:8080/api/payments/PAY001/refund \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 50.0
  }'
```

### 错误响应格式

所有 API 错误都返回统一格式:

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Email already exists: test@example.com",
  "path": "/api/users/students",
  "timestamp": 1699876543210
}
```

**HTTP 状态码**:

- `200 OK`: 成功
- `201 Created`: 创建成功
- `400 Bad Request`: 参数错误
- `404 Not Found`: 资源不存在
- `409 Conflict`: 状态冲突
- `500 Internal Server Error`: 服务器错误

## 🧪 测试

### 运行测试

```bash
# 运行所有测试
mvn test

# 运行特定测试类
mvn test -Dtest=UserTest

# 运行特定测试方法
mvn test -Dtest=CourseTest#testPublishCourse

# 静默模式运行
mvn test -q
```

### 测试覆盖

- ✅ **240 个单元测试**全部通过
- ✅ **用户模块**: User, Student, Instructor, Administrator 测试
- ✅ **课程模块**: Course, Enrollment, Lesson, VideoContent 测试
- ✅ **作业模块**: Assignment, Submission, Grade, 完整工作流测试
- ✅ **支付模块**: Payment, Subscription, Refund, 计费工作流测试
- ✅ **工具类**: Utils 工具方法测试
- ✅ **OCL 约束**: 12+ 业务规则验证测试

## 🔧 配置

### 开发环境

使用 H2 内存数据库，无需额外配置：

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### 生产环境

需要配置 MySQL 数据库：

```properties
# application-prod.properties
spring.datasource.url=jdbc:mysql://localhost:3306/olp_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

运行：

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## 📚 开发指南

### 添加新的 API 端点

遵循三层架构模式添加新功能：

```java
// 1. 定义 Repository (数据访问层)
@Repository
public interface MyEntityRepository extends JpaRepository<MyEntity, String> {
    List<MyEntity> findByCustomField(String field);
}

// 2. 实现 Service (业务逻辑层)
@Service
@Transactional
public class MyServiceImpl implements MyService {
    @Autowired
    private MyEntityRepository repository;

    public MyEntity createEntity(String id, String field) {
        MyEntity entity = new MyEntity(id, field);
        return repository.save(entity);
    }
}

// 3. 创建 Controller (API 层)
@RestController
@RequestMapping("/api/myentities")
@CrossOrigin(origins = "*")
public class MyEntityController {
    @Autowired
    private MyService service;

    @PostMapping
    public ResponseEntity<MyEntity> create(@RequestBody Map<String, String> request) {
        try {
            MyEntity entity = service.createEntity(
                request.get("id"),
                request.get("field")
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(entity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
```

### 数据库模型

项目使用 JPA 注解定义实体关系：

```java
@Entity
@Table(name = "courses")
public class Course {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Enrollment> courseEnrollments = new ArrayList<>();
}
```

### 状态机实现

项目实现了三个核心状态机：

1. **Course Status**: Draft → Published → EnrollmentOpen → InProgress → Completed
2. **Submission Status**: Created → Submitted → UnderCheck → Grading → Graded
3. **Subscription Status**: Trial → Active → PastDue → Suspended

状态转换通过业务方法控制，包含守卫条件验证。

### 代码规范

- ✅ 遵循 Spring Boot 最佳实践
- ✅ 使用 RESTful API 设计原则
- ✅ 三层架构：Controller → Service → Repository
- ✅ 统一异常处理 (@ControllerAdvice)
- ✅ 事务管理 (@Transactional)
- ✅ 完整的 JavaDoc 注释
- ✅ 单元测试覆盖所有业务逻辑

### OCL 约束验证

项目实现了 12+ OCL (Object Constraint Language) 约束：

```java
// 示例：课程容量约束
public boolean validateSeatsNotExceeded() {
    int activeCount = 0;
    for (Enrollment enrollment : courseEnrollments) {
        if (enrollment.getStatus() == EnrollmentStatus.Active) {
            activeCount++;
        }
    }
    return activeCount <= capacity;
}
```

## 🎯 项目里程碑

### 已完成 (63/68 任务)

- ✅ **阶段 0**: Spring Boot 环境准备 (7/7)
- ✅ **阶段 1**: 用户模块基础实现 (4/4)
- ✅ **阶段 2**: 课程模块核心实现 (10/10)
- ✅ **阶段 3**: 作业系统实现 (10/10)
- ✅ **阶段 4**: 支付系统实现 (10/10)
- ✅ **阶段 5**: OCL 约束验证实现 (12/12)
- ✅ **阶段 6**: Spring Boot 服务层实现 (10/15)
  - ✅ Repository 层完成
  - ✅ Service 层完成
  - ✅ Controller 层完成
  - ✅ 全局异常处理完成

### 待完成 (可选增强)

- ⏳ **Swagger/OpenAPI** 文档生成
- ⏳ **数据初始化脚本**
- ⏳ **集成测试套件**
- ⏳ **性能测试**
- ⏳ **Docker 容器化**

### 下一步建议

1. **前端集成**: 开发 React/Vue 前端界面
2. **认证授权**: 集成 Spring Security + JWT
3. **文件上传**: 实现作业文件上传功能
4. **实时通知**: WebSocket 实时消息推送
5. **邮件服务**: 课程通知和成绩邮件
6. **数据分析**: 学习进度和成绩统计

## 📖 参考文档

- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [Spring Data JPA 指南](https://spring.io/projects/spring-data-jpa)
- [RESTful API 设计最佳实践](https://restfulapi.net/)
- [项目任务清单](tasks.md) - 详细的开发任务和测试标准

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

## 📄 许可证

Educational Use - 用于学习和教学目的

---

**开发团队**: Online Learning Platform Development Team  
**最后更新**: 2025-11-13  
**版本**: 1.0.0 (MVP)
