# 在线学习平台 MVP 实现任务清单（Java 版）

> **目标**: 基于 `architecture.md` 的设计，逐步实现一个可测试的在线学习平台 MVP
>
> **原则**:
>
> - 每个任务都非常小且可独立测试
> - 每个任务有明确的开始和结束标准
> - 任务按依赖顺序排列
> - 先实现核心功能，再实现高级特性
> - 使用 JUnit 5 和 Spring Boot Test 进行单元测试和集成测试
> - 使用 Spring Boot 框架实现 REST API
> - 使用 Spring Data JPA 实现数据持久化

---

## 阶段 0: 环境准备与验证 (7 个任务)

### Task 0.1: 设置 Spring Boot 项目结构

**目标**: 创建 Spring Boot 项目基础结构

- [ ] 创建 Spring Boot 标准目录结构：
  - `src/main/java/com/olp/`
  - `src/main/resources/`
  - `src/test/java/com/olp/`
- [ ] 创建 `pom.xml` Maven 配置文件
- [ ] 添加 Spring Boot 依赖：
  - `spring-boot-starter-web` (REST API)
  - `spring-boot-starter-data-jpa` (数据库)
  - `spring-boot-starter-test` (测试)
  - `h2` (开发数据库)
- [ ] 配置 Java 11 编译版本
- [ ] 创建 Spring Boot 主类：`OnlineLearningPlatformApplication.java`

**测试标准**:

- 项目可以编译：`mvn clean compile`
- Spring Boot 应用可以启动：`mvn spring-boot:run`
- 无编译错误

**开始条件**: 无

**结束条件**: Spring Boot 项目可以正常启动

---

### Task 0.2: 配置 Spring Boot 应用

**目标**: 配置 Spring Boot 应用属性和 CORS

- [ ] 创建 `application.properties` 配置文件
- [ ] 配置服务器端口（8080）
- [ ] 配置 H2 数据库连接（开发环境）
- [ ] 配置 JPA/Hibernate 设置
- [ ] 创建 `application-dev.properties` 和 `application-prod.properties`
- [ ] 创建 `CorsConfig.java` 配置类（支持前端跨域）

**测试标准**:

- 应用可以启动并连接到 H2 数据库
- H2 控制台可以访问：http://localhost:8080/h2-console
- CORS 配置正确

**开始条件**: Task 0.1 完成

**结束条件**: 应用配置完成，可以正常启动

---

### Task 0.3: 迁移 Umple 生成的类到 Spring Boot 结构

**目标**: 将 Umple 生成的类移动到 Spring Boot 包结构中

- [ ] 将 Umple 生成的类移动到对应的包：
  - `User.java, Student.java, Instructor.java, Administrator.java` → `model/user/`
  - `Course.java, Enrollment.java, Lesson.java, VideoContent.java, CourseCategory.java` → `model/course/`
  - `Assignment.java, Submission.java, Grade.java` → `model/assignment/`
  - `Payment.java, Subscription.java, Refund.java` → `model/payment/`
- [ ] 更新所有类的包声明为 `com.olp.model.*`
- [ ] 验证所有类可以编译（无语法错误）
- [ ] 确认所有枚举类型定义存在

**测试标准**:

- 所有 15 个类文件在正确的包中
- `mvn clean compile` 成功
- 所有枚举类型定义正确
- 包结构符合 Spring Boot 规范

**开始条件**: Task 0.2 完成

**结束条件**: 所有类迁移完成并编译通过

---

### Task 0.4: 验证状态机枚举定义

**目标**: 确认三个状态机的枚举类型正确

- [x] 验证 `Course.Status` 枚举（7 个状态：Draft, Published, EnrollmentOpen, Waitlisted, InProgress, Completed, Cancelled）
- [x] 验证 `Submission.Status` 枚举（7 个状态：Created, Submitted, UnderCheck, Grading, Graded, Returned, ResubmissionRequested）
- [x] 验证 `Subscription.Status` 枚举（5 个状态：Trial, Active, PastDue, Suspended, Cancelled）
- [x] 验证其他枚举：EnrollmentStatus, PaymentStatus, PlanType

**测试标准**:

- 所有枚举值与 architecture.md 一致
- 枚举可以正确使用（创建测试类验证）

**开始条件**: Task 0.2 完成

**结束条件**: 所有枚举验证通过

---

### Task 0.5: 创建工具类

**目标**: 创建 `Utils.java` 用于存放公共工具函数

- [x] 创建 `com.olp.util.Utils` 类
- [x] 声明时间比较方法：`public static int compareDates(Date d1, Date d2)`
- [x] 声明 ID 生成方法：`public static String generateId(String prefix)`
- [x] 声明时间计算方法：`public static Date addDays(Date base, int days)`, `public static Date addMonths(Date base, int months)`
- [x] 声明当前时间获取：`public static Date getCurrentTime()`

**测试标准**:

- Utils.java 文件存在
- 所有方法声明语法正确
- 类可以编译

**开始条件**: Task 0.4 完成

**结束条件**: Utils 类编译通过

---

### Task 0.6: 实现工具类方法

**目标**: 实现 `Utils.java` 中的所有工具方法

- [x] 实现 `compareDates()`: 返回 -1/0/1（使用 `Date.compareTo()`）
- [x] 实现 `generateId()`: 使用 UUID 或时间戳生成唯一 ID（建议：`prefix + "_" + System.currentTimeMillis() + "_" + counter`）
- [x] 实现 `addDays()`: 使用 `Calendar` 或 `LocalDate` 添加天数
- [x] 实现 `addMonths()`: 使用 `Calendar` 或 `LocalDate` 添加月数
- [x] 实现 `getCurrentTime()`: 返回当前系统时间（`new Date()`）

**测试标准**:

- 创建 JUnit 测试类 `UtilsTest.java`（使用 `@SpringBootTest`）
- `compareDates()` 正确比较不同日期
- `generateId()` 每次调用返回不同 ID
- 时间计算方法处理边界情况（如 1 月 31 日 + 1 个月）

**开始条件**: Task 0.5 完成

**结束条件**: 所有工具方法实现并通过测试

---

### Task 0.7: 配置 JPA 实体注解

**目标**: 为模型类添加 JPA 注解，准备数据库持久化

- [x] 为所有模型类添加 `@Entity` 注解
- [x] 为所有主键字段添加 `@Id` 注解
- [x] 配置关联关系注解：
  - `@OneToMany`, `@ManyToOne`, `@OneToOne`, `@ManyToMany`
- [x] 添加 `@Table` 注解指定表名
- [x] 验证 JPA 配置正确（应用可以启动，Hibernate 可以创建表）

**测试标准**:

- 所有实体类有正确的 JPA 注解
- 应用启动时 Hibernate 可以创建表结构
- 无 JPA 配置错误

**开始条件**: Task 0.6 完成

**结束条件**: JPA 配置完成，应用可以正常启动

---

## 阶段 1: 用户模块基础实现 (4 个任务)

### Task 1.1: 实现 User 基类构造函数验证

**目标**: 为 User 类添加属性验证

- [x] 打开 `User.java`
- [x] 在构造函数中添加验证：
  - `id` 不能为 null 或空字符串
  - `email` 格式基本验证（包含 @ 符号）
  - `name` 不能为 null 或空字符串
- [x] 验证失败时抛出 `IllegalArgumentException` 异常

**测试标准**:

- 创建 JUnit 测试 `UserTest.java`
- 创建有效 User 对象成功（通过子类测试）
- 传入 null id 抛出 `IllegalArgumentException`
- 传入无效 email（无 @）抛出异常
- 传入空 name 抛出异常

**开始条件**: Task 0.5 完成

**结束条件**: User 构造函数验证通过所有测试

---

### Task 1.2: 实现 Student 类基础功能

**目标**: 完善 Student 类的基本操作

- [x] 验证 Student 可以正确继承 User
- [x] 测试添加/移除 Enrollment 关联（使用 Umple 生成的方法）
- [x] 测试添加/移除 Submission 关联
- [x] 添加便捷方法：`public int getActiveEnrollmentCount()`
  - 统计 `studentEnrollments` 中 `status == EnrollmentStatus.Active` 的数量

**测试标准**:

- 创建 JUnit 测试 `StudentTest.java`
- 可以创建 Student 对象
- 可以访问继承的属性（id, name, email）
- 关联集合操作正常（添加/移除 Enrollment）
- `getActiveEnrollmentCount()` 返回正确结果

**开始条件**: Task 1.1 完成

**结束条件**: Student 类功能通过所有测试

---

### Task 1.3: 实现 Instructor 类基础功能

**目标**: 完善 Instructor 类的基本操作

- [x] 验证 Instructor 可以正确继承 User
- [x] 测试添加/移除 Course 关联
- [x] 添加便捷方法：`public int getActiveCourseCount()`
  - 统计 `taughtCourses` 中状态不是 `Cancelled` 和 `Completed` 的课程数
- [x] 添加便捷方法：`public List<Course> getCoursesInProgress()`
  - 返回状态为 `InProgress` 的课程列表

**测试标准**:

- 创建 JUnit 测试 `InstructorTest.java`
- 可以创建 Instructor 对象
- 可以管理课程列表
- `getActiveCourseCount()` 返回正确结果
- `getCoursesInProgress()` 返回正确列表

**开始条件**: Task 1.2 完成

**结束条件**: Instructor 类功能通过所有测试

---

### Task 1.4: 实现 Administrator 类占位符

**目标**: 为管理员类预留接口

- [x] 验证 Administrator 可以正确继承 User
- [x] 添加注释说明未来功能（系统管理、用户管理等）
- [x] 确保可以创建 Administrator 对象

**测试标准**:

- 创建 JUnit 测试 `AdministratorTest.java`
- 可以创建 Administrator 对象
- 继承关系正确

**开始条件**: Task 1.3 完成

**结束条件**: Administrator 类可以正常创建和使用

---

## 阶段 2: 课程模块核心实现 (10 个任务)

### Task 2.1: 实现 CourseCategory 类

**目标**: 实现最简单的课程分类实体

- [x] 打开 `CourseCategory.java`
- [x] 在构造函数中验证 `id` 和 `name` 不为 null 或空字符串
- [x] 验证失败时抛出 `IllegalArgumentException`
- [x] 测试创建多个分类对象
- [x] 测试分类的多对多关联（添加到 Course）

**测试标准**:

- 创建 JUnit 测试 `CourseCategoryTest.java`
- 可以创建 CourseCategory 对象
- id 和 name 验证正常
- 可以与 Course 建立多对多关系

**开始条件**: Task 1.4 完成

**结束条件**: CourseCategory 类功能通过所有测试

---

### Task 2.2: 实现 VideoContent 类

**目标**: 实现视频内容实体

- [x] 打开 `VideoContent.java`
- [x] 验证 `url` 不为 null 或空字符串
- [x] 验证 `durationSec > 0`
- [x] 验证失败时抛出 `IllegalArgumentException`
- [x] 确保 Composition 关系正确（通过 Lesson 管理）

**测试标准**:

- 创建 JUnit 测试 `VideoContentTest.java`
- 可以创建 VideoContent 对象
- url 和 durationSec 验证正常
- 可以与 Lesson 建立关联

**开始条件**: Task 2.1 完成

**结束条件**: VideoContent 类功能通过所有测试

---

### Task 2.3: 实现 Lesson 类基础功能

**目标**: 实现课时实体

- [x] 打开 `Lesson.java`
- [x] 验证 `orderIndex >= 0`
- [x] 验证 `title` 不为 null 或空字符串
- [x] 添加便捷方法：`public int getTotalDuration()`
  - 计算所有关联的 VideoContent 的 `durationSec` 之和
- [x] 验证失败时抛出 `IllegalArgumentException`

**测试标准**:

- 创建 JUnit 测试 `LessonTest.java`
- 可以创建 Lesson 对象
- orderIndex 验证正常
- 可以添加 VideoContent
- `getTotalDuration()` 计算正确

**开始条件**: Task 2.2 完成

**结束条件**: Lesson 类功能通过所有测试

---

### Task 2.4: 实现 Enrollment 关联类基础功能

**目标**: 实现选课记录实体

- [x] 打开 `Enrollment.java`
- [x] 验证 `enrolledAt` 不为 null
- [x] 验证 `student` 和 `course` 关联不为 null
- [x] 实现状态转换方法：`public boolean dropCourse()`
  - 从 `Active` 或 `Waitlisted` 转换为 `Dropped`
- [x] 验证失败时抛出 `IllegalArgumentException` 或返回 false

**测试标准**:

- 创建 JUnit 测试 `EnrollmentTest.java`
- 可以创建 Enrollment 对象
- 关联验证正常
- `dropCourse()` 状态转换正常工作

**开始条件**: Task 2.3 完成

**结束条件**: Enrollment 类功能通过所有测试

---

### Task 2.5: 实现 Course 类构造函数验证

**目标**: 为 Course 类添加基本验证

- [x] 打开 `Course.java`
- [x] 验证 `capacity > 0`
- [x] 验证 `title` 不为 null 或空字符串
- [x] 验证 `instructor` 不为 null
- [x] 验证失败时抛出 `IllegalArgumentException`
- [x] 确认初始状态为 `Draft`（Umple 已生成）

**测试标准**:

- 创建 JUnit 测试 `CourseTest.java`
- 可以创建 Course 对象
- 容量验证正常（capacity <= 0 抛出异常）
- 初始状态为 Draft

**开始条件**: Task 2.4 完成

**结束条件**: Course 构造函数验证通过所有测试

---

### Task 2.6: 实现 Course::publish() 状态转换

**目标**: 实现课程发布功能

- [x] 在 `Course.java` 中找到 `publish()` 方法（Umple 已生成框架）
- [x] 添加守卫条件：当前状态必须是 `Draft`
- [x] 添加守卫方法：`private boolean hasMinimumContent()`
  - 检查 `lessons.size() >= 1` 且 `courseAssignments.size() >= 1`
- [x] 在 `publish()` 中调用守卫方法
- [x] 状态转换：`setStatus(Status.Published)`
- [x] 返回 `true` 表示成功，守卫失败返回 `false`

**测试标准**:

- 在 `CourseTest.java` 中添加测试
- Draft 状态的课程可以发布（有内容）
- 没有 Lesson 的课程发布失败
- 没有 Assignment 的课程发布失败
- 非 Draft 状态的课程发布失败

**开始条件**: Task 2.5 完成

**结束条件**: `publish()` 方法通过所有测试

---

### Task 2.7: 实现 Course::openEnrollment() 状态转换

**目标**: 实现开放选课功能

- [x] 在 `Course.java` 中找到 `openEnrollment()` 方法
- [x] 添加守卫条件：当前状态必须是 `Published`
- [x] 添加守卫条件：`capacity > 0`
- [x] 状态转换：`setStatus(Status.EnrollmentOpen)`
- [x] 返回操作结果（boolean）

**测试标准**:

- 在 `CourseTest.java` 中添加测试
- Published 状态的课程可以开放选课
- capacity = 0 的课程开放失败
- 非 Published 状态的课程开放失败

**开始条件**: Task 2.6 完成

**结束条件**: `openEnrollment()` 方法通过所有测试

---

### Task 2.8: 实现 Course::enroll() 选课逻辑

**目标**: 实现学生选课核心逻辑

- [x] 在 `Course.java` 中实现 `public Enrollment enroll(Student student)` 方法
- [x] 检查课程状态（必须是 `EnrollmentOpen` 或 `Waitlisted`）
- [x] 检查学生是否已报名（遍历 `courseEnrollments`，避免重复）
- [x] 统计当前 `Active` 报名数（遍历 `courseEnrollments`，统计 `status == EnrollmentStatus.Active`）
- [x] 如果未满（activeCount < capacity），创建 `Active` 状态的 Enrollment
- [x] 如果已满，创建 `Waitlisted` 状态的 Enrollment，并将课程状态改为 `Waitlisted`（如果当前是 `EnrollmentOpen`）
- [x] 使用 `Utils.generateId()` 生成 enrollment ID
- [x] 使用 `Utils.getCurrentTime()` 设置 `enrolledAt`
- [x] 返回创建的 Enrollment 对象

**测试标准**:

- 在 `CourseTest.java` 中添加测试
- 未满员课程可以正常选课（返回 Active 状态 Enrollment）
- 满员课程自动进入候补（返回 Waitlisted 状态 Enrollment）
- 课程状态在满员时自动切换到 Waitlisted
- 重复选课被拒绝（返回 null 或抛出异常）

**开始条件**: Task 2.7 完成

**结束条件**: `enroll()` 方法通过所有测试

---

### Task 2.9: 实现 Course::startCourse() 状态转换

**目标**: 实现课程开课功能

- [x] 在 `Course.java` 中找到 `startCourse()` 方法
- [x] 添加守卫条件：当前状态必须是 `EnrollmentOpen` 或 `Waitlisted`
- [x] 添加守卫方法：`private boolean hasActiveEnrollments()`
  - 检查至少有一个 `Active` 状态的 Enrollment
- [x] 在 `startCourse()` 中调用守卫方法
- [x] 状态转换：`setStatus(Status.InProgress)`
- [x] 返回操作结果（boolean）

**测试标准**:

- 在 `CourseTest.java` 中添加测试
- 有 Active 学生的课程可以开课
- 没有学生的课程开课失败
- 只有 Waitlisted 学生的课程开课失败
- 非 EnrollmentOpen/Waitlisted 状态的课程开课失败

**开始条件**: Task 2.8 完成

**结束条件**: `startCourse()` 方法通过所有测试

---

### Task 2.10: 实现 Course::complete() 和 Course::cancel() 状态转换

**目标**: 实现课程结束和取消功能

- [x] 实现 `complete()` 方法：
  - 守卫条件：当前状态必须是 `InProgress`
  - 状态转换：`setStatus(Status.Completed)`
  - 返回操作结果
- [x] 实现 `cancel(String reason)` 方法：
  - 可以从任意状态取消（除了 `Completed`）
  - 状态转换：`setStatus(Status.Cancelled)`
  - 记录取消原因（可以添加新属性 `cancelReason: String`，需要修改 Umple 模型或直接添加字段）
  - 返回操作结果

**测试标准**:

- 在 `CourseTest.java` 中添加测试
- InProgress 的课程可以结课
- 任何状态（除 Completed）可以取消
- Completed 的课程不能取消

**开始条件**: Task 2.9 完成

**结束条件**: `complete()` 和 `cancel()` 方法通过所有测试

---

## 阶段 3: 作业系统实现 (10 个任务)

### Task 3.1: 实现 Assignment 类基础功能

**目标**: 实现作业实体

- [x] 打开 `Assignment.java`
- [x] 验证 `title` 不为 null 或空字符串
- [x] 验证 `maxScore > 0`
- [x] 验证 `deadline` 不为 null
- [x] 验证失败时抛出 `IllegalArgumentException`
- [x] 添加便捷方法：`public boolean isOverdue()`
  - 比较当前时间与 `deadline`（使用 `Utils.compareDates()`）
- [x] 添加便捷方法：`public int getSubmissionCount()`
  - 返回 `assignmentSubmissions.size()`

**测试标准**:

- 创建 JUnit 测试 `AssignmentTest.java`
- 可以创建 Assignment 对象
- maxScore 验证正常（<= 0 抛出异常）
- `isOverdue()` 判断正确（当前时间 > deadline 返回 true）
- `getSubmissionCount()` 返回正确数量

**开始条件**: Task 2.10 完成

**结束条件**: Assignment 类功能通过所有测试

---

### Task 3.2: 实现 Grade 类基础功能

**目标**: 实现成绩实体

- [x] 打开 `Grade.java`
- [x] 验证 `score >= 0`
- [x] 验证 `score <= submission.getAssignment().getMaxScore()`（需要访问关联）
- [x] 验证 `submission` 不为 null
- [x] 验证失败时抛出 `IllegalArgumentException`
- [x] 添加便捷方法：`public double getPercentage()`
  - 计算 `(score / assignment.maxScore) * 100.0`
  - 处理除零情况（返回 0.0）

**测试标准**:

- 创建 JUnit 测试 `GradeTest.java`
- 可以创建 Grade 对象
- 分数范围验证正常（负数或超过 maxScore 抛出异常）
- `getPercentage()` 计算正确

**开始条件**: Task 3.1 完成

**结束条件**: Grade 类功能通过所有测试

---

### Task 3.3: 实现 Submission 类构造函数验证

**目标**: 为 Submission 类添加基本验证

- [x] 打开 `Submission.java`
- [x] 验证 `assignment` 和 `student` 不为 null
- [x] 验证失败时抛出 `IllegalArgumentException`
- [x] 确认初始状态为 `Created`（Umple 已生成）
- [x] 确认初始 `version` 为构造函数参数值
- [x] 确认初始 `checkPassed` 为构造函数参数值
- [x] `submittedAt` 初始可为 null

**测试标准**:

- 创建 JUnit 测试 `SubmissionTest.java`
- 可以创建 Submission 对象
- 初始状态为 Created
- 关联验证正常（null assignment 或 student 抛出异常）

**开始条件**: Task 3.2 完成

**结束条件**: Submission 构造函数验证通过所有测试

---

### Task 3.4: 实现 Submission::submit() 状态转换

**目标**: 实现作业提交功能

- [x] 在 `Submission.java` 中找到 `submit()` 方法
- [x] 添加守卫条件：当前状态必须是 `Created`
- [x] 添加守卫条件：`Utils.getCurrentTime()` <= `assignment.getDeadline()`（使用 `Utils.compareDates()`）
- [x] 设置 `submittedAt := Utils.getCurrentTime()`
- [x] 计算版本号：统计该学生对该作业的已有提交数 + 1
  - 遍历 `assignment.getAssignmentSubmissions()`，筛选 `student == this.student`，统计数量
- [x] 状态转换：`setStatus(Status.Submitted)`
- [x] 返回操作结果（boolean）

**测试标准**:

- 在 `SubmissionTest.java` 中添加测试
- Created 状态的提交可以提交
- 超过截止时间的提交失败
- 版本号自动递增（第一个提交 version = 1，第二个 version = 2）
- 提交时间被记录

**开始条件**: Task 3.3 完成

**结束条件**: `submit()` 方法通过所有测试

---

### Task 3.5: 实现 Submission::startAutoChecks() 状态转换

**目标**: 实现自动检查触发功能

- [x] 在 `Submission.java` 中找到 `startAutoChecks()` 方法
- [x] 添加守卫条件：当前状态必须是 `Submitted`
- [x] 状态转换：`setStatus(Status.UnderCheck)`
- [x] 返回操作结果（boolean）
- [x] 添加注释：实际的检查逻辑（查重、编译）由外部系统触发

**测试标准**:

- 在 `SubmissionTest.java` 中添加测试
- Submitted 状态的提交可以开始检查
- 非 Submitted 状态的提交检查失败

**开始条件**: Task 3.4 完成

**结束条件**: `startAutoChecks()` 方法通过所有测试

---

### Task 3.6: 实现 Submission::checksPass() 和 checksFail() 状态转换

**目标**: 实现检查结果处理

- [x] 实现 `checksPass()` 方法（如果 Umple 未生成，需要添加）：
  - 守卫条件：当前状态必须是 `UnderCheck`
  - 设置 `checkPassed := true`
  - 状态转换：`setStatus(Status.Submitted)`（回到已提交，标记为可评分）
  - 返回操作结果
- [x] 实现 `checksFail()` 方法：
  - 守卫条件：当前状态必须是 `UnderCheck`
  - 设置 `checkPassed := false`
  - 状态转换：`setStatus(Status.Returned)`
  - 返回操作结果

**测试标准**:

- 在 `SubmissionTest.java` 中添加测试
- UnderCheck 状态的提交可以标记为通过
- UnderCheck 状态的提交可以标记为失败
- `checkPassed` 标志正确设置
- 状态正确转换

**开始条件**: Task 3.5 完成

**结束条件**: `checksPass()` 和 `checksFail()` 方法通过所有测试

---

### Task 3.7: 实现 Submission::startGrading() 状态转换

**目标**: 实现开始评分功能

- [x] 在 `Submission.java` 中找到 `startGrading()` 方法
- [x] 添加守卫条件：当前状态必须是 `Submitted`
- [x] 添加守卫条件：`checkPassed == true`
- [x] 状态转换：`setStatus(Status.Grading)`
- [x] 返回操作结果（boolean）

**测试标准**:

- 在 `SubmissionTest.java` 中添加测试
- 检查通过的提交可以开始评分
- 检查未通过的提交评分失败
- 非 Submitted 状态的提交评分失败

**开始条件**: Task 3.6 完成

**结束条件**: `startGrading()` 方法通过所有测试

---

### Task 3.8: 实现 Submission::grade() 状态转换

**目标**: 实现评分功能

- [x] 在 `Submission.java` 中找到 `grade(double score, String feedback)` 方法
- [x] 添加守卫条件：当前状态必须是 `Grading`
- [x] 添加守卫条件：`0 <= score <= assignment.getMaxScore()`
- [x] 如果 `submissionGrade` 为 null，创建新的 Grade 对象
- [x] 如果 `submissionGrade` 已存在，更新其 `score` 和 `feedback`
- [x] 状态转换：`setStatus(Status.Graded)`
- [x] 返回操作结果（boolean）

**测试标准**:

- 在 `SubmissionTest.java` 中添加测试
- Grading 状态的提交可以评分
- 分数范围验证正常（负数或超过 maxScore 失败）
- Grade 对象正确创建或更新
- 状态正确转换为 Graded

**开始条件**: Task 3.7 完成

**结束条件**: `grade()` 方法通过所有测试

---

### Task 3.9: 实现 Submission::requestResubmission() 状态转换

**目标**: 实现要求重交功能

- [x] 在 `Submission.java` 中找到 `requestResubmission()` 方法
- [x] 添加守卫条件：当前状态必须是 `Graded` 或 `Returned`
- [x] 添加守卫条件：`Utils.getCurrentTime() <= assignment.getDeadline()`（可选，根据业务需求）
- [x] 状态转换：`setStatus(Status.ResubmissionRequested)`
- [x] 返回操作结果（boolean）
- [x] 添加注释：学生可以创建新的 Submission（version 递增）

**测试标准**:

- 在 `SubmissionTest.java` 中添加测试
- Graded 状态的提交可以要求重交
- Returned 状态的提交可以要求重交
- 超过截止时间的提交重交失败（如果实现该守卫）

**开始条件**: Task 3.8 完成

**结束条件**: `requestResubmission()` 方法通过所有测试

---

### Task 3.10: 实现提交工作流测试

**目标**: 创建完整的提交工作流集成测试

- [x] 创建 JUnit 测试类 `SubmissionWorkflowTest.java`
- [x] 测试完整流程：
  1. 创建 Submission（状态：Created）
  2. 提交（状态：Submitted）
  3. 开始自动检查（状态：UnderCheck）
  4. 检查通过（状态：Submitted，checkPassed = true）
  5. 开始评分（状态：Grading）
  6. 评分（状态：Graded）
- [x] 测试失败流程：
  1. 创建 Submission
  2. 提交
  3. 开始自动检查
  4. 检查失败（状态：Returned）
  5. 要求重交（状态：ResubmissionRequested）

**测试标准**:

- 所有状态转换正确
- 守卫条件正确执行
- 完整流程测试通过

**开始条件**: Task 3.9 完成

**结束条件**: 提交工作流测试全部通过

---

## 阶段 4: 支付系统实现 (10 个任务)

### Task 4.1: 实现 Subscription 构造函数验证

**目标**: 为 Subscription 类添加基本验证

- [x] 打开 `Subscription.java`
- [x] 验证 `plan` 不为 null
- [x] 验证 `startAt` 不为 null
- [x] `nextBillingAt` 可以为 null（初始创建时）
- [x] 验证失败时抛出 `IllegalArgumentException`
- [x] 确认初始状态为 `Trial`（Umple 已生成）

**测试标准**:

- 创建 JUnit 测试 `SubscriptionTest.java`
- 可以创建 Subscription 对象
- 初始状态为 Trial
- 参数验证正常

**开始条件**: Task 3.10 完成

**结束条件**: Subscription 构造函数验证通过所有测试

---

### Task 4.2: 实现 Subscription::chargeSuccess() 状态转换

**目标**: 实现计费成功功能

- [x] 在 `Subscription.java` 中找到 `chargeSuccess()` 方法
- [x] 添加守卫条件：当前状态必须是 `Trial`、`Active` 或 `PastDue`
- [x] 添加守卫条件：最近一笔 Payment 状态为 `Succeeded`
  - 遍历 `subscriptionPayments`，找到最新的 Payment，检查其状态
- [x] 状态转换：`setStatus(Status.Active)`
- [x] 更新 `nextBillingAt`：
  - 如果 `plan == PlanType.Monthly`，使用 `Utils.addMonths(nextBillingAt, 1)`
  - 如果 `plan == PlanType.Annual`，使用 `Utils.addMonths(nextBillingAt, 12)`
  - 如果 `plan == PlanType.Trial`，不更新（或按业务需求）
- [x] 返回操作结果（boolean）

**测试标准**:

- 在 `SubscriptionTest.java` 中添加测试
- Trial/Active/PastDue 状态的订阅可以计费成功
- nextBillingAt 正确更新（Monthly +1 月，Annual +12 月）
- 没有成功 Payment 的订阅计费失败

**开始条件**: Task 4.1 完成

**结束条件**: `chargeSuccess()` 方法通过所有测试

---

### Task 4.3: 实现 Subscription::chargeFail() 状态转换

**目标**: 实现计费失败功能

- [x] 在 `Subscription.java` 中找到 `chargeFail()` 方法
- [x] 添加守卫条件：当前状态必须是 `Active`
- [x] 添加守卫条件：最近一笔 Payment 状态为 `Failed`
- [x] 状态转换：`setStatus(Status.PastDue)`
- [x] 返回操作结果（boolean）

**测试标准**:

- 在 `SubscriptionTest.java` 中添加测试
- Active 状态的订阅计费失败后转为 PastDue
- 非 Active 状态的订阅计费失败操作失败

**开始条件**: Task 4.2 完成

**结束条件**: `chargeFail()` 方法通过所有测试

---

### Task 4.4: 实现 Subscription::cancel() 状态转换

**目标**: 实现取消订阅功能

- [x] 在 `Subscription.java` 中找到 `cancel()` 方法
- [x] 可以从任意状态取消（无守卫条件）
- [x] 状态转换：`setStatus(Status.Cancelled)`
- [x] 返回操作结果（boolean）

**测试标准**:

- 在 `SubscriptionTest.java` 中添加测试
- 任何状态的订阅都可以取消
- 状态正确转换为 Cancelled

**开始条件**: Task 4.3 完成

**结束条件**: `cancel()` 方法通过所有测试

---

### Task 4.5: 实现 Subscription::graceExpire() 状态转换

**目标**: 实现宽限期到期功能

- [x] 在 `Subscription.java` 中找到 `graceExpire()` 方法（如果 Umple 未生成，需要添加）
- [x] 添加守卫条件：当前状态必须是 `PastDue`
- [x] 添加守卫条件：当前日期 > 宽限到期（可以硬编码宽限期，如 7 天）
  - 使用 `Utils.addDays(nextBillingAt, 7)` 计算宽限到期日
- [x] 状态转换：`setStatus(Status.Suspended)`
- [x] 返回操作结果（boolean）

**测试标准**:

- 在 `SubscriptionTest.java` 中添加测试
- PastDue 状态的订阅宽限期到期后转为 Suspended
- 未到期的订阅操作失败

**开始条件**: Task 4.4 完成

**结束条件**: `graceExpire()` 方法通过所有测试

---

### Task 4.6: 实现 Payment 构造函数验证

**目标**: 为 Payment 类添加基本验证

- [x] 打开 `Payment.java`
- [x] 验证 `amount > 0`
- [x] 验证 `subscription` 不为 null
- [x] 验证失败时抛出 `IllegalArgumentException`
- [x] 确认初始状态为构造函数参数值（通常是 `Pending`）

**测试标准**:

- 创建 JUnit 测试 `PaymentTest.java`
- 可以创建 Payment 对象
- amount 验证正常（<= 0 抛出异常）
- 初始状态正确

**开始条件**: Task 4.5 完成

**结束条件**: Payment 构造函数验证通过所有测试

---

### Task 4.7: 实现 Payment 状态转换方法

**目标**: 实现支付状态管理

- [x] 在 `Payment.java` 中添加方法：`public boolean markSucceeded()`
  - 守卫条件：当前状态必须是 `Pending`
  - 状态转换：`setStatus(PaymentStatus.Succeeded)`
  - 设置 `paidAt := Utils.getCurrentTime()`
- [x] 添加方法：`public boolean markFailed()`
  - 守卫条件：当前状态必须是 `Pending`
  - 状态转换：`setStatus(PaymentStatus.Failed)`
- [x] 返回操作结果（boolean）

**测试标准**:

- 在 `PaymentTest.java` 中添加测试
- Pending 状态的支付可以标记为成功
- Pending 状态的支付可以标记为失败
- 非 Pending 状态的支付操作失败

**开始条件**: Task 4.6 完成

**结束条件**: Payment 状态转换方法通过所有测试

---

### Task 4.8: 实现 Payment::initiateRefund() 方法

**目标**: 实现发起退款功能

- [x] 在 `Payment.java` 中添加方法：`public boolean initiateRefund(double amount)`
- [x] 添加守卫条件：当前状态必须是 `Succeeded`
- [x] 添加守卫条件：`0 < amount <= this.amount`
- [x] 状态转换：`setStatus(PaymentStatus.Refunding)`
- [x] 创建 Refund 对象：
  - 使用 `Utils.generateId("REF")` 生成 ID
  - 设置 `requestedAt := Utils.getCurrentTime()`
  - 设置 `processedAt := null`
  - 关联到当前 Payment
- [x] 返回操作结果（boolean）

**测试标准**:

- 在 `PaymentTest.java` 中添加测试
- Succeeded 状态的支付可以发起退款
- 退款金额验证正常（负数或超过原金额失败）
- Refund 对象正确创建
- 状态正确转换为 Refunding

**开始条件**: Task 4.7 完成

**结束条件**: `initiateRefund()` 方法通过所有测试

---

### Task 4.9: 实现 Refund 构造函数验证

**目标**: 为 Refund 类添加基本验证

- [x] 打开 `Refund.java`
- [x] 验证 `amount > 0`
- [x] 验证 `payment` 不为 null
- [x] 验证 `payment.getStatus() == PaymentStatus.Succeeded`（OCL 约束：RefundOnlyForSucceededPayment）
- [x] 验证失败时抛出 `IllegalArgumentException`

**测试标准**:

- 创建 JUnit 测试 `RefundTest.java`
- 可以创建 Refund 对象
- amount 验证正常
- 只能为 Succeeded 状态的 Payment 创建 Refund

**开始条件**: Task 4.8 完成

**结束条件**: Refund 构造函数验证通过所有测试

---

### Task 4.10: 实现订阅计费工作流测试

**目标**: 创建完整的订阅计费工作流集成测试

- [x] 创建 JUnit 测试类 `SubscriptionBillingWorkflowTest.java`
- [x] 测试完整流程：
  1. 创建 Subscription（状态：Trial）
  2. 创建 Payment（状态：Pending）
  3. Payment 成功（状态：Succeeded）
  4. Subscription 计费成功（状态：Active，nextBillingAt 更新）
  5. 创建新的 Payment（状态：Pending）
  6. Payment 失败（状态：Failed）
  7. Subscription 计费失败（状态：PastDue）
  8. 宽限期到期（状态：Suspended）
- [x] 测试退款流程：
  1. 创建成功的 Payment
  2. 发起退款（状态：Refunding，创建 Refund）
  3. 完成退款（Refund.processedAt 设置）

**测试标准**:

- 所有状态转换正确
- 守卫条件正确执行
- 完整流程测试通过

**开始条件**: Task 4.9 完成

**结束条件**: 订阅计费工作流测试全部通过

---

## 阶段 5: OCL 约束验证实现 (12 个任务)

### Task 5.1: 实现 SeatsNotExceeded 约束验证

**目标**: 课程容量约束

- [x] 在 `Course.java` 中添加方法：`public boolean validateSeatsNotExceeded()`
- [x] 统计 `Active` 状态的 Enrollment 数量
- [x] 返回 `activeCount <= capacity`
- [x] 在 `enroll()` 方法中调用验证（已在 Task 2.8 中实现）
- [x] 添加测试用例

**测试标准**:

- 在 `CourseTest.java` 中添加测试
- 满员课程验证失败
- 未满员课程验证成功

**开始条件**: Task 4.10 完成

**结束条件**: SeatsNotExceeded 约束验证通过测试

---

### Task 5.2: 实现 EnrollmentOnlyAfterPublish 约束验证

**目标**: 选课前提约束

- [x] 在 `Enrollment.java` 的构造函数中添加验证
- [x] 检查 `course.getStatus() != Course.Status.Draft`
- [x] 验证失败抛出 `IllegalArgumentException`
- [x] 添加测试用例

**测试标准**:

- 在 `EnrollmentTest.java` 中添加测试
- Draft 状态的课程无法创建 Enrollment
- 其他状态的课程可以创建 Enrollment

**开始条件**: Task 5.1 完成

**结束条件**: EnrollmentOnlyAfterPublish 约束验证通过测试

---

### Task 5.3: 实现 CourseStartRequiresStudent 约束验证

**目标**: 开课学生数约束

- [x] 在 `Course::startCourse()` 中已经实现了守卫条件（Task 2.9）
- [x] 添加独立的验证方法：`public boolean validateHasActiveStudents()`
- [x] 添加测试用例验证约束

**测试标准**:

- 在 `CourseTest.java` 中添加测试
- 无学生的课程无法开课
- 有学生的课程可以开课

**开始条件**: Task 5.2 完成

**结束条件**: CourseStartRequiresStudent 约束验证通过测试

---

### Task 5.4: 实现 SubmissionBeforeDeadline 约束验证

**目标**: 提交时间约束

- [x] 在 `Submission::submit()` 中已经实现了守卫条件（Task 3.4）
- [x] 添加独立的验证方法：`public boolean validateNotOverdue()`
- [x] 添加测试用例验证约束

**测试标准**:

- 在 `SubmissionTest.java` 中添加测试
- 超过截止时间的提交被拒绝
- 截止时间前的提交成功

**开始条件**: Task 5.3 完成

**结束条件**: SubmissionBeforeDeadline 约束验证通过测试

---

### Task 5.5: 实现 SubmissionVersionMonotonic 约束验证

**目标**: 版本单调性约束

- [x] 在 Task 3.4 中已经实现了版本管理
- [x] 添加验证方法：`public boolean validateVersionMonotonic()`
- [x] 检查当前 `version` 等于该学生的提交数
- [x] 添加测试用例

**测试标准**:

- 在 `SubmissionTest.java` 中添加测试
- 版本号单调递增
- 不同学生的版本号独立

**开始条件**: Task 5.4 完成

**结束条件**: SubmissionVersionMonotonic 约束验证通过测试

---

### Task 5.6: 实现 GradeWithinRange 约束验证

**目标**: 成绩范围约束

- [ ] 在 `Grade.java` 的构造函数和 setter 中添加验证（已在 Task 3.2 中实现）
- [x] 检查 `0 <= score <= submission.getAssignment().getMaxScore()`
- [x] 验证失败抛出异常
- [x] 添加测试用例

**测试标准**:

- 在 `GradeTest.java` 中添加测试
- 超出范围的分数被拒绝
- 有效范围的分数接受

**开始条件**: Task 5.5 完成

**结束条件**: GradeWithinRange 约束验证通过测试

---

### Task 5.7: 实现 AtMostOneGradePerSubmission 约束验证

**目标**: 提交评分唯一性

- [x] 在 `Submission::grade()` 中已经检查了 Grade 的存在（Task 3.8）
- [x] 添加验证方法：`public boolean validateGradeUniqueness()`
- [x] 确保只有一个 Grade 对象（`submissionGrade != null` 且唯一）
- [x] 添加测试用例

**测试标准**:

- 在 `SubmissionTest.java` 中添加测试
- 一个提交只能有一个 Grade
- 尝试创建多个 Grade 失败（通过 grade() 方法逻辑保证）

**开始条件**: Task 5.6 完成

**结束条件**: AtMostOneGradePerSubmission 约束验证通过测试

---

### Task 5.8: 实现 UniqueCourseCategories 约束验证

**目标**: 课程分类唯一性

- [x] 在 `Course.java` 中添加方法：`public boolean validateUniqueCategories()`
- [x] 检查 `categories` 中没有重复的 `id`
- [x] 在 `addCategory()` 中调用验证（使用 Umple 生成的方法）
- [x] 添加测试用例

**测试标准**:

- 在 `CourseTest.java` 中添加测试
- 重复的分类无法添加到课程
- 唯一的分类可以添加

**开始条件**: Task 5.7 完成

**结束条件**: UniqueCourseCategories 约束验证通过测试

---

### Task 5.9: 实现 RefundOnlyForSucceededPayment 约束验证

**目标**: 退款前提约束

- [x] 在 Task 4.9 中已经在 Refund 构造函数中验证
- [x] 在 `Payment::initiateRefund()` 中也添加验证（已在 Task 4.8 中实现）
- [x] 添加测试用例

**测试标准**:

- 在 `PaymentTest.java` 和 `RefundTest.java` 中添加测试
- 只有成功的支付可以退款
- 失败或待处理的支付无法退款

**开始条件**: Task 5.8 完成

**结束条件**: RefundOnlyForSucceededPayment 约束验证通过测试

---

### Task 5.10: 实现 SubscriptionBillingConsistency 约束验证

**目标**: 订阅计费一致性

- [x] 在 `Subscription.java` 中添加方法：`public boolean validateBillingConsistency()`
- [x] 如果状态为 `Active`，检查 `nextBillingAt > startAt`
- [x] 添加测试用例

**测试标准**:

- 在 `SubscriptionTest.java` 中添加测试
- Active 状态的订阅 nextBillingAt 必须大于 startAt
- 其他状态无此约束

**开始条件**: Task 5.9 完成

**结束条件**: SubscriptionBillingConsistency 约束验证通过测试

---

### Task 5.11: 实现 PublishRequiresContent 约束验证

**目标**: 发布内容要求

- [x] 在 `Course::publish()` 中已经实现了守卫条件（Task 2.6）
- [x] 添加独立的验证方法：`public boolean validateHasMinimumContent()`
- [x] 添加测试用例

**测试标准**:

- 在 `CourseTest.java` 中添加测试
- 没有内容的课程无法发布
- 有最少内容的课程可以发布

**开始条件**: Task 5.10 完成

**结束条件**: PublishRequiresContent 约束验证通过测试

---

### Task 5.12: 实现 PaymentRefundStateTransition 约束验证

**目标**: 支付退款状态转换

- [x] 在 `Payment::initiateRefund()` 中已经实现了前置条件（Task 4.8）
- [x] 添加验证方法：`public boolean validateRefundTransition()`
- [x] 检查前置：`status == PaymentStatus.Succeeded`
- [x] 检查后置：`status == PaymentStatus.Refunding` 且 `refund != null`
- [x] 添加测试用例

**测试标准**:

- 在 `PaymentTest.java` 中添加测试
- 只有 Succeeded 状态的支付可以退款
- 退款后状态正确转换

**开始条件**: Task 5.11 完成

**结束条件**: PaymentRefundStateTransition 约束验证通过测试

---

## 阶段 6: Spring Boot 服务层实现 (15 个任务)

### Task 6.1: 创建 UserRepository 接口

**目标**: 创建用户模块的 JPA Repository

- [x] 创建 `com.olp.repository.UserRepository` 接口
- [x] 继承 `JpaRepository<User, String>`
- [x] 添加查询方法：
  - `Optional<User> findById(String id)`
  - `Optional<User> findByEmail(String email)`
- [x] 创建 `StudentRepository` 和 `InstructorRepository` 接口
- [x] 添加测试验证 Repository 功能

**测试标准**:

- Repository 接口可以编译
- 可以注入 Repository 到测试类
- 基本 CRUD 操作可用

**开始条件**: Task 5.12 完成

**结束条件**: UserRepository 创建并通过测试

---

### Task 6.2: 创建 UserService 实现

**目标**: 实现用户服务层业务逻辑

- [x] 创建 `com.olp.service.UserService` 接口
- [x] 创建 `com.olp.service.UserServiceImpl` 实现类
- [x] 注入 `UserRepository`
- [x] 实现方法：
  - `User createUser(String id, String name, String email)`
  - `Optional<User> findById(String id)`
  - `Optional<User> findByEmail(String email)`
- [x] 添加 `@Service` 注解
- [x] 添加业务验证逻辑

**测试标准**:

- 创建 JUnit 测试 `UserServiceTest.java`（使用 `@SpringBootTest`）
- Service 可以创建和查找用户
- 验证逻辑正确执行

**开始条件**: Task 6.1 完成

**结束条件**: UserService 实现并通过测试

---

### Task 6.3: 创建 UserController REST API

**目标**: 创建用户模块的 REST API 端点

- [x] 创建 `com.olp.controller.UserController` 类
- [x] 添加 `@RestController` 和 `@RequestMapping("/api/users")` 注解
- [x] 注入 `UserService`
- [x] 实现端点：
  - `GET /api/users/{id}` - 获取用户
  - `POST /api/users` - 创建用户
  - `GET /api/users/email/{email}` - 通过邮箱查找
- [x] 使用 `@GetMapping`, `@PostMapping` 注解
- [x] 返回 `ResponseEntity` 包装响应

**测试标准**:

- 创建集成测试 `UserControllerTest.java`（使用 `@WebMvcTest`）
- 所有端点可以访问
- 返回正确的 HTTP 状态码和 JSON 响应

**开始条件**: Task 6.2 完成

**结束条件**: UserController 创建并通过测试

---

### Task 6.4: 创建 CourseRepository 和 CourseService

**目标**: 实现课程模块的数据访问和业务逻辑层

- [x] 创建 `CourseRepository` 接口
- [x] 添加查询方法：
  - `List<Course> findByInstructorId(String instructorId)`
  - `List<Course> findByStatus(Course.Status status)`
- [x] 创建 `CourseService` 接口和实现
- [x] 实现业务方法：
  - `Course createCourse(String id, String title, int capacity, Instructor instructor)`
  - `Optional<Course> findById(String id)`
  - `List<Course> findByInstructor(String instructorId)`
  - `Course publishCourse(String courseId)`
  - `Course enrollStudent(String courseId, String studentId)`

**测试标准**:

- Repository 和 Service 可以编译
- 业务逻辑正确执行
- 通过单元测试

**开始条件**: Task 6.3 完成

**结束条件**: CourseRepository 和 CourseService 实现并通过测试

---

### Task 6.5: 创建 CourseController REST API

**目标**: 创建课程模块的 REST API

- [x] 创建 `CourseController` 类
- [x] 实现端点：
  - `GET /api/courses` - 获取所有课程
  - `GET /api/courses/{id}` - 获取课程详情
  - `POST /api/courses` - 创建课程
  - `POST /api/courses/{id}/publish` - 发布课程
  - `POST /api/courses/{id}/enroll` - 学生选课
  - `POST /api/courses/{id}/start` - 开始课程
- [x] 使用 DTO 类传输数据（可选）

**测试标准**:

- 创建集成测试
- 所有端点可以访问
- 返回正确的响应

**开始条件**: Task 6.4 完成

**结束条件**: CourseController 创建并通过测试

---

### Task 6.6: 创建 AssignmentRepository 和 AssignmentService

**目标**: 实现作业模块的数据访问和业务逻辑层

- [x] 创建 `AssignmentRepository` 接口
- [x] 创建 `SubmissionRepository` 接口
- [x] 创建 `GradeRepository` 接口
- [x] 创建 `AssignmentService` 实现
- [x] 实现作业和提交的业务逻辑

**测试标准**:

- Repository 和 Service 可以编译
- 通过单元测试

**开始条件**: Task 6.5 完成

**结束条件**: Assignment 模块 Repository 和 Service 实现

---

### Task 6.7: 创建 AssignmentController REST API

**目标**: 创建作业模块的 REST API

- [x] 创建 `AssignmentController` 类
- [x] 实现端点：
  - `GET /api/assignments/{id}` - 获取作业
  - `POST /api/assignments/{id}/submit` - 提交作业
  - `POST /api/submissions/{id}/grade` - 评分
- [x] 处理状态机转换

**测试标准**:

- 创建集成测试
- 所有端点可以访问

**开始条件**: Task 6.6 完成

**结束条件**: AssignmentController 创建并通过测试

---

### Task 6.8: 创建 PaymentRepository 和 PaymentService

**目标**: 实现支付模块的数据访问和业务逻辑层

- [x] 创建 `PaymentRepository` 接口
- [x] 创建 `SubscriptionRepository` 接口
- [x] 创建 `RefundRepository` 接口
- [x] 创建 `PaymentService` 实现
- [x] 实现支付和订阅的业务逻辑

**测试标准**:

- Repository 和 Service 可以编译
- 通过单元测试

**开始条件**: Task 6.7 完成

**结束条件**: Payment 模块 Repository 和 Service 实现

---

### Task 6.9: 创建 PaymentController REST API

**目标**: 创建支付模块的 REST API

- [x] 创建 `PaymentController` 类
- [x] 实现端点：
  - `POST /api/subscriptions` - 创建订阅
  - `POST /api/payments` - 创建支付
  - `POST /api/payments/{id}/refund` - 退款
- [x] 处理支付状态转换

**测试标准**:

- 创建集成测试
- 所有端点可以访问

**开始条件**: Task 6.8 完成

**结束条件**: PaymentController 创建并通过测试

---

### Task 6.10: 添加全局异常处理

**目标**: 实现统一的异常处理机制

- [x] 创建 `@ControllerAdvice` 类
- [x] 处理 `IllegalArgumentException`
- [x] 处理 `EntityNotFoundException`
- [x] 返回统一的错误响应格式
- [x] 添加适当的 HTTP 状态码

**测试标准**:

- 异常被正确捕获和处理
- 返回统一的错误格式
- 通过测试验证

**开始条件**: Task 6.9 完成

**结束条件**: 全局异常处理实现并通过测试

---

### Task 6.11: 添加 API 文档（Swagger/OpenAPI）

**目标**: 生成 API 文档

- [ ] 添加 Swagger/OpenAPI 依赖
- [ ] 配置 Swagger
- [ ] 为所有 Controller 添加 API 文档注解
- [ ] 生成 API 文档页面

**测试标准**:

- Swagger UI 可以访问：http://localhost:8080/swagger-ui.html
- 所有 API 端点有文档说明

**开始条件**: Task 6.10 完成

**结束条件**: API 文档生成并可访问

---

### Task 6.12: 创建数据库初始化脚本

**目标**: 创建测试数据初始化脚本

- [ ] 创建 `data.sql` 文件
- [ ] 添加示例数据：
  - 创建测试用户（Student, Instructor）
  - 创建测试课程
  - 创建测试作业
- [ ] 配置 Spring Boot 在启动时执行脚本

**测试标准**:

- 应用启动时数据正确初始化
- 可以通过 API 查询到测试数据

**开始条件**: Task 6.11 完成

**结束条件**: 数据库初始化脚本创建并验证

---

### Task 6.13: 创建集成测试套件

**目标**: 创建完整的系统集成测试

- [ ] 创建 `SystemIntegrationTest.java`
- [ ] 使用 `@SpringBootTest` 和 `@AutoConfigureMockMvc`
- [ ] 测试完整业务流程：
  1. 创建用户
  2. 创建课程并发布
  3. 学生选课
  4. 课程开课
  5. 创建作业
  6. 学生提交作业
  7. 教师评分
- [ ] 测试支付流程

**测试标准**:

- 所有集成测试通过
- 业务流程完整无误

**开始条件**: Task 6.12 完成

**结束条件**: 集成测试全部通过

---

### Task 6.14: 性能测试和优化

**目标**: 验证系统性能

- [ ] 创建性能测试类
- [ ] 测试大量数据操作（1000+ 用户，100+ 课程）
- [ ] 测试 API 响应时间
- [ ] 优化慢查询（如有）

**测试标准**:

- 系统可以处理合理数量的数据
- API 响应时间在可接受范围内（< 500ms）

**开始条件**: Task 6.13 完成

**结束条件**: 性能测试通过

---

### Task 6.15: 最终验收和文档更新

**目标**: 完成 MVP 验收

- [ ] 运行所有测试套件
- [ ] 更新 README.md 文档
- [ ] 创建 API 使用示例
- [ ] 准备部署说明

**测试标准**:

- 所有测试通过（100%）
- 文档完整清晰
- 系统可以正常运行

**开始条件**: Task 6.14 完成

**结束条件**: MVP 验收通过，准备交付

---

## 阶段 7: 系统集成与测试 (原阶段 6 的部分任务已整合到阶段 6)

### Task 6.1: 创建主程序入口

**目标**: 创建可运行的主程序

- [ ] 创建 `com.olp.Main.java` 类
- [ ] 实现 `main(String[] args)` 方法
- [ ] 创建示例数据：
  - 创建 Instructor、Student、Course 等对象
  - 演示基本功能（发布课程、选课、提交作业等）
- [ ] 输出运行结果

**测试标准**:

- 程序可以运行：`java com.olp.Main`
- 无运行时异常
- 输出符合预期

**开始条件**: Task 5.12 完成

**结束条件**: 主程序可以正常运行

---

### Task 6.2: 创建集成测试套件

**目标**: 创建完整的系统集成测试

- [ ] 创建 JUnit 测试类 `SystemIntegrationTest.java`
- [ ] 测试完整业务流程：
  1. 创建用户（Instructor、Student）
  2. 创建课程并发布
  3. 学生选课
  4. 课程开课
  5. 创建作业
  6. 学生提交作业
  7. 教师评分
  8. 课程结课
- [ ] 测试支付流程：
  1. 创建订阅
  2. 处理支付
  3. 退款流程

**测试标准**:

- 所有集成测试通过
- 业务流程完整无误

**开始条件**: Task 6.1 完成

**结束条件**: 集成测试全部通过

---

### Task 6.3: 性能测试基础

**目标**: 验证系统基本性能

- [ ] 创建性能测试类 `PerformanceTest.java`
- [ ] 测试大量对象创建（1000 个 Student、100 个 Course）
- [ ] 测试关联查询性能（查找学生的所有选课）
- [ ] 记录执行时间

**测试标准**:

- 系统可以处理合理数量的对象（1000+）
- 查询操作在可接受时间内完成（< 1 秒）

**开始条件**: Task 6.2 完成

**结束条件**: 性能测试通过

---

### Task 6.4: 异常处理测试

**目标**: 验证异常处理正确性

- [ ] 创建异常测试类 `ExceptionHandlingTest.java`
- [ ] 测试所有验证失败场景：
  - 无效参数（null、空字符串、负数等）
  - 状态转换失败（守卫条件不满足）
  - 关联约束违反
- [ ] 验证异常类型和消息

**测试标准**:

- 所有异常场景正确抛出异常
- 异常类型正确（IllegalArgumentException）
- 异常消息清晰

**开始条件**: Task 6.3 完成

**结束条件**: 异常处理测试通过

---

### Task 6.5: 边界条件测试

**目标**: 验证边界条件处理

- [ ] 创建边界测试类 `BoundaryConditionTest.java`
- [ ] 测试边界值：
  - capacity = 1（最小容量）
  - maxScore = 1（最小分数）
  - 日期边界（今天、昨天、明天）
  - 空集合操作
- [ ] 验证边界情况下的行为

**测试标准**:

- 所有边界条件正确处理
- 无数组越界、空指针异常

**开始条件**: Task 6.4 完成

**结束条件**: 边界条件测试通过

---

### Task 6.6: 文档更新

**目标**: 更新项目文档

- [ ] 创建或更新 `README.md`
  - 项目概述
  - 构建说明
  - 运行说明
  - 测试说明
- [ ] 更新 `architecture.md`（如有需要）
- [ ] 添加代码注释（关键方法）

**测试标准**:

- README 包含完整的项目说明
- 文档清晰易懂

**开始条件**: Task 6.5 完成

**结束条件**: 文档更新完成

---

### Task 6.7: 代码审查与重构

**目标**: 代码质量提升

- [ ] 运行代码检查工具（Checkstyle、PMD 等）
- [ ] 修复代码风格问题
- [ ] 重构重复代码
- [ ] 优化性能瓶颈（如有）

**测试标准**:

- 代码符合 Java 编码规范
- 无严重代码质量问题
- 所有测试仍然通过

**开始条件**: Task 6.6 完成

**结束条件**: 代码审查通过

---

### Task 6.8: 最终验收测试

**目标**: 全面验证 MVP 功能

- [ ] 运行所有测试套件
- [ ] 验证所有功能点：
  - 用户管理 ✅
  - 课程管理 ✅
  - 作业系统 ✅
  - 支付系统 ✅
  - OCL 约束 ✅
- [ ] 创建测试报告

**测试标准**:

- 所有测试通过（100%）
- 所有功能点验证通过
- 系统可以正常运行

**开始条件**: Task 6.7 完成

**结束条件**: MVP 验收通过

---

## 任务统计

- **总任务数**: 80 个
- **阶段 0**: 7 个任务（Spring Boot 环境准备）
- **阶段 1**: 4 个任务（用户模块）
- **阶段 2**: 10 个任务（课程模块）
- **阶段 3**: 10 个任务（作业系统）
- **阶段 4**: 10 个任务（支付系统）
- **阶段 5**: 12 个任务（OCL 约束）
- **阶段 6**: 15 个任务（Spring Boot 服务层实现）
- **阶段 7**: 12 个任务（系统集成与测试）

---

## 使用说明

1. **按顺序执行**: 任务按依赖关系排列，必须按顺序完成
2. **测试驱动**: 每个任务都有明确的测试标准，完成测试后再进行下一个任务
3. **小步迭代**: 每个任务都很小，专注于一个问题
4. **持续验证**: 每完成一个任务就运行测试，确保功能正确

---

**文档版本**: 1.0  
**最后更新**: 2025-11-10  
**维护者**: Online Learning Platform Development Team
