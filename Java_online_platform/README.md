# Online Learning Platform - Spring Boot Backend

基于 Spring Boot 的在线学习平台后端 API 系统。

## 🚀 技术栈

- **框架**: Spring Boot 2.7.18
- **语言**: Java 11
- **构建工具**: Maven
- **数据库**: H2 (开发) / MySQL (生产)
- **ORM**: Spring Data JPA / Hibernate
- **测试**: JUnit 5, Spring Boot Test

## 📁 项目结构

```
Java_online_platform/
├── src/main/java/com/olp/
│   ├── OnlineLearningPlatformApplication.java  # Spring Boot 主类
│   ├── model/                                  # 领域模型层
│   │   ├── user/                               # 用户模块
│   │   ├── course/                              # 课程模块
│   │   ├── assignment/                         # 作业模块
│   │   └── payment/                             # 支付模块
│   ├── repository/                             # 数据访问层 (JPA Repository)
│   ├── service/                                 # 业务逻辑层
│   ├── controller/                              # REST API 层
│   ├── config/                                  # 配置类
│   └── util/                                    # 工具类
├── src/main/resources/
│   ├── application.properties                   # 主配置文件
│   ├── application-dev.properties              # 开发环境配置
│   └── application-prod.properties             # 生产环境配置
├── src/test/java/                               # 测试代码
├── pom.xml                                       # Maven 配置
└── README.md                                     # 本文件
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

API 文档将在后续任务中生成（使用 Swagger/OpenAPI）。

## 🧪 测试

```bash
# 运行所有测试
mvn test

# 运行特定测试类
mvn test -Dtest=UserServiceTest
```

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

1. 在 `controller` 包中创建 Controller 类
2. 在 `service` 包中实现业务逻辑
3. 在 `repository` 包中创建 Repository 接口
4. 在 `model` 包中定义实体类

### 代码规范

- 遵循 Spring Boot 最佳实践
- 使用 RESTful API 设计
- 添加适当的注释和文档
- 编写单元测试和集成测试

## 🎯 下一步

参考 `tasks.md` 文件，按照任务清单逐步实现功能。

## 📄 许可证

Educational Use - 用于学习和教学目的
