# 在线学习平台（Online Learning Platform）架构设计文档

## 目录

1. [项目概述](#项目概述)
2. [文件与文件夹结构](#文件与文件夹结构)
3. [核心类与属性](#核心类与属性)
4. [关联关系与多重度](#关联关系与多重度)
5. [状态机设计](#状态机设计)
6. [OCL 约束规范](#ocl约束规范)
7. [方法签名与职责](#方法签名与职责)
8. [实现指南](#实现指南)

---

## 项目概述

本项目是一个完整的**在线学习平台系统**，支持课程管理、作业提交、成绩评定、订阅付费等核心功能。系统采用面向对象设计，包含清晰的类层次结构、状态机模型和业务约束规则。

### 技术栈

- **语言**: C++17
- **建模工具**: UMLe (用于代码框架生成)
- **构建系统**: CMake
- **设计方法**: UML 类图、状态图、OCL 约束

### 核心功能模块

- **用户管理** (`olp.user`): 学生、教师、管理员角色
- **课程管理** (`olp.course`): 课程、选课、课时、视频内容、分类
- **作业系统** (`olp.assignment`): 作业、提交、评分
- **支付系统** (`olp.payment`): 支付、订阅、退款

---

## 文件与文件夹结构

```
Cpp_online_platform/
│
├── architecture.md              # 本架构文档
├── task.md                      # 详细实现任务列表
├── CMakeLists.txt              # CMake构建配置
├── model_Model.h               # Umple生成的主头文件（包含所有类声明）
├── model_Main.cpp              # 程序入口
│
├── [用户管理模块 - olp.user]
│   ├── User.h / User.cpp              # 抽象用户基类
│   ├── Student.h / Student.cpp        # 学生类
│   ├── Instructor.h / Instructor.cpp  # 教师类
│   └── Administrator.h / Administrator.cpp  # 管理员类
│
├── [课程管理模块 - olp.course]
│   ├── Course.h / Course.cpp                 # 课程类（含状态机）
│   ├── Enrollment.h / Enrollment.cpp         # 选课关联类
│   ├── Lesson.h / Lesson.cpp                 # 课时类
│   ├── VideoContent.h / VideoContent.cpp     # 视频内容类
│   └── CourseCategory.h / CourseCategory.cpp # 课程分类类
│
├── [作业系统模块 - olp.assignment]
│   ├── Assignment.h / Assignment.cpp         # 作业类
│   ├── Submission.h / Submission.cpp         # 提交类（含状态机）
│   └── Grade.h / Grade.cpp                   # 成绩类
│
└── [支付系统模块 - olp.payment]
    ├── Payment.h / Payment.cpp               # 支付类
    ├── Subscription.h / Subscription.cpp     # 订阅类（含状态机）
    └── Refund.h / Refund.cpp                 # 退款类
```

### 文件职责说明

#### 用户管理模块 (olp.user)

| 文件                  | 职责                                              |
| --------------------- | ------------------------------------------------- |
| `User.h/cpp`          | 定义抽象用户基类，包含 id、name、email 等通用属性 |
| `Student.h/cpp`       | 学生类，管理选课记录、作业提交、订阅信息          |
| `Instructor.h/cpp`    | 教师类，管理所教授的课程列表                      |
| `Administrator.h/cpp` | 管理员类，负责系统管理功能                        |

#### 课程管理模块 (olp.course)

| 文件                   | 职责                                           |
| ---------------------- | ---------------------------------------------- |
| `Course.h/cpp`         | 核心课程实体，包含发布、选课、开课、结课状态机 |
| `Enrollment.h/cpp`     | 关联类，连接学生和课程，记录选课状态和时间     |
| `Lesson.h/cpp`         | 课时实体，属于某课程，按 orderIndex 排序       |
| `VideoContent.h/cpp`   | 视频内容，关联到课时，记录 URL 和时长          |
| `CourseCategory.h/cpp` | 课程分类，支持多对多关系                       |

#### 作业系统模块 (olp.assignment)

| 文件               | 职责                                   |
| ------------------ | -------------------------------------- |
| `Assignment.h/cpp` | 作业实体，记录标题、截止时间、满分     |
| `Submission.h/cpp` | 提交实体，含完整的提交-检查-评分状态机 |
| `Grade.h/cpp`      | 成绩实体，记录分数和反馈               |

#### 支付系统模块 (olp.payment)

| 文件                 | 职责                                     |
| -------------------- | ---------------------------------------- |
| `Payment.h/cpp`      | 支付记录，包含金额、状态、支付时间       |
| `Subscription.h/cpp` | 订阅管理，含试用、活跃、逾期、暂停状态机 |
| `Refund.h/cpp`       | 退款记录，关联支付                       |

#### 核心配置文件

| 文件             | 职责                                           |
| ---------------- | ---------------------------------------------- |
| `model_Model.h`  | Umple 生成的统一头文件，包含所有枚举和类型定义 |
| `model_Main.cpp` | 程序入口，用于测试和演示                       |
| `CMakeLists.txt` | CMake 构建配置，管理编译依赖                   |

---

## 核心类与属性

### 1. 用户管理包 (olp.user)

#### User (抽象基类)

```
Class: User
Type: Abstract
Attributes:
  - id: String
  - name: String
  - email: String
```

#### Student (继承 User)

```
Class: Student
Extends: User
Attributes:
  (继承自User)
Associations:
  - enrollments: Enrollment[*]
  - submissions: Submission[*]
  - (可选) subscription: Subscription[0..1]
```

#### Instructor (继承 User)

```
Class: Instructor
Extends: User
Attributes:
  (继承自User)
Associations:
  - courses: Course[*]
```

#### Administrator (继承 User)

```
Class: Administrator
Extends: User
Attributes:
  (继承自User)
Methods:
  - 系统管理方法（待定义）
```

---

### 2. 课程管理包 (olp.course)

#### Course

```
Class: Course
Attributes:
  - id: String
  - title: String
  - capacity: Integer           # 课程容量
  - status: CourseStatus        # 状态机状态
Enumerations:
  CourseStatus: {Draft, Published, EnrollmentOpen, Waitlisted, InProgress, Completed, Cancelled}
Associations:
  - instructor: Instructor[1]
  - lessons: Lesson[1..*]       # Composition
  - assignments: Assignment[1..*]
  - enrollments: Enrollment[*]  # 通过关联类
  - categories: CourseCategory[*]  # 多对多
```

#### Enrollment (关联类)

```
Class: Enrollment
Type: Association Class (Student ↔ Course)
Attributes:
  - id: String
  - status: EnrollmentStatus
  - enrolledAt: DateTime
Enumerations:
  EnrollmentStatus: {Active, Waitlisted, Dropped, Completed}
Associations:
  - student: Student[1]
  - course: Course[1]
```

#### Lesson

```
Class: Lesson
Attributes:
  - id: String
  - title: String
  - orderIndex: Integer         # 课时顺序
Associations:
  - course: Course[1]           # Composition（部分-整体）
  - videos: VideoContent[0..*]  # Composition
```

#### VideoContent

```
Class: VideoContent
Attributes:
  - id: String
  - url: String
  - durationSec: Integer
Associations:
  - lesson: Lesson[1]           # Composition
```

#### CourseCategory

```
Class: CourseCategory
Attributes:
  - id: String
  - name: String
Associations:
  - courses: Course[*]          # 多对多
```

---

### 3. 作业系统包 (olp.assignment)

#### Assignment

```
Class: Assignment
Attributes:
  - id: String
  - title: String
  - deadline: DateTime
  - maxScore: Integer
Associations:
  - course: Course[1]
  - submissions: Submission[*]
```

#### Submission

```
Class: Submission
Attributes:
  - id: String
  - submittedAt: DateTime
  - status: SubmissionStatus    # 状态机状态
  - version: Integer            # 提交版本号
  - checkPassed: Boolean        # 自动检查是否通过
Enumerations:
  SubmissionStatus: {Created, Submitted, UnderCheck, Grading, Graded, Returned, ResubmissionRequested}
Associations:
  - assignment: Assignment[1]
  - student: Student[1]
  - grade: Grade[0..1]          # 最多一个成绩
```

#### Grade

```
Class: Grade
Attributes:
  - id: String
  - score: Double
  - feedback: String
Associations:
  - submission: Submission[1]
```

---

### 4. 支付系统包 (olp.payment)

#### Payment

```
Class: Payment
Attributes:
  - id: String
  - amount: Money (Double)
  - status: PaymentStatus
  - paidAt: DateTime
Enumerations:
  PaymentStatus: {Pending, Succeeded, Failed, Refunding, Refunded}
Associations:
  - subscription: Subscription[1]
  - refund: Refund[0..1]
```

#### Subscription

```
Class: Subscription
Attributes:
  - id: String
  - plan: PlanType
  - status: SubscriptionStatus  # 状态机状态
  - startAt: DateTime
  - nextBillingAt: DateTime
Enumerations:
  PlanType: {Trial, Monthly, Annual}
  SubscriptionStatus: {Trial, Active, PastDue, Suspended, Cancelled}
Associations:
  - payments: Payment[*]
  - (可选) student: Student[1]
```

#### Refund

```
Class: Refund
Attributes:
  - id: String
  - amount: Money (Double)
  - requestedAt: DateTime
  - processedAt: DateTime
Associations:
  - payment: Payment[1]
```

---

## 关联关系与多重度

### 关联表

| 关联      | 端点 1                          | 端点 2                          | 类型                              | 说明                   |
| --------- | ------------------------------- | ------------------------------- | --------------------------------- | ---------------------- |
| 学生选课  | Student (student) `1`           | Course (course) `*`             | Association Class: **Enrollment** | 通过 Enrollment 关联类 |
| 课程-课时 | Course (course) `1`             | Lesson (lessons) `1..*`         | **Composition**                   | 课时是课程的组成部分   |
| 课时-视频 | Lesson (lesson) `1`             | VideoContent (videos) `0..*`    | **Composition**                   | 视频是课时的组成部分   |
| 课程-作业 | Course (course) `1`             | Assignment (assignments) `1..*` | Association                       | 一门课程有多个作业     |
| 作业-提交 | Assignment (assignment) `1`     | Submission (submissions) `*`    | Association                       | 一个作业有多个提交     |
| 提交-成绩 | Submission (submission) `1`     | Grade (grade) `0..1`            | Association                       | 一个提交最多一个成绩   |
| 学生-提交 | Student (student) `1`           | Submission (submissions) `*`    | Association                       | 学生创建多个提交       |
| 教师-课程 | Instructor (instructor) `1`     | Course (courses) `*`            | Association                       | 一个教师教授多门课程   |
| 课程-分类 | Course (courses) `*`            | CourseCategory (categories) `*` | **Many-to-Many**                  | 多对多关系             |
| 订阅-支付 | Subscription (subscription) `1` | Payment (payments) `*`          | Association                       | 一个订阅有多次支付     |
| 支付-退款 | Payment (payment) `1`           | Refund (refund) `0..1`          | Association                       | 一次支付最多一次退款   |

### 关联端角色名（Role Names）

在 UML 类图和 VP 工具中，每个关联端都应设置角色名，保持与 OCL 约束一致：

- **Student - Course**: `student` ↔ `course`
- **Course - Lesson**: `course` ↔ `lessons`
- **Lesson - VideoContent**: `lesson` ↔ `videos`
- **Assignment - Submission**: `assignment` ↔ `submissions`
- **Submission - Grade**: `submission` ↔ `grade`
- **Instructor - Course**: `instructor` ↔ `courses`
- **Subscription - Payment**: `subscription` ↔ `payments`

---

## 状态机设计

### 1. 课程生命周期状态机 (CourseLifecycle)

**上下文类**: `Course`

#### 状态集合

```
Draft (草稿)
  ↓ publish()
Published (已发布)
  ↓ openEnrollment()
EnrollmentOpen (可选课) ↔ Waitlisted (候补)
  ↓ startCourse()
InProgress (进行中)
  ↓ complete()
Completed (已结课)

任何状态 → cancel(reason) → Cancelled (已取消)
```

#### 状态描述

| 状态               | 说明                         | 允许操作        |
| ------------------ | ---------------------------- | --------------- |
| **Draft**          | 课程创建初始状态，内容编辑中 | 添加课时、作业  |
| **Published**      | 课程已发布，但未开放选课     | 开放选课        |
| **EnrollmentOpen** | 选课开放，学生可报名         | 创建 Enrollment |
| **Waitlisted**     | 课程满员，后续报名进入候补   | 候补名单管理    |
| **InProgress**     | 课程进行中，学生学习阶段     | 作业提交、评分  |
| **Completed**      | 课程已结课，归档状态         | 查看历史        |
| **Cancelled**      | 课程已取消                   | 仅查看          |

#### 事件与转换

##### 1) publish()

- **转换**: `Draft → Published`
- **守卫条件**:
  - `lessons.size() >= 1` (至少一个课时)
  - `assignments.size() >= 1` (至少一个作业)
- **效果**:
  - `status := Published`
  - 记录发布日期
  - 通知 instructor

##### 2) openEnrollment()

- **转换**: `Published → EnrollmentOpen`
- **守卫条件**:
  - `capacity > 0`
- **效果**:
  - `status := EnrollmentOpen`
  - 允许创建 Enrollment

##### 3) startCourse()

- **转换**: `EnrollmentOpen | Waitlisted → InProgress`
- **守卫条件**:
  - `enrollments.select(e|e.status=Active).size() >= 1` (至少一名学生)
- **效果**:
  - `status := InProgress`
  - 记录开课时间

##### 4) complete()

- **转换**: `InProgress → Completed`
- **守卫条件**:
  - 无硬性要求
  - (可选) 所有作业已截止
- **效果**:
  - `status := Completed`
  - 触发归档流程

##### 5) cancel(reason: String)

- **转换**: `任何状态 → Cancelled`
- **守卫条件**:
  - (可选) 不能在 Completed 后取消
- **效果**:
  - `status := Cancelled`
  - 记录取消原因
  - 不删除实体

#### 与其他类的交互

- **EnrollmentOpen 阶段**: 允许创建 Enrollment，当 Active 数量达到 capacity 时，新增报名的 Enrollment.status 设为 Waitlisted
- **InProgress 阶段**: 学生可提交作业，教师可评分，但不影响课程主状态

---

### 2. 提交流程状态机 (SubmissionWorkflow)

**上下文类**: `Submission`

#### 状态集合

```
Created (草稿)
  ↓ submit()
Submitted (已提交)
  ↓ startAutoChecks()
UnderCheck (自动检查中)
  ↓ checksCompleted()
  ├─ 通过 → Submitted (标记checkPassed=true)
  └─ 失败 → Returned

Submitted (检查通过)
  ↓ startGrading()
Grading (评分中)
  ↓ grade(score, feedback)
Graded (已评分)
  ↓ requestResubmission()
ResubmissionRequested (要求重交) → 回到Created
```

#### 状态描述

| 状态                      | 说明                    | 允许操作     |
| ------------------------- | ----------------------- | ------------ |
| **Created**               | 草稿状态，学生编辑中    | 编辑内容     |
| **Submitted**             | 已提交，等待检查        | 触发自动检查 |
| **UnderCheck**            | 自动检查中（查重/编译） | 等待检查结果 |
| **Grading**               | 教师评分中              | 教师打分     |
| **Graded**                | 已评分                  | 查看成绩     |
| **Returned**              | 检查失败，退回学生      | 查看原因     |
| **ResubmissionRequested** | 要求重新提交            | 创建新版本   |

#### 事件与转换

##### 1) submit()

- **转换**: `Created → Submitted`
- **守卫条件**:
  - `now <= assignment.deadline` (未超截止时间)
- **效果**:
  - `submittedAt := now`
  - `version := version + 1`
  - `status := Submitted`

##### 2) startAutoChecks()

- **转换**: `Submitted → UnderCheck`
- **守卫条件**: 无
- **效果**:
  - `status := UnderCheck`
  - 触发后台检测（查重、编译等）

##### 3) checksCompleted(flagged: Boolean, compiledOk: Boolean)

- **转换**:
  - `UnderCheck → Submitted` (通过)
  - `UnderCheck → Returned` (失败)
- **守卫条件**: 无
- **效果**:
  - 若 `flagged=false ∧ compiledOk=true`:
    - `checkPassed := true`
    - `status := Submitted`
  - 否则:
    - `checkPassed := false`
    - `status := Returned`
    - 附加失败说明

##### 4) startGrading()

- **转换**: `Submitted → Grading`
- **守卫条件**:
  - `checkPassed = true`
- **效果**:
  - `status := Grading`

##### 5) grade(score: Double, feedback: String)

- **转换**: `Grading → Graded`
- **守卫条件**:
  - `0 <= score <= assignment.maxScore`
- **效果**:
  - 若 grade 不存在则创建 Grade 对象
  - `grade.score := score`
  - `grade.feedback := feedback`
  - `status := Graded`

##### 6) requestResubmission()

- **转换**: `Graded | Returned → ResubmissionRequested`
- **守卫条件**:
  - 课程政策允许（例如：允许一次重交）
  - `now <= assignment.deadline`
- **效果**:
  - `status := ResubmissionRequested`
  - 学生可创建新版本提交

#### 与其他类的交互

- **与 Assignment**: 读取 deadline/maxScore 作为守卫条件
- **与 Grade**: 在 grade()时创建或更新 Grade 对象，保证"每次提交最多一个 Grade"
- **版本管理**: 每次 submit()时 version 递增，同一学生对同一作业可有多个版本

---

### 3. 订阅计费状态机 (SubscriptionBilling)

**上下文类**: `Subscription`

#### 状态集合

```
Trial (试用期)
  ↓ chargeSuccess()
Active (活跃)
  ↓ chargeFail()
PastDue (逾期宽限)
  ↓ graceExpire()
Suspended (暂停)

任何状态 → cancel() → Cancelled (已取消)
```

#### 状态描述

| 状态          | 说明             | 允许操作           |
| ------------- | ---------------- | ------------------ |
| **Trial**     | 试用期           | 访问有限功能       |
| **Active**    | 正常活跃，已付费 | 完整功能访问       |
| **PastDue**   | 扣款失败，宽限期 | 有限功能，提示续费 |
| **Suspended** | 宽限期结束，暂停 | 功能锁定           |
| **Cancelled** | 用户取消订阅     | 仅查看历史         |

#### 事件与转换

##### 1) chargeSuccess()

- **转换**: `Trial | Active | PastDue → Active`
- **守卫条件**:
  - 最近一笔 Payment.status = Succeeded
- **效果**:
  - `status := Active`
  - `nextBillingAt := nextBillingAt + plan周期`
    - Monthly: +30 天
    - Annual: +365 天

##### 2) chargeFail()

- **转换**: `Active → PastDue`
- **守卫条件**:
  - 最近一笔 Payment.status = Failed
- **效果**:
  - `status := PastDue`
  - 记录失败时间

##### 3) graceExpire()

- **转换**: `PastDue → Suspended`
- **守卫条件**:
  - `now > 宽限到期时间` (例如：失败后 7 天)
- **效果**:
  - `status := Suspended`

##### 4) cancel()

- **转换**: `任何状态 → Cancelled`
- **守卫条件**: 无
- **效果**:
  - `status := Cancelled`

##### 5) refundRequested() (在 Payment 上)

- **转换**: 不改变 Subscription 状态
- **守卫条件**:
  - `Payment.status = Succeeded`
- **效果**:
  - `Payment.status := Refunding`
  - 创建 Refund 对象

##### 6) refundProcessed() (在 Payment 上)

- **转换**: `Payment.status: Refunding → Refunded`
- **守卫条件**: 无
- **效果**:
  - `Payment.status := Refunded`
  - `Refund.processedAt := now`

#### 与其他类的交互

- **与 Payment**: Subscription 状态由最近一次计费结果驱动
- **退款流程**: 退款绑定在 Payment/Refund 上，与 Subscription 主状态解耦，仅作为财务纠正
- **计费周期**: nextBillingAt 根据 plan 类型自动计算

---

## OCL 约束规范

### 约束列表

#### 1. SeatsNotExceeded (课程容量约束)

- **作用域**: Course
- **业务意图**: 报名的 Active 人数不能超过课程容量
- **依赖元素**: Course.capacity, Course.enrollments, Enrollment.status
- **约束描述**: enrollments 中 status=Active 的计数 ≤ capacity
- **OCL 形式**:

```ocl
context Course
inv SeatsNotExceeded:
  self.enrollments->select(e | e.status = EnrollmentStatus::Active)->size() <= self.capacity
```

#### 2. EnrollmentOnlyAfterPublish (选课前提约束)

- **作用域**: Enrollment
- **业务意图**: 课程未发布（Draft）时不得产生报名
- **依赖元素**: Enrollment.course.status
- **约束描述**: course.status ≠ Draft
- **OCL 形式**:

```ocl
context Enrollment
inv EnrollmentOnlyAfterPublish:
  self.course.status <> CourseStatus::Draft
```

#### 3. CourseStartRequiresStudent (开课学生数约束)

- **作用域**: Course
- **业务意图**: 至少一名有效学生才可开课
- **依赖元素**: Course.enrollments.status
- **约束描述**: Active 报名数 ≥ 1
- **OCL 形式**:

```ocl
context Course::startCourse()
pre: self.enrollments->exists(e | e.status = EnrollmentStatus::Active)
```

#### 4. SubmissionBeforeDeadline (提交时间约束)

- **作用域**: Submission
- **业务意图**: 提交时间不得晚于作业截止
- **依赖元素**: Submission.submittedAt, Submission.assignment.deadline
- **约束描述**: submittedAt ≤ assignment.deadline
- **OCL 形式**:

```ocl
context Submission::submit()
pre: getCurrentTime() <= self.assignment.deadline

context Submission
inv SubmissionBeforeDeadline:
  self.submittedAt <= self.assignment.deadline
```

#### 5. SubmissionVersionMonotonic (版本单调性约束)

- **作用域**: Submission
- **业务意图**: 同一学生对同一作业的版本号从 1 开始并单调递增
- **依赖元素**: Submission.version, Submission.student, Assignment.submissions
- **约束描述**: 当前提交的 version 等于该学生在该作业下现有提交数量
- **OCL 形式**:

```ocl
context Submission
inv SubmissionVersionMonotonic:
  self.version = self.assignment.submissions
    ->select(s | s.student = self.student)->size()
```

#### 6. GradeWithinRange (成绩范围约束)

- **作用域**: Grade
- **业务意图**: 分数在区间 [0, maxScore]
- **依赖元素**: Grade.score, Submission.assignment.maxScore, Grade.submission
- **约束描述**: 0 ≤ score ≤ maxScore
- **OCL 形式**:

```ocl
context Grade
inv GradeWithinRange:
  0 <= self.score and
  self.score <= self.submission.assignment.maxScore
```

#### 7. AtMostOneGradePerSubmission (提交评分唯一性)

- **作用域**: Submission
- **业务意图**: 一次提交最多一个评分对象
- **依赖元素**: Submission.grade (0..1)
- **约束描述**: grade 关联的基数 ≤ 1
- **OCL 形式**:

```ocl
context Submission
inv AtMostOneGradePerSubmission:
  self.grade->size() <= 1
```

#### 8. UniqueCourseCategories (课程分类唯一性)

- **作用域**: Course
- **业务意图**: 同一课程中类别去重
- **依赖元素**: Course.categories.id
- **约束描述**: categories 的 id 唯一
- **OCL 形式**:

```ocl
context Course
inv UniqueCourseCategories:
  self.categories->isUnique(c | c.id)
```

#### 9. RefundOnlyForSucceededPayment (退款前提约束)

- **作用域**: Refund
- **业务意图**: 只有成功支付才能退款
- **依赖元素**: Refund.payment.status
- **约束描述**: payment.status = Succeeded
- **OCL 形式**:

```ocl
context Refund
inv RefundOnlyForSucceededPayment:
  self.payment.status = PaymentStatus::Succeeded
```

#### 10. SubscriptionBillingConsistency (订阅计费一致性)

- **作用域**: Subscription
- **业务意图**: 活跃订阅必须拥有严格未来的下一计费时间
- **依赖元素**: Subscription.status, Subscription.nextBillingAt, Subscription.startAt
- **约束描述**: 若 status=Active，则 nextBillingAt > startAt
- **OCL 形式**:

```ocl
context Subscription
inv SubscriptionBillingConsistency:
  self.status = SubscriptionStatus::Active implies
    self.nextBillingAt > self.startAt
```

#### 11. PublishRequiresContent (发布内容完整性)

- **作用域**: Course
- **业务意图**: 发布前必须至少 1 个 Lesson 与 1 个 Assignment
- **依赖元素**: Course.lessons, Course.assignments
- **约束描述**: lessons.size ≥ 1 ∧ assignments.size ≥ 1
- **OCL 形式**:

```ocl
context Course::publish()
pre: self.lessons->size() >= 1 and
     self.assignments->size() >= 1
```

#### 12. PaymentRefundStateTransition (支付退款状态转换)

- **作用域**: Payment
- **业务意图**: 仅在成功支付上触发退款，并进入退款中状态
- **依赖元素**: Payment.status, Payment.refund
- **约束描述**:
  - **前置**: status = Succeeded
  - **后置**: status = Refunding ∧ 存在 Refund
- **OCL 形式**:

```ocl
context Payment::refund()
pre: self.status = PaymentStatus::Succeeded
post: self.status = PaymentStatus::Refunding and
      self.refund->size() = 1
```

---

## 方法签名与职责

### Course 类方法

| 方法签名                               | 状态转换                               | 职责                           |
| -------------------------------------- | -------------------------------------- | ------------------------------ |
| `bool publish()`                       | Draft → Published                      | 发布课程，需满足内容完整性约束 |
| `bool openEnrollment()`                | Published → EnrollmentOpen             | 开放选课，需 capacity > 0      |
| `bool startCourse()`                   | EnrollmentOpen/Waitlisted → InProgress | 开始课程，需至少 1 名学生      |
| `bool complete()`                      | InProgress → Completed                 | 结课，可选检查作业截止         |
| `bool cancel(String reason)`           | \* → Cancelled                         | 取消课程，记录原因             |
| `Enrollment* enroll(Student* student)` | -                                      | 创建选课记录，检查容量         |

### Submission 类方法

| 方法签名                     | 状态转换                                | 职责                            |
| ---------------------------- | --------------------------------------- | ------------------------------- |
| `bool submit()`              | Created → Submitted                     | 提交作业，检查截止时间          |
| `bool startAutoChecks()`     | Submitted → UnderCheck                  | 触发自动检查                    |
| `bool checksPass()`          | UnderCheck → Submitted                  | 检查通过，标记 checkPassed=true |
| `bool checksFail()`          | UnderCheck → Returned                   | 检查失败，附加说明              |
| `bool startGrading()`        | Submitted → Grading                     | 开始评分，需 checkPassed=true   |
| `bool grade()`               | Grading → Graded                        | 评分，创建/更新 Grade 对象      |
| `bool requestResubmission()` | Graded/Returned → ResubmissionRequested | 要求重交                        |

**注**: 原需求中的 `grade(score: Double, feedback: String)` 在当前 Umple 生成代码中简化为 `grade()`，需要在实现时添加参数。

### Subscription 类方法

| 方法签名               | 状态转换                      | 职责                         |
| ---------------------- | ----------------------------- | ---------------------------- |
| `bool chargeSuccess()` | Trial/Active/PastDue → Active | 扣款成功，更新 nextBillingAt |
| `bool chargeFail()`    | Active → PastDue              | 扣款失败，进入宽限期         |
| `bool graceExpire()`   | PastDue → Suspended           | 宽限期结束，暂停服务         |
| `bool cancel()`        | \* → Cancelled                | 取消订阅                     |

### Payment 类方法

| 方法签名                 | 状态转换              | 职责                              |
| ------------------------ | --------------------- | --------------------------------- |
| `bool refund()`          | Succeeded → Refunding | 发起退款，创建 Refund 对象        |
| `bool refundProcessed()` | Refunding → Refunded  | 退款完成，更新 Refund.processedAt |

---

## 实现指南

### 阶段 1: 基础类实现

1. **验证 Umple 生成的代码**

   - 检查所有类的属性 getter/setter
   - 验证关联关系是否正确
   - 确认枚举类型定义

2. **实现 OCL 约束验证**
   - 在 setter 方法中添加约束检查
   - 创建独立的验证方法（如`validateSeatsNotExceeded()`）
   - 使用异常处理机制报告约束违反

### 阶段 2: 状态机实现

1. **Course 状态机**

   - 实现所有事件方法的守卫条件
   - 添加状态转换的前置/后置检查
   - 实现与 Enrollment 的交互逻辑

2. **Submission 状态机**

   - 实现 checkPassed 标志的逻辑
   - 实现 Grade 对象的创建/更新
   - 处理版本号递增逻辑

3. **Subscription 状态机**
   - 实现 nextBillingAt 的计算
   - 实现 Payment 状态的检查
   - 实现宽限期到期检测

### 阶段 3: 业务逻辑完善

1. **选课逻辑**

   - 实现容量检查
   - 实现候补名单管理
   - 实现状态转换通知

2. **作业提交流程**

   - 实现自动检查模拟（查重、编译）
   - 实现版本管理
   - 实现重交逻辑

3. **订阅计费流程**
   - 实现计费周期计算
   - 实现宽限期计时
   - 实现退款流程

### 阶段 4: 测试与验证

1. **单元测试**

   - 每个类的基本功能测试
   - OCL 约束验证测试
   - 边界条件测试

2. **状态机测试**

   - 所有合法状态转换测试
   - 非法状态转换拒绝测试
   - 守卫条件测试

3. **集成测试**
   - 完整业务流程测试（选课 → 上课 → 作业 → 评分）
   - 订阅计费流程测试
   - 异常场景测试

### 待实现的关键逻辑

#### 1. Course::enroll(Student\* student)

```cpp
Enrollment* Course::enroll(Student* student) {
    // 检查课程状态
    if (status != Status::EnrollmentOpen && status != Status::Waitlisted) {
        throw std::runtime_error("Course enrollment is not open");
    }

    // 检查是否已报名
    for (auto enrollment : *courseEnrollments) {
        if (enrollment->getStudent() == student) {
            throw std::runtime_error("Student already enrolled");
        }
    }

    // 检查容量，决定Active或Waitlisted
    int activeCount = 0;
    for (auto enrollment : *courseEnrollments) {
        if (enrollment->getStatus() == EnrollmentStatus::Active) {
            activeCount++;
        }
    }

    EnrollmentStatus enrollStatus;
    if (activeCount < capacity) {
        enrollStatus = EnrollmentStatus::Active;
    } else {
        enrollStatus = EnrollmentStatus::Waitlisted;
        // 切换课程状态到Waitlisted
        setStatus(Status::Waitlisted);
    }

    // 创建Enrollment
    time_t now = time(nullptr);
    tm* enrollTime = localtime(&now);
    return addCourseEnrollment(generateId(), enrollStatus, enrollTime, student);
}
```

#### 2. Submission::submit()

```cpp
bool Submission::submit() {
    // 守卫条件：检查截止时间
    time_t now = time(nullptr);
    if (difftime(now, mktime(assignment->getDeadline())) > 0) {
        return false; // 超过截止时间
    }

    // 更新提交时间和版本
    tm* submitTime = localtime(&now);
    setSubmittedAt(submitTime);

    // 版本号递增（需要从Assignment查询已有提交数）
    int currentVersion = assignment->getSubmissions()->size() + 1;
    setVersion(currentVersion);

    // 状态转换
    return setStatus(Status::Submitted);
}
```

#### 3. Submission::grade()

```cpp
bool Submission::grade(double score, const std::string& feedback) {
    // 守卫条件：检查状态
    if (getStatus() != Status::Grading) {
        return false;
    }

    // 守卫条件：检查分数范围
    if (score < 0 || score > assignment->getMaxScore()) {
        return false;
    }

    // 创建或更新Grade
    if (submissionGrade == nullptr) {
        // 创建新Grade（需要实现Grade构造函数）
        submissionGrade = new Grade(generateId(), score, feedback, this);
    } else {
        submissionGrade->setScore(score);
        submissionGrade->setFeedback(feedback);
    }

    // 状态转换
    return setStatus(Status::Graded);
}
```

#### 4. Subscription::chargeSuccess()

```cpp
bool Subscription::chargeSuccess() {
    // 检查最近一次支付状态
    if (subscriptionPayments->empty()) {
        return false;
    }

    Payment* lastPayment = subscriptionPayments->back();
    if (lastPayment->getStatus() != PaymentStatus::Succeeded) {
        return false;
    }

    // 更新nextBillingAt
    tm* currentNext = nextBillingAt;
    tm newNext = *currentNext;

    if (plan == PlanType::Monthly) {
        newNext.tm_mon += 1; // 加1个月
    } else if (plan == PlanType::Annual) {
        newNext.tm_year += 1; // 加1年
    }

    mktime(&newNext); // 规范化时间
    setNextBillingAt(&newNext);

    // 状态转换
    return setStatus(Status::Active);
}
```

### 需要补充的工具函数

1. **时间比较函数**: 比较两个 tm\*对象
2. **ID 生成函数**: 生成唯一字符串 ID
3. **时间计算函数**: 计算未来时间（加 n 天/月/年）
4. **验证函数**: 集中的 OCL 约束验证

---

## 总结

本架构文档完整描述了在线学习平台的：

- ✅ 文件与文件夹结构（按模块组织）
- ✅ 核心类与属性（15 个实体类）
- ✅ 关联关系与多重度（11 种关联）
- ✅ 状态机设计（3 个核心状态机）
- ✅ OCL 约束规范（12 条业务规则）
- ✅ 方法签名与职责
- ✅ 实现指南与示例代码

**下一步**: 根据此架构在`task.md`中编写详细的分步实现任务列表。

---

## 附录：枚举类型定义

### CourseStatus

```cpp
enum CourseStatus {
    Draft,              // 草稿
    Published,          // 已发布
    EnrollmentOpen,     // 选课开放
    Waitlisted,         // 候补
    InProgress,         // 进行中
    Completed,          // 已完成
    Cancelled           // 已取消
};
```

### EnrollmentStatus

```cpp
enum EnrollmentStatus {
    Active,             // 正式学生
    Waitlisted,         // 候补名单
    Dropped,            // 已退课
    Completed           // 已完成
};
```

### SubmissionStatus

```cpp
enum SubmissionStatus {
    Created,                    // 已创建
    Submitted,                  // 已提交
    UnderCheck,                 // 检查中
    Grading,                    // 评分中
    Graded,                     // 已评分
    Returned,                   // 已退回
    ResubmissionRequested       // 要求重交
};
```

### PaymentStatus

```cpp
enum PaymentStatus {
    Pending,            // 待处理
    Succeeded,          // 成功
    Failed,             // 失败
    Refunding,          // 退款中
    Refunded            // 已退款
};
```

### SubscriptionStatus

```cpp
enum SubscriptionStatus {
    Trial,              // 试用期
    Active,             // 活跃
    PastDue,            // 逾期
    Suspended,          // 暂停
    Cancelled           // 已取消
};
```

### PlanType

```cpp
enum PlanType {
    Trial,              // 试用
    Monthly,            // 月付
    Annual              // 年付
};
```

---

**文档版本**: 1.0  
**最后更新**: 2025-11-10  
**维护者**: 开发团队
