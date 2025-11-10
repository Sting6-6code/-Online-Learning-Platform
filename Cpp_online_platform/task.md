# 在线学习平台 MVP 实现任务清单

> **目标**: 基于 `architecture.md` 的设计，逐步实现一个可测试的在线学习平台 MVP
>
> **原则**:
>
> - 每个任务都非常小且可独立测试
> - 每个任务有明确的开始和结束标准
> - 任务按依赖顺序排列
> - 先实现核心功能，再实现高级特性

---

## 阶段 0: 环境准备与验证 (5 个任务)

### Task 0.1: 验证 CMake 构建系统

**目标**: 确保项目可以编译

- [ ] 运行 `cmake .` 验证 CMakeLists.txt 配置正确
- [ ] 运行 `make` 编译所有生成的代码
- [ ] 验证无编译错误
- [ ] 运行生成的可执行文件，确保能正常启动

**测试标准**:

- 编译成功，无错误
- 可执行文件能运行且正常退出

---

### Task 0.2: 验证 Umple 生成的所有类声明

**目标**: 确认所有 15 个类都正确生成

- [ ] 打开 `model_Model.h`，确认包含所有类声明
- [ ] 确认以下类存在：
  - User, Student, Instructor, Administrator
  - Course, Enrollment, Lesson, VideoContent, CourseCategory
  - Assignment, Submission, Grade
  - Payment, Subscription, Refund
- [ ] 确认所有枚举类型定义存在

**测试标准**:

- 所有 15 个类的头文件存在
- 所有枚举类型定义正确

---

### Task 0.3: 验证状态机枚举定义

**目标**: 确认三个状态机的枚举类型正确

- [ ] 验证 `Course::Status` 枚举（7 个状态）
- [ ] 验证 `Submission::Status` 枚举（7 个状态）
- [ ] 验证 `Subscription::Status` 枚举（5 个状态）
- [ ] 验证其他枚举：EnrollmentStatus, PaymentStatus, PlanType

**测试标准**:

- 所有枚举值与 architecture.md 一致
- 枚举可以正确转换为字符串

---

### Task 0.4: 创建工具函数头文件

**目标**: 创建 `Utils.h` 用于存放公共工具函数声明

- [ ] 创建 `Utils.h` 文件
- [ ] 声明时间比较函数：`int compareTimes(tm* t1, tm* t2)`
- [ ] 声明 ID 生成函数：`string generateId(const string& prefix)`
- [ ] 声明时间计算函数：`tm* addDays(tm* base, int days)`, `tm* addMonths(tm* base, int months)`
- [ ] 声明当前时间获取：`tm* getCurrentTime()`

**测试标准**:

- Utils.h 文件存在
- 所有函数声明语法正确

---

### Task 0.5: 实现工具函数

**目标**: 实现 `Utils.cpp` 中的所有工具函数

- [ ] 创建 `Utils.cpp` 文件
- [ ] 实现 `compareTimes()`: 返回 -1/0/1
- [ ] 实现 `generateId()`: 使用 UUID 或时间戳生成唯一 ID
- [ ] 实现 `addDays()`: 正确处理月份/年份边界
- [ ] 实现 `addMonths()`: 正确处理年份边界
- [ ] 实现 `getCurrentTime()`: 返回当前系统时间

**测试标准**:

- 创建简单的测试程序验证每个函数
- `compareTimes()` 正确比较不同时间
- `generateId()` 每次调用返回不同 ID
- 时间计算函数处理边界情况（如 1 月 31 日 + 1 个月）

---

## 阶段 1: 用户模块基础实现 (4 个任务)

### Task 1.1: 实现 User 基类构造函数验证

**目标**: 为 User 类添加属性验证

- [ ] 打开 `User.cpp`
- [ ] 在构造函数中添加验证：
  - `id` 不能为空
  - `email` 格式基本验证（包含 @）
  - `name` 不能为空
- [ ] 验证失败时抛出 `std::invalid_argument` 异常

**测试标准**:

- 创建有效 User 对象成功
- 传入空 id 抛出异常
- 传入无效 email 抛出异常

---

### Task 1.2: 实现 Student 类基础功能

**目标**: 完善 Student 类的基本操作

- [ ] 验证 Student 可以正确继承 User
- [ ] 测试添加/移除 Enrollment 关联
- [ ] 测试添加/移除 Submission 关联
- [ ] 添加便捷方法：`int getActiveEnrollmentCount()`

**测试标准**:

- 可以创建 Student 对象
- 可以访问继承的属性（id, name, email）
- 关联集合操作正常

---

### Task 1.3: 实现 Instructor 类基础功能

**目标**: 完善 Instructor 类的基本操作

- [ ] 验证 Instructor 可以正确继承 User
- [ ] 测试添加/移除 Course 关联
- [ ] 添加便捷方法：`int getActiveCourseCount()`
- [ ] 添加便捷方法：`vector<Course*>* getCoursesInProgress()`

**测试标准**:

- 可以创建 Instructor 对象
- 可以管理课程列表
- 便捷方法返回正确结果

---

### Task 1.4: 实现 Administrator 类占位符

**目标**: 为管理员类预留接口

- [ ] 验证 Administrator 可以正确继承 User
- [ ] 添加注释说明未来功能（系统管理、用户管理等）
- [ ] 确保可以创建 Administrator 对象

**测试标准**:

- 可以创建 Administrator 对象
- 继承关系正确

---

## 阶段 2: 课程模块核心实现 (10 个任务)

### Task 2.1: 实现 CourseCategory 类

**目标**: 实现最简单的课程分类实体

- [ ] 打开 `CourseCategory.cpp`
- [ ] 在构造函数中验证 `id` 和 `name` 不为空
- [ ] 测试创建多个分类对象
- [ ] 测试分类的多对多关联（添加到 Course）

**测试标准**:

- 可以创建 CourseCategory 对象
- id 和 name 验证正常
- 可以与 Course 建立多对多关系

---

### Task 2.2: 实现 VideoContent 类

**目标**: 实现视频内容实体

- [ ] 打开 `VideoContent.cpp`
- [ ] 验证 `url` 不为空
- [ ] 验证 `durationSec > 0`
- [ ] 确保 Composition 关系（删除 Lesson 时自动删除 VideoContent）

**测试标准**:

- 可以创建 VideoContent 对象
- 时长验证正常
- Composition 删除级联正确

---

### Task 2.3: 实现 Lesson 类基础功能

**目标**: 实现课时实体

- [ ] 打开 `Lesson.cpp`
- [ ] 验证 `orderIndex >= 0`
- [ ] 实现 `addVideo()` 方法的 Composition 语义
- [ ] 添加便捷方法：`int getTotalDuration()` (所有视频时长之和)

**测试标准**:

- 可以创建 Lesson 对象
- orderIndex 验证正常
- 可以添加 VideoContent
- getTotalDuration() 计算正确

---

### Task 2.4: 实现 Enrollment 关联类基础功能

**目标**: 实现选课记录实体

- [ ] 打开 `Enrollment.cpp`
- [ ] 验证 `enrolledAt` 不为 null
- [ ] 验证 `student` 和 `course` 关联不为 null
- [ ] 实现状态转换方法：`bool dropCourse()` (Active/Waitlisted → Dropped)
- [ ] 实现状态转换方法：`bool completeCourse()` (Active → Completed)

**测试标准**:

- 可以创建 Enrollment 对象
- 关联验证正常
- 状态转换方法正常工作

---

### Task 2.5: 实现 Course 类构造函数验证

**目标**: 为 Course 类添加基本验证

- [ ] 打开 `Course.cpp`
- [ ] 验证 `capacity > 0`
- [ ] 验证 `title` 不为空
- [ ] 验证 `instructor` 不为 null
- [ ] 初始状态设为 `Draft`

**测试标准**:

- 可以创建 Course 对象
- 容量验证正常
- 初始状态为 Draft

---

### Task 2.6: 实现 Course::publish() 状态转换

**目标**: 实现课程发布功能

- [ ] 在 `Course.cpp` 中找到 `publish()` 方法
- [ ] 添加守卫条件：当前状态必须是 Draft
- [ ] 添加守卫条件：`lessons->size() >= 1`
- [ ] 添加守卫条件：`courseAssignments->size() >= 1`
- [ ] 状态转换：`status := Published`
- [ ] 返回 `true` 表示成功，守卫失败返回 `false`

**测试标准**:

- Draft 状态的课程可以发布
- 没有 Lesson 的课程发布失败
- 没有 Assignment 的课程发布失败
- 非 Draft 状态的课程发布失败

---

### Task 2.7: 实现 Course::openEnrollment() 状态转换

**目标**: 实现开放选课功能

- [ ] 在 `Course.cpp` 中找到 `openEnrollment()` 方法
- [ ] 添加守卫条件：当前状态必须是 Published
- [ ] 添加守卫条件：`capacity > 0`
- [ ] 状态转换：`status := EnrollmentOpen`
- [ ] 返回操作结果

**测试标准**:

- Published 状态的课程可以开放选课
- capacity = 0 的课程开放失败
- 非 Published 状态的课程开放失败

---

### Task 2.8: 实现 Course::enroll() 选课逻辑

**目标**: 实现学生选课核心逻辑

- [ ] 在 `Course.cpp` 中实现 `enroll(Student* student)` 方法
- [ ] 检查课程状态（EnrollmentOpen 或 Waitlisted）
- [ ] 检查学生是否已报名（避免重复）
- [ ] 统计当前 Active 报名数
- [ ] 如果未满，创建 Active 状态的 Enrollment
- [ ] 如果已满，创建 Waitlisted 状态的 Enrollment，并将课程状态改为 Waitlisted
- [ ] 返回创建的 Enrollment 对象

**测试标准**:

- 未满员课程可以正常选课（Active）
- 满员课程自动进入候补（Waitlisted）
- 重复选课被拒绝
- 课程状态在满员时自动切换到 Waitlisted

---

### Task 2.9: 实现 Course::startCourse() 状态转换

**目标**: 实现课程开课功能

- [ ] 在 `Course.cpp` 中找到 `startCourse()` 方法
- [ ] 添加守卫条件：当前状态必须是 EnrollmentOpen 或 Waitlisted
- [ ] 添加守卫条件：至少一个 Active 的 Enrollment
- [ ] 状态转换：`status := InProgress`
- [ ] 返回操作结果

**测试标准**:

- 有学生的课程可以开课
- 没有学生的课程开课失败
- 只有 Waitlisted 学生的课程开课失败

---

### Task 2.10: 实现 Course::complete() 和 Course::cancel() 状态转换

**目标**: 实现课程结束和取消功能

- [ ] 实现 `complete()` 方法：
  - 守卫条件：当前状态必须是 InProgress
  - 状态转换：`status := Completed`
- [ ] 实现 `cancel(String reason)` 方法：
  - 可以从任意状态取消（除了 Completed）
  - 状态转换：`status := Cancelled`
  - 记录取消原因（可以添加新属性 `cancelReason`）

**测试标准**:

- InProgress 的课程可以结课
- 任何状态（除 Completed）可以取消
- Completed 的课程不能取消

---

## 阶段 3: 作业系统实现 (10 个任务)

### Task 3.1: 实现 Assignment 类基础功能

**目标**: 实现作业实体

- [ ] 打开 `Assignment.cpp`
- [ ] 验证 `title` 不为空
- [ ] 验证 `maxScore > 0`
- [ ] 验证 `deadline` 不为 null
- [ ] 添加便捷方法：`bool isOverdue()` (比较当前时间与 deadline)
- [ ] 添加便捷方法：`int getSubmissionCount()`

**测试标准**:

- 可以创建 Assignment 对象
- maxScore 验证正常
- isOverdue() 判断正确

---

### Task 3.2: 实现 Grade 类基础功能

**目标**: 实现成绩实体

- [ ] 打开 `Grade.cpp`
- [ ] 验证 `score >= 0`
- [ ] 验证 `score <= submission->assignment->maxScore` (需要访问关联)
- [ ] 验证 `submission` 不为 null
- [ ] 添加便捷方法：`double getPercentage()` (score / maxScore \* 100)

**测试标准**:

- 可以创建 Grade 对象
- 分数范围验证正常
- getPercentage() 计算正确

---

### Task 3.3: 实现 Submission 类构造函数验证

**目标**: 为 Submission 类添加基本验证

- [ ] 打开 `Submission.cpp`
- [ ] 验证 `assignment` 和 `student` 不为 null
- [ ] 初始状态设为 `Created`
- [ ] 初始 `version` 设为 0
- [ ] 初始 `checkPassed` 设为 false
- [ ] `submittedAt` 初始可为 null

**测试标准**:

- 可以创建 Submission 对象
- 初始状态为 Created
- 关联验证正常

---

### Task 3.4: 实现 Submission::submit() 状态转换

**目标**: 实现作业提交功能

- [ ] 在 `Submission.cpp` 中找到 `submit()` 方法
- [ ] 添加守卫条件：当前状态必须是 Created
- [ ] 添加守卫条件：`getCurrentTime() <= assignment->deadline`
- [ ] 设置 `submittedAt := getCurrentTime()`
- [ ] 计算版本号：统计该学生对该作业的已有提交数 + 1
- [ ] 状态转换：`status := Submitted`
- [ ] 返回操作结果

**测试标准**:

- Created 状态的提交可以提交
- 超过截止时间的提交失败
- 版本号自动递增
- 提交时间被记录

---

### Task 3.5: 实现 Submission::startAutoChecks() 状态转换

**目标**: 实现自动检查触发功能

- [ ] 在 `Submission.cpp` 中找到 `startAutoChecks()` 方法
- [ ] 添加守卫条件：当前状态必须是 Submitted
- [ ] 状态转换：`status := UnderCheck`
- [ ] 返回操作结果
- [ ] 添加注释：实际的检查逻辑（查重、编译）由外部系统触发

**测试标准**:

- Submitted 状态的提交可以开始检查
- 非 Submitted 状态的提交检查失败

---

### Task 3.6: 实现 Submission::checksPass() 和 checksFail() 状态转换

**目标**: 实现检查结果处理

- [ ] 实现 `checksPass()` 方法：
  - 守卫条件：当前状态必须是 UnderCheck
  - 设置 `checkPassed := true`
  - 状态转换：`status := Submitted`（回到已提交，标记为可评分）
- [ ] 实现 `checksFail()` 方法：
  - 守卫条件：当前状态必须是 UnderCheck
  - 设置 `checkPassed := false`
  - 状态转换：`status := Returned`
- [ ] 返回操作结果

**测试标准**:

- UnderCheck 状态的提交可以标记为通过
- UnderCheck 状态的提交可以标记为失败
- checkPassed 标志正确设置
- 状态正确转换

---

### Task 3.7: 实现 Submission::startGrading() 状态转换

**目标**: 实现开始评分功能

- [ ] 在 `Submission.cpp` 中找到 `startGrading()` 方法
- [ ] 添加守卫条件：当前状态必须是 Submitted
- [ ] 添加守卫条件：`checkPassed == true`
- [ ] 状态转换：`status := Grading`
- [ ] 返回操作结果

**测试标准**:

- 检查通过的提交可以开始评分
- 检查未通过的提交评分失败
- 非 Submitted 状态的提交评分失败

---

### Task 3.8: 实现 Submission::grade() 状态转换（添加参数版本）

**目标**: 实现评分功能

- [ ] 在 `Submission.h` 中添加新方法签名：`bool grade(double score, const string& feedback)`
- [ ] 在 `Submission.cpp` 中实现该方法：
  - 守卫条件：当前状态必须是 Grading
  - 守卫条件：`0 <= score <= assignment->maxScore`
  - 如果 `submissionGrade == nullptr`，创建新 Grade 对象
  - 否则更新现有 Grade 的 score 和 feedback
  - 状态转换：`status := Graded`
  - 返回操作结果

**测试标准**:

- Grading 状态的提交可以评分
- 分数范围验证正常
- Grade 对象正确创建/更新
- 非 Grading 状态的提交评分失败

---

### Task 3.9: 实现 Submission::requestResubmission() 状态转换

**目标**: 实现重交请求功能

- [ ] 在 `Submission.cpp` 中找到 `requestResubmission()` 方法
- [ ] 添加守卫条件：当前状态必须是 Graded 或 Returned
- [ ] 添加守卫条件（可选）：`getCurrentTime() <= assignment->deadline`
- [ ] 状态转换：`status := ResubmissionRequested`
- [ ] 返回操作结果
- [ ] 添加注释：学生需要创建新的 Submission 对象（新版本）

**测试标准**:

- Graded 或 Returned 状态的提交可以请求重交
- 其他状态的提交请求失败
- 状态正确转换

---

### Task 3.10: 实现提交版本管理逻辑

**目标**: 确保同一学生对同一作业的版本号单调递增

- [ ] 在 Assignment 类中添加辅助方法：`int getVersionCount(Student* student)`
- [ ] 该方法统计 `submissions` 中属于指定学生的提交数量
- [ ] 在 `Submission::submit()` 中使用该方法计算正确的版本号
- [ ] 添加测试：同一学生多次提交同一作业，版本号递增

**测试标准**:

- 第一次提交版本号为 1
- 第二次提交版本号为 2
- 不同学生的提交版本号独立计算

---

## 阶段 4: 支付系统实现 (9 个任务)

### Task 4.1: 实现 Payment 类基础功能

**目标**: 实现支付实体

- [ ] 打开 `Payment.cpp`
- [ ] 验证 `amount > 0`
- [ ] 验证 `subscription` 不为 null
- [ ] 初始状态设为 `Pending`
- [ ] `paidAt` 初始可为 null
- [ ] 添加便捷方法：`bool isSuccessful()` (status == Succeeded)

**测试标准**:

- 可以创建 Payment 对象
- 金额验证正常
- 初始状态为 Pending

---

### Task 4.2: 实现 Payment 状态转换方法

**目标**: 实现支付状态变化

- [ ] 添加方法：`bool markSucceeded()`
  - 守卫条件：状态为 Pending
  - 设置 `paidAt := getCurrentTime()`
  - 状态转换：`status := Succeeded`
- [ ] 添加方法：`bool markFailed()`
  - 守卫条件：状态为 Pending
  - 状态转换：`status := Failed`
- [ ] 返回操作结果

**测试标准**:

- Pending 状态的支付可以标记为成功
- Pending 状态的支付可以标记为失败
- 成功支付记录 paidAt
- 非 Pending 状态的支付不能更改

---

### Task 4.3: 实现 Refund 类基础功能

**目标**: 实现退款实体

- [ ] 打开 `Refund.cpp`
- [ ] 验证 `amount > 0`
- [ ] 验证 `amount <= payment->amount`
- [ ] 验证 `payment->status == Succeeded`
- [ ] 设置 `requestedAt := getCurrentTime()`
- [ ] `processedAt` 初始为 null

**测试标准**:

- 可以创建 Refund 对象
- 金额验证正常
- 只能为成功的支付创建退款
- 退款金额不能超过支付金额

---

### Task 4.4: 实现 Payment::refund() 方法（添加）

**目标**: 实现退款触发功能

- [ ] 在 `Payment.h` 中添加方法签名：`bool initiateRefund(double amount)`
- [ ] 在 `Payment.cpp` 中实现该方法：
  - 守卫条件：当前状态必须是 Succeeded
  - 守卫条件：`amount <= this->amount`
  - 守卫条件：`refund == nullptr` (不能重复退款)
  - 创建 Refund 对象并关联
  - 状态转换：`status := Refunding`
  - 返回操作结果

**测试标准**:

- Succeeded 状态的支付可以退款
- 退款金额验证正常
- 重复退款被拒绝
- Refund 对象正确创建

---

### Task 4.5: 实现 Payment::refundProcessed() 方法（添加）

**目标**: 实现退款完成功能

- [ ] 在 `Payment.h` 中添加方法签名：`bool completeRefund()`
- [ ] 在 `Payment.cpp` 中实现该方法：
  - 守卫条件：当前状态必须是 Refunding
  - 守卫条件：`refund != nullptr`
  - 设置 `refund->processedAt := getCurrentTime()`
  - 状态转换：`status := Refunded`
  - 返回操作结果

**测试标准**:

- Refunding 状态的支付可以完成退款
- processedAt 被正确设置
- 非 Refunding 状态的支付不能完成退款

---

### Task 4.6: 实现 Subscription 类构造函数验证

**目标**: 为 Subscription 类添加基本验证

- [ ] 打开 `Subscription.cpp`
- [ ] 验证 `plan` 是有效的 PlanType 枚举值
- [ ] 验证 `startAt` 不为 null
- [ ] 设置 `nextBillingAt` 根据 plan 类型：
  - Trial: startAt + 7 天
  - Monthly: startAt + 30 天
  - Annual: startAt + 365 天
- [ ] 初始状态设为 `Trial` (如果 plan == Trial) 或 `Active`

**测试标准**:

- 可以创建 Subscription 对象
- nextBillingAt 自动计算正确
- 初始状态正确

---

### Task 4.7: 实现 Subscription::chargeSuccess() 状态转换

**目标**: 实现扣款成功处理

- [ ] 在 `Subscription.cpp` 中找到 `chargeSuccess()` 方法
- [ ] 添加守卫条件：`subscriptionPayments` 不为空
- [ ] 获取最近一笔支付：`lastPayment = subscriptionPayments->back()`
- [ ] 添加守卫条件：`lastPayment->status == Succeeded`
- [ ] 更新 `nextBillingAt`：
  - Monthly: +30 天
  - Annual: +365 天
- [ ] 状态转换：`status := Active`
- [ ] 返回操作结果

**测试标准**:

- 有成功支付的订阅可以激活
- nextBillingAt 正确更新
- 没有支付或支付失败的订阅激活失败

---

### Task 4.8: 实现 Subscription::chargeFail() 状态转换

**目标**: 实现扣款失败处理

- [ ] 在 `Subscription.cpp` 中找到 `chargeFail()` 方法
- [ ] 添加守卫条件：当前状态必须是 Active
- [ ] 添加守卫条件：`subscriptionPayments` 不为空
- [ ] 获取最近一笔支付：`lastPayment = subscriptionPayments->back()`
- [ ] 添加守卫条件：`lastPayment->status == Failed`
- [ ] 状态转换：`status := PastDue`
- [ ] 返回操作结果

**测试标准**:

- Active 状态的订阅可以因失败支付进入 PastDue
- 非 Active 状态的订阅不能进入 PastDue
- 必须有失败的支付记录

---

### Task 4.9: 实现 Subscription::graceExpire() 和 cancel() 状态转换

**目标**: 实现宽限期结束和取消订阅

- [ ] 实现 `graceExpire()` 方法：
  - 守卫条件：当前状态必须是 PastDue
  - 状态转换：`status := Suspended`
- [ ] 实现 `cancel()` 方法：
  - 可以从任意状态取消
  - 状态转换：`status := Cancelled`
- [ ] 返回操作结果

**测试标准**:

- PastDue 状态的订阅可以暂停
- 任何状态的订阅可以取消

---

## 阶段 5: OCL 约束验证实现 (12 个任务)

### Task 5.1: 实现 SeatsNotExceeded 约束验证

**目标**: 课程容量约束

- [ ] 在 `Course.cpp` 中添加方法：`bool validateSeatsNotExceeded()`
- [ ] 统计 Active 状态的 Enrollment 数量
- [ ] 返回 `activeCount <= capacity`
- [ ] 在 `enroll()` 方法中调用验证
- [ ] 添加测试用例

**测试标准**:

- 满员课程验证失败
- 未满员课程验证成功

---

### Task 5.2: 实现 EnrollmentOnlyAfterPublish 约束验证

**目标**: 选课前提约束

- [ ] 在 `Enrollment.cpp` 的构造函数中添加验证
- [ ] 检查 `course->status != Draft`
- [ ] 验证失败抛出异常
- [ ] 添加测试用例

**测试标准**:

- Draft 状态的课程无法创建 Enrollment
- 其他状态的课程可以创建 Enrollment

---

### Task 5.3: 实现 CourseStartRequiresStudent 约束验证

**目标**: 开课学生数约束

- [ ] 在 `Course::startCourse()` 中已经实现了守卫条件
- [ ] 添加独立的验证方法：`bool validateHasActiveStudents()`
- [ ] 添加测试用例验证约束

**测试标准**:

- 无学生的课程无法开课
- 有学生的课程可以开课

---

### Task 5.4: 实现 SubmissionBeforeDeadline 约束验证

**目标**: 提交时间约束

- [ ] 在 `Submission::submit()` 中已经实现了守卫条件
- [ ] 添加独立的验证方法：`bool validateNotOverdue()`
- [ ] 添加测试用例验证约束

**测试标准**:

- 超过截止时间的提交被拒绝
- 截止时间前的提交成功

---

### Task 5.5: 实现 SubmissionVersionMonotonic 约束验证

**目标**: 版本单调性约束

- [ ] 在 Task 3.10 中已经实现了版本管理
- [ ] 添加验证方法：`bool validateVersionMonotonic()`
- [ ] 检查当前 version 等于该学生的提交数
- [ ] 添加测试用例

**测试标准**:

- 版本号单调递增
- 不同学生的版本号独立

---

### Task 5.6: 实现 GradeWithinRange 约束验证

**目标**: 成绩范围约束

- [ ] 在 `Grade.cpp` 的构造函数和 setter 中添加验证
- [ ] 检查 `0 <= score <= submission->assignment->maxScore`
- [ ] 验证失败抛出异常
- [ ] 添加测试用例

**测试标准**:

- 超出范围的分数被拒绝
- 有效范围的分数接受

---

### Task 5.7: 实现 AtMostOneGradePerSubmission 约束验证

**目标**: 提交评分唯一性

- [ ] 在 `Submission::grade()` 中已经检查了 Grade 的存在
- [ ] 添加验证方法：`bool validateGradeUniqueness()`
- [ ] 确保只有一个 Grade 对象
- [ ] 添加测试用例

**测试标准**:

- 一个提交只能有一个 Grade
- 尝试创建多个 Grade 失败

---

### Task 5.8: 实现 UniqueCourseCategories 约束验证

**目标**: 课程分类唯一性

- [ ] 在 `Course.cpp` 中添加方法：`bool validateUniqueCat egories()`
- [ ] 检查 categories 中没有重复的 id
- [ ] 在 `addCategory()` 中调用验证
- [ ] 添加测试用例

**测试标准**:

- 重复的分类无法添加到课程
- 唯一的分类可以添加

---

### Task 5.9: 实现 RefundOnlyForSucceededPayment 约束验证

**目标**: 退款前提约束

- [ ] 在 Task 4.3 中已经在 Refund 构造函数中验证
- [ ] 在 `Payment::initiateRefund()` 中也添加验证
- [ ] 添加测试用例

**测试标准**:

- 只有成功的支付可以退款
- 失败或待处理的支付无法退款

---

### Task 5.10: 实现 SubscriptionBillingConsistency 约束验证

**目标**: 订阅计费一致性

- [ ] 在 `Subscription.cpp` 中添加方法：`bool validateBillingConsistency()`
- [ ] 如果状态为 Active，检查 `nextBillingAt > startAt`
- [ ] 在状态转换到 Active 时调用验证
- [ ] 添加测试用例

**测试标准**:

- Active 订阅的 nextBillingAt 必须在未来
- 其他状态的订阅不受约束

---

### Task 5.11: 实现 PublishRequiresContent 约束验证

**目标**: 发布内容完整性

- [ ] 在 Task 2.6 中已经在 `Course::publish()` 实现了守卫条件
- [ ] 添加独立的验证方法：`bool validatePublishReady()`
- [ ] 添加测试用例

**测试标准**:

- 没有内容的课程无法发布
- 有完整内容的课程可以发布

---

### Task 5.12: 实现 PaymentRefundStateTransition 约束验证

**目标**: 支付退款状态转换

- [ ] 在 Task 4.4 和 4.5 中已经实现了状态转换验证
- [ ] 添加测试用例验证前置和后置条件
- [ ] 确保状态转换的原子性

**测试标准**:

- 只有 Succeeded 的支付可以开始退款
- 退款后状态为 Refunding
- 完成后状态为 Refunded

---

## 阶段 6: 集成测试与场景验证 (8 个任务)

### Task 6.1: 实现完整的课程发布流程测试

**目标**: 测试课程从创建到发布的完整流程

- [ ] 创建测试文件：`test_course_lifecycle.cpp`
- [ ] 测试场景：
  1. 创建教师
  2. 创建课程（Draft 状态）
  3. 添加 Lesson 和 VideoContent
  4. 添加 Assignment
  5. 发布课程
  6. 开放选课
- [ ] 验证每一步的状态和约束

**测试标准**:

- 完整流程无错误执行
- 所有状态转换正确
- 所有约束被正确验证

---

### Task 6.2: 实现完整的选课流程测试

**目标**: 测试学生选课的完整流程

- [ ] 创建测试文件：`test_enrollment_flow.cpp`
- [ ] 测试场景：
  1. 创建课程并开放选课
  2. 创建学生
  3. 学生选课（Active）
  4. 选课直到满员
  5. 再有学生选课（进入 Waitlisted）
  6. 验证课程状态变化
- [ ] 测试边界情况（容量边界）

**测试标准**:

- 选课逻辑正确
- 满员后自动进入候补
- 课程状态正确切换

---

### Task 6.3: 实现完整的作业提交与评分流程测试

**目标**: 测试作业从发布到评分的完整流程

- [ ] 创建测试文件：`test_assignment_flow.cpp`
- [ ] 测试场景：
  1. 课程中创建作业
  2. 学生创建提交（Created）
  3. 学生提交作业（Submitted）
  4. 自动检查（UnderCheck → Submitted）
  5. 教师开始评分（Grading）
  6. 教师给出成绩（Graded）
  7. 学生查看成绩
- [ ] 测试异常场景（超时提交、检查失败）

**测试标准**:

- 完整流程无错误执行
- 所有状态转换正确
- 异常场景被正确处理

---

### Task 6.4: 实现作业重交流程测试

**目标**: 测试作业重新提交的完整流程

- [ ] 创建测试文件：`test_resubmission.cpp`
- [ ] 测试场景：
  1. 学生首次提交并被评分
  2. 教师要求重交（ResubmissionRequested）
  3. 学生创建新的 Submission（版本 2）
  4. 新提交完成评分流程
  5. 验证版本号递增
- [ ] 测试版本管理逻辑

**测试标准**:

- 重交流程正确
- 版本号单调递增
- 每个版本独立管理

---

### Task 6.5: 实现订阅与支付流程测试

**目标**: 测试订阅计费的完整流程

- [ ] 创建测试文件：`test_subscription_billing.cpp`
- [ ] 测试场景：
  1. 创建学生
  2. 创建订阅（Trial 状态）
  3. 创建支付并标记成功
  4. 订阅激活（Active）
  5. 验证 nextBillingAt 更新
  6. 模拟下一次计费周期
- [ ] 测试不同 plan 类型（Monthly, Annual）

**测试标准**:

- 订阅状态正确转换
- nextBillingAt 计算正确
- 不同 plan 类型处理正确

---

### Task 6.6: 实现支付失败与宽限期流程测试

**目标**: 测试扣款失败的处理流程

- [ ] 创建测试文件：`test_payment_failure.cpp`
- [ ] 测试场景：
  1. Active 订阅
  2. 创建支付并标记失败
  3. 订阅进入 PastDue
  4. 宽限期结束（Suspended）
  5. 用户取消订阅（Cancelled）
- [ ] 测试宽限期逻辑

**测试标准**:

- 失败处理流程正确
- 状态转换符合预期
- 宽限期逻辑正确

---

### Task 6.7: 实现退款流程测试

**目标**: 测试支付退款的完整流程

- [ ] 创建测试文件：`test_refund_flow.cpp`
- [ ] 测试场景：
  1. 创建成功的支付
  2. 发起退款
  3. 验证 Refund 对象创建
  4. 验证支付状态变为 Refunding
  5. 完成退款
  6. 验证支付状态变为 Refunded
- [ ] 测试异常场景（重复退款、失败支付退款）

**测试标准**:

- 退款流程正确
- Refund 对象正确创建
- 异常场景被拒绝

---

### Task 6.8: 实现完整的用户学习旅程测试

**目标**: 测试用户从注册到完成课程的端到端流程

- [ ] 创建测试文件：`test_end_to_end.cpp`
- [ ] 测试场景：
  1. 学生注册并创建订阅
  2. 浏览并选课
  3. 观看课程视频（模拟）
  4. 提交作业
  5. 获得成绩
  6. 完成课程
  7. 订阅续费
- [ ] 验证所有关联关系和状态

**测试标准**:

- 完整用户旅程无错误
- 所有模块正确协作
- 数据一致性保持

---

## 阶段 7: 优化与完善 (5 个任务)

### Task 7.1: 添加日志输出

**目标**: 为关键操作添加日志

- [ ] 创建简单的日志工具类
- [ ] 在状态转换时输出日志
- [ ] 在约束验证失败时输出详细信息
- [ ] 在对象创建/销毁时输出日志

**测试标准**:

- 运行测试时可以看到清晰的日志
- 日志包含时间戳和操作类型
- 日志帮助调试

---

### Task 7.2: 优化错误处理

**目标**: 统一异常处理机制

- [ ] 创建自定义异常类：`ValidationException`, `StateTransitionException`
- [ ] 将所有 `std::runtime_error` 替换为自定义异常
- [ ] 添加详细的错误信息
- [ ] 创建错误代码枚举

**测试标准**:

- 所有异常都有清晰的错误信息
- 异常类型区分明确
- 错误处理一致

---

### Task 7.3: 添加对象序列化与反序列化（可选）

**目标**: 实现对象的保存和加载

- [ ] 为每个类添加 `serialize()` 方法
- [ ] 为每个类添加 `deserialize()` 静态方法
- [ ] 使用简单的文本格式（如 JSON 或自定义格式）
- [ ] 测试保存和加载数据

**测试标准**:

- 对象可以保存到文件
- 可以从文件恢复对象
- 关联关系正确恢复

---

### Task 7.4: 性能优化与内存管理

**目标**: 优化内存使用和性能

- [ ] 检查所有动态内存分配
- [ ] 确保所有 new 都有对应的 delete
- [ ] 使用智能指针（std::shared_ptr, std::unique_ptr）替代裸指针
- [ ] 运行内存泄漏检测工具（如 Valgrind）
- [ ] 优化大集合的查询操作（添加索引）

**测试标准**:

- 无内存泄漏
- 程序运行流畅
- 内存使用合理

---

### Task 7.5: 编写用户文档

**目标**: 创建使用文档

- [ ] 创建 `USER_GUIDE.md` 文件
- [ ] 编写类使用示例
- [ ] 编写典型业务流程示例
- [ ] 编写 API 参考文档
- [ ] 添加常见问题解答

**测试标准**:

- 文档清晰易懂
- 示例代码可运行
- 覆盖主要功能

---

## 阶段 8: 最终验收 (3 个任务)

### Task 8.1: 运行所有测试并生成报告

**目标**: 确保所有功能正常

- [ ] 编译并运行所有测试文件
- [ ] 记录测试结果
- [ ] 修复所有失败的测试
- [ ] 生成测试覆盖率报告（如果可能）

**测试标准**:

- 所有测试通过
- 无已知 bug
- 覆盖率达到目标（如 80%）

---

### Task 8.2: 代码审查与重构

**目标**: 提高代码质量

- [ ] 检查代码风格一致性
- [ ] 消除重复代码
- [ ] 优化复杂函数（拆分长函数）
- [ ] 添加必要的注释
- [ ] 检查命名规范

**测试标准**:

- 代码风格一致
- 无重复代码
- 函数职责单一
- 注释充分

---

### Task 8.3: 准备演示程序

**目标**: 创建可演示的程序

- [ ] 创建 `demo_Main.cpp` 文件
- [ ] 实现完整的演示场景：
  1. 创建用户（学生、教师）
  2. 创建并发布课程
  3. 学生选课
  4. 学生完成作业
  5. 教师评分
  6. 订阅计费
- [ ] 添加友好的控制台输出
- [ ] 提供交互式菜单（可选）

**测试标准**:

- 演示程序运行流畅
- 输出清晰直观
- 展示主要功能

---

## 总结

**任务总计**: 73 个小任务

**预计时间**:

- 阶段 0: 2-3 小时
- 阶段 1: 2-3 小时
- 阶段 2: 5-6 小时
- 阶段 3: 5-6 小时
- 阶段 4: 4-5 小时
- 阶段 5: 4-5 小时
- 阶段 6: 4-5 小时
- 阶段 7: 3-4 小时
- 阶段 8: 2-3 小时

**总计**: 31-40 小时

**关键里程碑**:

1. ✅ 阶段 0 完成：项目可编译运行
2. ✅ 阶段 2 完成：课程管理核心功能可用
3. ✅ 阶段 3 完成：作业系统完整可用
4. ✅ 阶段 4 完成：支付系统完整可用
5. ✅ 阶段 5 完成：所有业务约束得到验证
6. ✅ 阶段 6 完成：MVP 系统集成完成
7. ✅ 阶段 8 完成：可交付的产品

**使用建议**:

- 严格按顺序执行任务
- 每完成一个任务立即测试
- 遇到问题及时记录并调整
- 保持小步迭代，频繁提交代码
- 每个阶段结束后进行回顾

**下一步**:
选择第一个任务（Task 0.1）开始实现！

---

**文档版本**: 1.0  
**最后更新**: 2025-11-10  
**维护者**: 开发团队
