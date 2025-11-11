# 在线学习平台（Online Learning Platform）Java 版架构设计文档

## 目录

1. [项目概述](#项目概述)
2. [文件与文件夹结构](#文件与文件夹结构)
3. [核心类与属性](#核心类与属性)
4. [关联关系与多重度](#关联关系与多重度)
5. [状态机设计](#状态机设计)
6. [状态存储位置](#状态存储位置)
7. [服务之间如何连接](#服务之间如何连接)
8. [OCL 约束规范](#ocl约束规范)
9. [方法签名与职责](#方法签名与职责)
10. [实现指南与扩展建议](#实现指南与扩展建议)

---

## 项目概述

本项目是一个完整的**在线学习平台系统**的 Java 实现版本，支持课程管理、作业提交、成绩评定、订阅付费等核心功能。系统采用面向对象设计，包含清晰的类层次结构、状态机模型和业务约束规则。

### 技术栈

- **语言**: Java 8+
- **建模工具**: UMLe (用于代码框架生成)
- **构建系统**: Maven / Gradle (建议)
- **设计方法**: UML 类图、状态图、OCL 约束
- **当前架构**: POJO 模型层（可扩展为 Spring Boot 微服务架构）

### 核心功能模块

- **用户管理** (`olp.user`): 学生、教师、管理员角色
- **课程管理** (`olp.course`): 课程、选课、课时、视频内容、分类
- **作业系统** (`olp.assignment`): 作业、提交、评分
- **支付系统** (`olp.payment`): 支付、订阅、退款

---

## 文件与文件夹结构

### 当前结构（Umple 生成）

```
Java_online_platform/
│
├── architecture.md              # 本架构文档
│
├── [用户管理模块 - olp.user]
│   ├── User.java                # 抽象用户基类
│   ├── Student.java             # 学生类
│   ├── Instructor.java          # 教师类
│   └── Administrator.java       # 管理员类
│
├── [课程管理模块 - olp.course]
│   ├── Course.java              # 课程类（含状态机）
│   ├── Enrollment.java          # 选课关联类
│   ├── Lesson.java              # 课时类
│   ├── VideoContent.java        # 视频内容类
│   └── CourseCategory.java      # 课程分类类
│
├── [作业系统模块 - olp.assignment]
│   ├── Assignment.java          # 作业类
│   ├── Submission.java          # 提交类（含状态机）
│   └── Grade.java               # 成绩类
│
└── [支付系统模块 - olp.payment]
    ├── Payment.java             # 支付类
    ├── Subscription.java         # 订阅类（含状态机）
    └── Refund.java               # 退款类
```

### 建议的扩展结构（Spring Boot 架构）

```
Java_online_platform/
│
├── src/main/java/com/olp/
│   │
│   ├── model/                   # 领域模型层（当前文件）
│   │   ├── user/
│   │   ├── course/
│   │   ├── assignment/
│   │   └── payment/
│   │
│   ├── repository/              # 数据访问层
│   │   ├── UserRepository.java
│   │   ├── CourseRepository.java
│   │   └── ...
│   │
│   ├── service/                 # 业务逻辑层
│   │   ├── UserService.java
│   │   ├── CourseService.java
│   │   └── ...
│   │
│   ├── controller/              # REST API 层
│   │   ├── UserController.java
│   │   ├── CourseController.java
│   │   └── ...
│   │
│   └── config/                  # 配置层
│       ├── DatabaseConfig.java
│       └── SecurityConfig.java
│
├── src/main/resources/
│   ├── application.properties
│   └── db/migration/           # Flyway/Liquibase 迁移脚本
│
└── src/test/java/              # 测试代码
    └── ...
```

### 文件职责说明

#### 用户管理模块 (olp.user)

| 文件                 | 职责                                              | 状态存储位置                                         |
| -------------------- | ------------------------------------------------- | ---------------------------------------------------- |
| `User.java`          | 定义抽象用户基类，包含 id、name、email 等通用属性 | 私有字段：`id`, `name`, `email`                      |
| `Student.java`       | 学生类，管理选课记录、作业提交                    | 关联集合：`studentEnrollments`, `studentSubmissions` |
| `Instructor.java`    | 教师类，管理所教授的课程列表                      | 关联集合：`taughtCourses`                            |
| `Administrator.java` | 管理员类，负责系统管理功能                        | 继承自 User，无额外状态                              |

#### 课程管理模块 (olp.course)

| 文件                  | 职责                                           | 状态存储位置                                                                                                           |
| --------------------- | ---------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------- |
| `Course.java`         | 核心课程实体，包含发布、选课、开课、结课状态机 | 私有字段：`status: Status` (状态机状态)<br>关联集合：`lessons`, `courseEnrollments`, `courseAssignments`, `categories` |
| `Enrollment.java`     | 关联类，连接学生和课程，记录选课状态和时间     | 私有字段：`status: EnrollmentStatus`<br>私有字段：`enrolledAt: Date`                                                   |
| `Lesson.java`         | 课时实体，属于某课程，按 orderIndex 排序       | 私有字段：`orderIndex: int`<br>关联：`course: Course`                                                                  |
| `VideoContent.java`   | 视频内容，关联到课时，记录 URL 和时长          | 私有字段：`url: String`, `durationSec: int`                                                                            |
| `CourseCategory.java` | 课程分类，支持多对多关系                       | 关联集合：`courses: List<Course>`                                                                                      |

#### 作业系统模块 (olp.assignment)

| 文件              | 职责                                   | 状态存储位置                                                                                |
| ----------------- | -------------------------------------- | ------------------------------------------------------------------------------------------- |
| `Assignment.java` | 作业实体，记录标题、截止时间、满分     | 私有字段：`deadline: Date`, `maxScore: int`                                                 |
| `Submission.java` | 提交实体，含完整的提交-检查-评分状态机 | 私有字段：`status: Status` (状态机状态)<br>私有字段：`version: int`, `checkPassed: boolean` |
| `Grade.java`      | 成绩实体，记录分数和反馈               | 私有字段：`score: double`, `feedback: String`                                               |

#### 支付系统模块 (olp.payment)

| 文件                | 职责                                     | 状态存储位置                                                                                                  |
| ------------------- | ---------------------------------------- | ------------------------------------------------------------------------------------------------------------- |
| `Payment.java`      | 支付记录，包含金额、状态、支付时间       | 私有字段：`status: PaymentStatus`<br>私有字段：`amount: double`, `paidAt: Date`                               |
| `Subscription.java` | 订阅管理，含试用、活跃、逾期、暂停状态机 | 私有字段：`status: Status` (状态机状态)<br>私有字段：`plan: PlanType`, `startAt: Date`, `nextBillingAt: Date` |
| `Refund.java`       | 退款记录，关联支付                       | 私有字段：`amount: double`, `requestedAt: Date`, `processedAt: Date`                                          |

---

## 核心类与属性

### 1. 用户管理包 (olp.user)

#### User (抽象基类)

```java
public abstract class User {
    // 状态存储：私有字段
    private String id;
    private String name;
    private String email;

    // 构造函数
    public User(String aId, String aName, String aEmail)

    // Getter/Setter 方法
    public String getId()
    public String getName()
    public String getEmail()
}
```

#### Student (继承 User)

```java
public class Student extends User {
    // 状态存储：关联集合（通过引用连接）
    private List<Enrollment> studentEnrollments;
    private List<Submission> studentSubmissions;

    // 构造函数
    public Student(String aId, String aName, String aEmail)

    // 关联访问方法
    public List<Enrollment> getStudentEnrollments()
    public List<Submission> getStudentSubmissions()
}
```

#### Instructor (继承 User)

```java
public class Instructor extends User {
    // 状态存储：关联集合
    private List<Course> taughtCourses;

    // 关联访问方法
    public List<Course> getTaughtCourses()
}
```

#### Administrator (继承 User)

```java
public class Administrator extends User {
    // 继承自 User，无额外状态
    // 未来可扩展系统管理方法
}
```

---

### 2. 课程管理包 (olp.course)

#### Course

```java
public class Course {
    // 基本属性（状态存储：私有字段）
    private String id;
    private String title;
    private int capacity;

    // 状态机状态（状态存储：私有枚举字段）
    public enum Status { Draft, Published, EnrollmentOpen, Waitlisted, InProgress, Completed, Cancelled }
    private Status status;

    // 关联对象（状态存储：关联集合和引用）
    private List<Lesson> lessons;                    // Composition
    private List<Enrollment> courseEnrollments;      // 通过关联类
    private List<Assignment> courseAssignments;
    private List<CourseCategory> categories;          // 多对多
    private Instructor instructor;                    // 1对1

    // 状态机方法
    public boolean publish()
    public boolean openEnrollment()
    public boolean startCourse()
    public boolean complete()
    public boolean cancel(String reason)

    // 守卫方法
    private boolean hasMinimumContent()
    private boolean hasCapacity()
    private boolean hasActiveEnrollments()
}
```

#### Enrollment (关联类)

```java
public class Enrollment {
    // 状态存储：私有字段
    private String id;
    private EnrollmentStatus status;
    private Date enrolledAt;

    // 关联引用（状态存储：对象引用）
    private Student student;
    private Course course;

    // 枚举类型
    public enum EnrollmentStatus { Active, Waitlisted, Dropped }
}
```

---

### 3. 作业系统包 (olp.assignment)

#### Assignment

```java
public class Assignment {
    // 状态存储：私有字段
    private String id;
    private String title;
    private Date deadline;
    private int maxScore;

    // 关联引用
    private Course course;                           // 1对1
    private List<Submission> assignmentSubmissions; // 1对多
}
```

#### Submission

```java
public class Submission {
    // 状态存储：私有字段
    private String id;
    private Date submittedAt;
    private int version;
    private boolean checkPassed;

    // 状态机状态（状态存储：私有枚举字段）
    public enum Status { Created, Submitted, UnderCheck, Grading, Graded, Returned, ResubmissionRequested }
    private Status status;

    // 关联引用
    private Grade submissionGrade;    // 0..1
    private Student student;          // 1
    private Assignment assignment;    // 1

    // 状态机方法
    public boolean submit()
    public boolean startAutoChecks()
    public boolean checksPass(boolean flagged, boolean compiledOk)
    public boolean checksFail()
    public boolean startGrading()
    public boolean grade(double score, String feedback)
    public boolean requestResubmission()
}
```

#### Grade

```java
public class Grade {
    // 状态存储：私有字段
    private String id;
    private double score;
    private String feedback;

    // 关联引用
    private Submission submission;    // 1
}
```

---

### 4. 支付系统包 (olp.payment)

#### Payment

```java
public class Payment {
    // 状态存储：私有字段
    private String id;
    private double amount;
    private PaymentStatus status;
    private Date paidAt;

    // 关联引用
    private Subscription subscription;    // 1
    private Refund paymentRefund;        // 0..1

    // 枚举类型
    public enum PaymentStatus { Pending, Succeeded, Failed, Refunding, Refunded }
}
```

#### Subscription

```java
public class Subscription {
    // 状态存储：私有字段
    private String id;
    private PlanType plan;
    private Date startAt;
    private Date nextBillingAt;

    // 状态机状态（状态存储：私有枚举字段）
    public enum Status { Trial, Active, PastDue, Suspended, Cancelled }
    private Status status;

    // 关联集合
    private List<Payment> subscriptionPayments;

    // 枚举类型
    public enum PlanType { Trial, Monthly, Annual }

    // 状态机方法
    public boolean chargeSuccess()
    public boolean chargeFail()
    public boolean cancel()
    public boolean graceExpire()
}
```

#### Refund

```java
public class Refund {
    // 状态存储：私有字段
    private String id;
    private double amount;
    private Date requestedAt;
    private Date processedAt;

    // 关联引用
    private Payment payment;    // 1
}
```

---

## 关联关系与多重度

### 用户与课程

```
Student (student) 1..* ────── *..1 (course) Course
        通过 Enrollment（关联类）
```

**连接方式**:

- `Student` 通过 `List<Enrollment> studentEnrollments` 存储关联
- `Course` 通过 `List<Enrollment> courseEnrollments` 存储关联
- `Enrollment` 类持有 `Student student` 和 `Course course` 的引用

### 课程与内容

```
Course (course) 1 ────── 1..* (lessons) Lesson (Composition)
Course (course) 1 ────── 1..* (assignments) Assignment
```

**连接方式**:

- `Course` 通过 `List<Lesson> lessons` 和 `List<Assignment> courseAssignments` 存储
- `Lesson` 和 `Assignment` 持有 `Course course` 的引用（反向关联）

### 作业与提交

```
Assignment (assignment) 1 ────── 1..* (submissions) Submission
Student (student) 1 ────── 1..* (submissions) Submission
Submission (submission) 0..1 ────── 1 (grade) Grade
```

**连接方式**:

- `Assignment` 通过 `List<Submission> assignmentSubmissions` 存储
- `Student` 通过 `List<Submission> studentSubmissions` 存储
- `Submission` 持有 `Assignment assignment` 和 `Student student` 的引用
- `Grade` 持有 `Submission submission` 的引用

### 订阅与支付

```
Subscription (subscription) 1 ────── 1..* (payments) Payment
Payment (payment) 0..1 ────── 0..1 (refund) Refund
```

**连接方式**:

- `Subscription` 通过 `List<Payment> subscriptionPayments` 存储
- `Payment` 持有 `Subscription subscription` 的引用
- `Refund` 持有 `Payment payment` 的引用

---

## 状态机设计

### 1. 课程生命周期（CourseLifecycle）

**上下文类**: `Course`

**状态集合**:

- `Draft`（草稿）
- `Published`（已发布）
- `EnrollmentOpen`（可选课）
- `Waitlisted`（候补）
- `InProgress`（进行中）
- `Completed`（已结课）
- `Cancelled`（已取消）

**状态存储位置**:

```java
// Course.java
private Status status;  // 私有字段存储当前状态
```

**状态转换事件**:

| 事件               | 源状态                      | 目标状态       | 守卫条件                                   |
| ------------------ | --------------------------- | -------------- | ------------------------------------------ |
| `publish()`        | Draft                       | Published      | `hasMinimumContent()` (至少 1 课时+1 作业) |
| `openEnrollment()` | Published                   | EnrollmentOpen | `hasCapacity()` (capacity > 0)             |
| `startCourse()`    | EnrollmentOpen / Waitlisted | InProgress     | `hasActiveEnrollments()` (至少 1 名学生)   |
| `complete()`       | InProgress                  | Completed      | 无硬性要求                                 |
| `cancel(reason)`   | \* (任意状态)               | Cancelled      | 无（可选：不能在 Completed 后取消）        |

**状态转换实现**:

```java
// 状态存储在 Course 类的私有字段中
private Status status;

// 状态转换通过 setStatus() 方法实现
private void setStatus(Status aStatus) {
    status = aStatus;
}

// 状态机方法检查当前状态并执行转换
public boolean publish() {
    boolean wasEventProcessed = false;
    Status aStatus = status;
    switch (aStatus) {
        case Draft:
            if (hasMinimumContent()) {
                setStatus(Status.Published);
                wasEventProcessed = true;
            }
            break;
        // ...
    }
    return wasEventProcessed;
}
```

---

### 2. 提交流程（SubmissionWorkflow）

**上下文类**: `Submission`

**状态集合**:

- `Created`（已建草稿）
- `Submitted`（已提交）
- `UnderCheck`（自动检查中）
- `Grading`（教师评分）
- `Graded`（已评分）
- `Returned`（已退回需查看）
- `ResubmissionRequested`（要求重交）

**状态存储位置**:

```java
// Submission.java
private Status status;  // 私有字段存储当前状态
```

**状态转换事件**:

| 事件                     | 源状态            | 目标状态              | 守卫条件                              |
| ------------------------ | ----------------- | --------------------- | ------------------------------------- |
| `submit()`               | Created           | Submitted             | `isBeforeDeadline()` (now ≤ deadline) |
| `startAutoChecks()`      | Submitted         | UnderCheck            | 无                                    |
| `checksPass()`           | UnderCheck        | Submitted             | 无（根据参数决定）                    |
| `checksFail()`           | UnderCheck        | Returned              | 无                                    |
| `startGrading()`         | Submitted         | Grading               | `checkPassed == true`                 |
| `grade(score, feedback)` | Grading           | Graded                | `0 ≤ score ≤ assignment.maxScore`     |
| `requestResubmission()`  | Graded / Returned | ResubmissionRequested | 由课程政策决定                        |

---

### 3. 订阅计费（SubscriptionBilling）

**上下文类**: `Subscription`

**状态集合**:

- `Trial`（试用）
- `Active`（活跃）
- `PastDue`（逾期宽限）
- `Suspended`（暂停）
- `Cancelled`（取消）

**状态存储位置**:

```java
// Subscription.java
private Status status;  // 私有字段存储当前状态
```

**状态转换事件**:

| 事件              | 源状态                   | 目标状态  | 守卫条件                            |
| ----------------- | ------------------------ | --------- | ----------------------------------- |
| `chargeSuccess()` | Trial / Active / PastDue | Active    | 最近一笔 Payment.status = Succeeded |
| `chargeFail()`    | Active                   | PastDue   | 最近一笔 Payment.status = Failed    |
| `graceExpire()`   | PastDue                  | Suspended | 当前日期 > 宽限到期                 |
| `cancel()`        | \* (任意状态)            | Cancelled | 无                                  |

---

## 状态存储位置

### 1. 对象状态存储

所有对象的状态都存储在**类的私有字段**中：

```java
// 示例：Course 类的状态存储
public class Course {
    // 基本属性状态
    private String id;           // 存储在对象实例中
    private String title;       // 存储在对象实例中
    private int capacity;        // 存储在对象实例中

    // 状态机状态
    private Status status;       // 存储在对象实例中

    // 关联对象状态（通过引用）
    private List<Lesson> lessons;              // 存储在对象实例中
    private List<Enrollment> courseEnrollments; // 存储在对象实例中
    private Instructor instructor;             // 存储在对象实例中
}
```

### 2. 状态存储层次

#### 内存存储（当前实现）

- **位置**: JVM 堆内存中的对象实例
- **生命周期**: 对象创建到垃圾回收
- **持久化**: 无（程序关闭后丢失）

#### 数据库存储（建议扩展）

```java
// 建议的数据库表结构
CREATE TABLE users (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100)
);

CREATE TABLE courses (
    id VARCHAR(50) PRIMARY KEY,
    title VARCHAR(200),
    capacity INT,
    status VARCHAR(20),  -- 状态机状态
    instructor_id VARCHAR(50)
);

CREATE TABLE enrollments (
    id VARCHAR(50) PRIMARY KEY,
    student_id VARCHAR(50),
    course_id VARCHAR(50),
    status VARCHAR(20),
    enrolled_at TIMESTAMP
);
```

### 3. 状态访问方式

#### 直接访问（当前实现）

```java
// 通过 Getter 方法访问状态
Course course = new Course(...);
Status currentStatus = course.getStatus();  // 读取状态
course.publish();  // 通过状态机方法修改状态
```

#### 通过 Repository 访问（建议扩展）

```java
// 建议的 Repository 模式
public interface CourseRepository {
    Course findById(String id);
    void save(Course course);
    List<Course> findByStatus(Status status);
}

// 使用示例
CourseRepository repository = new JpaCourseRepository();
Course course = repository.findById("C001");
Status status = course.getStatus();  // 状态从数据库加载
```

---

## 服务之间如何连接

### 1. 当前实现（POJO 模型层）

#### 通过对象引用连接

```java
// 示例：学生注册课程
Student student = new Student("S001", "Alice", "alice@test.com");
Course course = new Course("C001", "Java Programming", 50, instructor);

// 创建 Enrollment 关联对象
Enrollment enrollment = new Enrollment("E001", EnrollmentStatus.Active,
                                       new Date(), student, course);

// 连接建立：
// 1. Enrollment 持有 Student 和 Course 的引用
// 2. Student 的 studentEnrollments 列表包含此 Enrollment
// 3. Course 的 courseEnrollments 列表包含此 Enrollment
```

#### 双向关联维护

```java
// Enrollment 构造函数中自动建立双向关联
public Enrollment(String aId, EnrollmentStatus aStatus, Date aEnrolledAt,
                  Student aStudent, Course aCourse) {
    // ...
    setStudent(aStudent);  // 内部调用 student.addStudentEnrollment(this)
    setCourse(aCourse);    // 内部调用 course.addCourseEnrollment(this)
}
```

### 2. 服务层连接（建议扩展）

#### Service 层架构

```java
// UserService.java
public class UserService {
    private UserRepository userRepository;
    private EnrollmentRepository enrollmentRepository;

    public Enrollment enrollStudent(String studentId, String courseId) {
        Student student = userRepository.findStudentById(studentId);
        Course course = courseRepository.findById(courseId);

        // 业务逻辑验证
        if (course.getStatus() != Course.Status.EnrollmentOpen) {
            throw new IllegalStateException("Course not open for enrollment");
        }

        // 创建关联
        Enrollment enrollment = new Enrollment(...);
        enrollmentRepository.save(enrollment);

        return enrollment;
    }
}
```

#### 服务依赖注入

```java
// 使用 Spring Framework
@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserService userService;

    public Course publishCourse(String courseId) {
        Course course = courseRepository.findById(courseId);
        if (course.publish()) {
            courseRepository.save(course);
            return course;
        }
        throw new IllegalStateException("Cannot publish course");
    }
}
```

### 3. REST API 连接（建议扩展）

#### Controller 层

```java
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<Enrollment> enrollStudent(
            @PathVariable String courseId,
            @RequestParam String studentId) {
        Enrollment enrollment = courseService.enrollStudent(studentId, courseId);
        return ResponseEntity.ok(enrollment);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourse(@PathVariable String courseId) {
        Course course = courseService.findById(courseId);
        return ResponseEntity.ok(course);
    }
}
```

#### 服务调用流程

```
客户端 (HTTP Request)
    ↓
Controller 层 (CourseController)
    ↓
Service 层 (CourseService)
    ↓
Repository 层 (CourseRepository)
    ↓
数据库 (Database)
```

### 4. 消息队列连接（高级扩展）

```java
// 使用消息队列解耦服务
@Service
public class PaymentService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void processPayment(Payment payment) {
        // 处理支付
        payment.markSucceeded();

        // 发送消息到队列
        rabbitTemplate.convertAndSend("payment.exchange",
                                      "payment.succeeded",
                                      payment);
    }
}

// 订阅支付成功消息
@Component
public class SubscriptionListener {
    @RabbitListener(queues = "payment.succeeded")
    public void handlePaymentSucceeded(Payment payment) {
        Subscription subscription = payment.getSubscription();
        subscription.chargeSuccess();
    }
}
```

---

## OCL 约束规范

### 1. SeatsNotExceeded

**作用域**: `Course`

**约束**: `self.enrollments->select(e|e.status=Active)->size() <= self.capacity`

**实现位置**: `Course.enroll()` 方法中验证

```java
public Enrollment enroll(Student student) {
    // 验证容量约束
    long activeCount = courseEnrollments.stream()
        .filter(e -> e.getStatus() == EnrollmentStatus.Active)
        .count();

    if (activeCount >= capacity) {
        // 返回 Waitlisted 状态
        return createWaitlistedEnrollment(student);
    }

    return createActiveEnrollment(student);
}
```

### 2. EnrollmentOnlyAfterPublish

**作用域**: `Enrollment`

**约束**: `self.course.status <> CourseStatus::Draft`

**实现位置**: `Enrollment` 构造函数中验证

```java
public Enrollment(String aId, EnrollmentStatus aStatus, Date aEnrolledAt,
                  Student aStudent, Course aCourse) {
    // OCL 约束验证
    if (aCourse.getStatus() == Course.Status.Draft) {
        throw new IllegalArgumentException("Cannot enroll in Draft course");
    }
    // ...
}
```

### 3. SubmissionBeforeDeadline

**作用域**: `Submission`

**约束**: `self.submittedAt <= self.assignment.deadline`

**实现位置**: `Submission.submit()` 方法中验证

```java
public boolean submit() {
    // OCL 约束验证
    Date now = new Date();
    if (now.after(assignment.getDeadline())) {
        return false;  // 超过截止时间
    }
    // ...
}
```

### 4. GradeWithinRange

**作用域**: `Grade`

**约束**: `0 <= self.score and self.score <= self.submission.assignment.maxScore`

**实现位置**: `Grade` 构造函数和 `setScore()` 方法中验证

```java
public Grade(String aId, double aScore, String aFeedback, Submission aSubmission) {
    if (aScore < 0 || aScore > aSubmission.getAssignment().getMaxScore()) {
        throw new IllegalArgumentException("Score out of range");
    }
    // ...
}
```

---

## 方法签名与职责

### Course 类核心方法

```java
// 状态机方法
public boolean publish()                    // 发布课程
public boolean openEnrollment()             // 开放选课
public boolean startCourse()                // 开始课程
public boolean complete()                   // 完成课程
public boolean cancel(String reason)        // 取消课程

// 业务方法
public Enrollment enroll(Student student)   // 学生注册

// 守卫方法（私有）
private boolean hasMinimumContent()         // 检查是否有最少内容
private boolean hasCapacity()              // 检查是否有容量
private boolean hasActiveEnrollments()      // 检查是否有活跃学生
```

### Submission 类核心方法

```java
// 状态机方法
public boolean submit()                                    // 提交作业
public boolean startAutoChecks()                          // 开始自动检查
public boolean checksPass(boolean flagged, boolean compiledOk)  // 检查通过
public boolean checksFail()                              // 检查失败
public boolean startGrading()                            // 开始评分
public boolean grade(double score, String feedback)      // 评分
public boolean requestResubmission()                     // 要求重交
```

### Subscription 类核心方法

```java
// 状态机方法
public boolean chargeSuccess()    // 计费成功
public boolean chargeFail()       // 计费失败
public boolean cancel()           // 取消订阅
public boolean graceExpire()      // 宽限期到期
```

---

## 实现指南与扩展建议

### 1. 当前架构特点

- ✅ **纯 POJO 模型**: 所有类都是简单的 Java 对象
- ✅ **内存存储**: 状态存储在对象实例中
- ✅ **直接关联**: 通过对象引用建立连接
- ✅ **状态机实现**: 使用枚举和 switch 语句

### 2. 建议的扩展方向

#### A. 添加持久化层

```java
// 使用 JPA 注解
@Entity
@Table(name = "courses")
public class Course {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> courseEnrollments;
}
```

#### B. 添加服务层

```java
@Service
@Transactional
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Course publishCourse(String courseId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new CourseNotFoundException(courseId));

        if (course.publish()) {
            return courseRepository.save(course);
        }
        throw new BusinessException("Cannot publish course");
    }
}
```

#### C. 添加 REST API

```java
@RestController
@RequestMapping("/api/v1")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/courses/{id}/publish")
    public ResponseEntity<CourseDTO> publishCourse(@PathVariable String id) {
        Course course = courseService.publishCourse(id);
        return ResponseEntity.ok(CourseDTO.from(course));
    }
}
```

#### D. 添加测试

```java
@SpringBootTest
class CourseServiceTest {
    @Autowired
    private CourseService courseService;

    @Test
    void testPublishCourse() {
        Course course = new Course("C001", "Java", 50, instructor);
        courseService.save(course);

        Course published = courseService.publishCourse("C001");
        assertEquals(Course.Status.Published, published.getStatus());
    }
}
```

### 3. 架构演进路径

```
阶段 1: POJO 模型层（当前）
    ↓
阶段 2: + Repository 层（数据访问）
    ↓
阶段 3: + Service 层（业务逻辑）
    ↓
阶段 4: + Controller 层（REST API）
    ↓
阶段 5: + 消息队列（异步处理）
    ↓
阶段 6: 微服务架构（服务拆分）
```

---

## 总结

### 当前架构

- **状态存储**: 对象私有字段（内存中）
- **服务连接**: 对象引用（直接关联）
- **数据持久化**: 无（程序关闭后丢失）
- **API 接口**: 无（纯模型层）

### 建议架构

- **状态存储**: 数据库表（持久化）
- **服务连接**: Service 层 + Repository 层
- **数据持久化**: JPA / MyBatis
- **API 接口**: REST API (Spring Boot)

### 下一步行动

1. ✅ 理解当前 POJO 模型结构
2. 🔄 添加数据库持久化（JPA）
3. 🔄 实现 Service 层业务逻辑
4. 🔄 创建 REST API 接口
5. 🔄 编写单元测试和集成测试

---

**文档版本**: 1.0  
**最后更新**: 2025-11-10  
**维护者**: Online Learning Platform Development Team
