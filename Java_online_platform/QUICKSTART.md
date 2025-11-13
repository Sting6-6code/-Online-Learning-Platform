# 快速开始指南

## 5 分钟上手在线学习平台

### 1. 启动应用 ⚡

```bash
cd Java_online_platform
mvn spring-boot:run
```

访问 http://localhost:8080 - 应用已启动！

### 2. 创建基础数据 📚

使用以下 curl 命令快速创建测试数据：

```bash
# 创建教师
curl -X POST http://localhost:8080/api/users/instructors \
  -H "Content-Type: application/json" \
  -d '{"id":"I001","name":"王老师","email":"wang@example.com"}'

# 创建学生
curl -X POST http://localhost:8080/api/users/students \
  -H "Content-Type: application/json" \
  -d '{"id":"S001","name":"张同学","email":"zhang@example.com"}'

# 创建课程
curl -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{"id":"C001","title":"Java高级编程","capacity":50,"instructorId":"I001"}'
```

### 3. 完整工作流示例 🎓

#### 课程发布和选课流程

```bash
# 1. 发布课程
curl -X POST http://localhost:8080/api/courses/C001/publish

# 2. 开放选课
curl -X POST http://localhost:8080/api/courses/C001/open-enrollment

# 3. 学生选课
curl -X POST http://localhost:8080/api/courses/C001/enroll \
  -H "Content-Type: application/json" \
  -d '{"studentId":"S001"}'

# 4. 开始课程
curl -X POST http://localhost:8080/api/courses/C001/start

# 5. 查看课程状态
curl http://localhost:8080/api/courses/C001
```

#### 作业提交和评分流程

```bash
# 1. 提交作业
curl -X POST http://localhost:8080/api/submissions/SUB001/submit

# 2. 开始自动检查
curl -X POST http://localhost:8080/api/submissions/SUB001/start-checks

# 3. 检查通过
curl -X POST http://localhost:8080/api/submissions/SUB001/checks-pass

# 4. 开始评分
curl -X POST http://localhost:8080/api/submissions/SUB001/start-grading

# 5. 评分
curl -X POST http://localhost:8080/api/submissions/SUB001/grade \
  -H "Content-Type: application/json" \
  -d '{"score":85.0,"feedback":"做得很好！"}'
```

### 4. 查看数据 🔍

#### H2 数据库控制台

访问 http://localhost:8080/h2-console

- **JDBC URL**: `jdbc:h2:mem:olpdb`
- **Username**: `sa`
- **Password**: (空)

在控制台中执行 SQL：

```sql
-- 查看所有课程
SELECT * FROM courses;

-- 查看选课记录
SELECT * FROM enrollments;

-- 查看作业提交
SELECT * FROM submissions;
```

### 5. 运行测试 ✅

```bash
# 运行所有测试
mvn test

# 查看测试结果
# Tests run: 240, Failures: 0, Errors: 0 ✅
```

## 常用 API 端点速查

| 功能         | 方法 | 端点                                     |
| ------------ | ---- | ---------------------------------------- |
| 获取所有课程 | GET  | `/api/courses`                           |
| 获取课程详情 | GET  | `/api/courses/{id}`                      |
| 学生选课     | POST | `/api/courses/{id}/enroll`               |
| 提交作业     | POST | `/api/submissions/{id}/submit`           |
| 作业评分     | POST | `/api/submissions/{id}/grade`            |
| 获取学生列表 | GET  | `/api/users/students`                    |
| 获取教师课程 | GET  | `/api/courses/instructor/{instructorId}` |

## 下一步

- 📖 查看完整 [README.md](README.md) 了解详细 API 文档
- 📋 参考 [tasks.md](tasks.md) 了解项目实现细节
- 🔧 根据需要修改 `application.properties` 配置
- 🚀 开发自己的功能扩展

## 常见问题

**Q: 如何重置数据库？**  
A: 重启应用即可，H2 使用内存数据库，重启后自动清空。

**Q: 如何切换到 MySQL？**  
A: 修改 `application-prod.properties` 配置，然后使用 `mvn spring-boot:run -Dspring-boot.run.profiles=prod` 启动。

**Q: 测试失败怎么办？**  
A: 运行 `mvn clean test` 清理后重新测试。如果还有问题，检查 JDK 版本是否为 11+。

---

祝你使用愉快！🎉
