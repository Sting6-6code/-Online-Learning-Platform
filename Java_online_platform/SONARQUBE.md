# SonarQube 代码质量检测指南

## 📋 目录

- [快速开始](#快速开始)
- [详细配置](#详细配置)
- [分析报告解读](#分析报告解读)
- [常见问题](#常见问题)

## 🚀 快速开始

### 方法 1: 使用 Docker（推荐）

**1. 启动 SonarQube 服务器**

```bash
# 拉取并运行 SonarQube 容器
docker run -d --name sonarqube \
  -p 9000:9000 \
  sonarqube:latest

# 等待启动完成（约 1-2 分钟）
# 查看日志
docker logs -f sonarqube
```

**2. 访问 SonarQube**

- URL: http://localhost:9000
- 默认账号: `admin`
- 默认密码: `admin`
- 首次登录会要求修改密码

**3. 创建项目并获取 Token**

1. 登录后，点击 "Create Project Manually"
2. Project key: `online-learning-platform`
3. Display name: `Online Learning Platform`
4. 选择 "Locally"
5. 生成 Token 并保存

**4. 运行代码分析**

```bash
# 进入项目目录
cd Java_online_platform

# 方法 A: 使用脚本（推荐）
chmod +x run-sonar.sh
./run-sonar.sh YOUR_TOKEN

# 方法 B: 直接使用 Maven
mvn clean verify sonar:sonar \
  -Dsonar.login=YOUR_TOKEN
```

### 方法 2: 使用 SonarCloud（在线服务）

**1. 注册 SonarCloud**

- 访问: https://sonarcloud.io
- 使用 GitHub 账号登录

**2. 创建组织和项目**

- 导入你的 GitHub 仓库
- 获取 Organization key 和 Token

**3. 更新配置**

修改 `pom.xml`:

```xml
<properties>
    <sonar.host.url>https://sonarcloud.io</sonar.host.url>
    <sonar.organization>your-org-key</sonar.organization>
</properties>
```

**4. 运行分析**

```bash
mvn clean verify sonar:sonar \
  -Dsonar.organization=your-org-key \
  -Dsonar.login=YOUR_TOKEN
```

## 📝 详细配置

### pom.xml 配置说明

已添加的配置：

**1. JaCoCo 插件**（代码覆盖率）

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <!-- 自动生成覆盖率报告 -->
</plugin>
```

**2. SonarQube Scanner 插件**

```xml
<plugin>
    <groupId>org.sonarsource.scanner.maven</groupId>
    <artifactId>sonar-maven-plugin</artifactId>
    <version>3.10.0.2594</version>
</plugin>
```

### sonar-project.properties 说明

主要配置项：

```properties
# 项目标识
sonar.projectKey=online-learning-platform
sonar.projectName=Online Learning Platform

# 源代码路径
sonar.sources=src/main/java
sonar.tests=src/test/java

# 代码覆盖率报告
sonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml

# 排除规则（不分析的文件）
sonar.exclusions=**/model/**/*Umple*.java
```

## 📊 分析报告解读

### 关键指标

**1. Bugs（错误）**

- 🔴 严重: 立即修复
- 🟠 主要: 尽快修复
- 🟡 次要: 计划修复

**2. Vulnerabilities（安全漏洞）**

- 🔴 高危: 安全风险
- 🟠 中危: 潜在风险
- 🟡 低危: 建议修复

**3. Code Smells（代码异味）**

- 影响可维护性的代码模式
- 建议重构的代码

**4. Coverage（覆盖率）**

- 当前项目目标: 50%+
- 理想目标: 80%+

**5. Duplications（重复代码）**

- 目标: < 3%
- 重构建议

**6. Technical Debt（技术债务）**

- 修复所需时间估算
- 代码质量趋势

### Quality Gate（质量门禁）

默认规则：

- ✅ 新代码覆盖率 > 80%
- ✅ 新代码重复率 < 3%
- ✅ 新代码可维护性评级 ≥ A
- ✅ 新代码可靠性评级 ≥ A
- ✅ 新代码安全性评级 ≥ A

## 🎯 预期分析结果

基于当前代码库（240+ 测试），预计：

**优势**:

- ✅ 高测试覆盖率（模型层）
- ✅ 良好的包结构
- ✅ OCL 约束验证
- ✅ 状态机实现

**可能的改进点**:

- ⚠️ Controller/Service 层测试覆盖率
- ⚠️ JavaDoc 文档
- ⚠️ 代码重复（Umple 生成的代码）
- ⚠️ 方法复杂度（部分业务逻辑）

## 🔧 常见问题

### Q1: 启动 SonarQube 失败

**问题**: 端口 9000 被占用

```bash
# 解决: 查找并终止占用端口的进程
lsof -ti:9000 | xargs kill -9

# 或使用不同端口
docker run -d --name sonarqube \
  -p 9001:9000 \
  sonarqube:latest
```

### Q2: 分析失败 - 内存不足

**解决**: 增加 Maven 堆内存

```bash
export MAVEN_OPTS="-Xmx2048m"
mvn clean verify sonar:sonar
```

### Q3: 无法连接到 SonarQube

**检查清单**:

1. SonarQube 是否运行: `docker ps | grep sonarqube`
2. 端口是否正确: 默认 9000
3. Token 是否有效
4. 防火墙设置

### Q4: Coverage 为 0%

**原因**: JaCoCo 报告未生成

**解决**:

```bash
# 先运行测试生成覆盖率报告
mvn clean test

# 检查报告是否生成
ls -la target/site/jacoco/

# 再运行 SonarQube 分析
mvn sonar:sonar
```

## 📈 持续集成

### GitHub Actions 集成

创建 `.github/workflows/sonar.yml`:

```yaml
name: SonarQube Analysis

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]

jobs:
  sonar:
    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v3
        with:
          fetch-depth: 0

      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: "11"
          distribution: "temurin"

      - name: Cache SonarCloud packages
        uses: actions/cache@v3
        with:
          path: ~/.sonar/cache
          key: ${{ runner.os }}-sonar

      - name: Build and analyze
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
        run: |
          cd Java_online_platform
          mvn clean verify sonar:sonar \
            -Dsonar.organization=your-org \
            -Dsonar.host.url=https://sonarcloud.io
```

## 🎓 最佳实践

1. **定期分析**: 每次提交或 PR 触发
2. **修复优先级**: Bugs > Vulnerabilities > Code Smells
3. **覆盖率目标**: 逐步提升至 80%+
4. **代码审查**: 结合 SonarQube 报告
5. **技术债务**: 定期清理和重构

## 📚 更多资源

- [SonarQube 官方文档](https://docs.sonarqube.org/)
- [SonarCloud](https://sonarcloud.io/)
- [JaCoCo 文档](https://www.jacoco.org/jacoco/trunk/doc/)
- [代码质量最佳实践](https://www.sonarqube.org/features/clean-code/)

## 🆘 获取帮助

- 查看 SonarQube 日志: `docker logs sonarqube`
- 检查 Maven 输出: `mvn sonar:sonar -X`
- 官方社区: https://community.sonarsource.com/
