# 🚀 SonarQube 快速开始指南

## ⚡ 5 分钟快速上手

### 步骤 1: 启动 SonarQube（首次使用）

```bash
# 使用 Docker 启动（最简单）
docker run -d --name sonarqube -p 9000:9000 sonarqube:latest

# 等待 1-2 分钟启动完成
# 浏览器访问: http://localhost:9000
# 登录: admin / admin（首次登录会要求修改密码）
```

### 步骤 2: 创建项目和 Token

1. 在 SonarQube 中点击 **"Create Project Manually"**
2. Project key: `online-learning-platform`
3. Display name: `Online Learning Platform`
4. 点击 **"Set Up"**
5. 选择 **"Locally"**
6. 点击 **"Generate"** 生成 Token
7. **复制并保存 Token**

### 步骤 3: 运行分析

```bash
cd Java_online_platform

# 使用脚本运行（推荐）
./run-sonar.sh YOUR_TOKEN

# 或直接使用 Maven
mvn clean verify sonar:sonar -Dsonar.login=YOUR_TOKEN
```

### 步骤 4: 查看结果

分析完成后，访问：

```
http://localhost:9000/dashboard?id=online-learning-platform
```

---

## 📊 已配置的功能

✅ **JaCoCo 代码覆盖率**

- 自动生成覆盖率报告
- 与测试集成
- 最低要求: 50% 覆盖率

✅ **SonarQube Maven 插件**

- 一键运行分析
- 自动上传结果
- 质量门禁检查

✅ **项目配置**

- 源代码: `src/main/java`
- 测试代码: `src/test/java`
- 排除 Umple 生成的代码

---

## 📁 生成的文件

```
Java_online_platform/
├── pom.xml                      # ✅ 已添加 JaCoCo + SonarQube 插件
├── sonar-project.properties     # ✅ SonarQube 配置文件
├── run-sonar.sh                 # ✅ 一键运行脚本
├── SONARQUBE.md                 # ✅ 详细文档
└── QUICK_START_SONAR.md        # ✅ 本文件
```

---

## 🎯 当前项目指标

基于 240+ 单元测试：

- **测试覆盖率**: 已生成 JaCoCo 报告
- **分析的类**: 33 个类
- **测试状态**: 240 passed, 1 skipped
- **构建状态**: ✅ BUILD SUCCESS

---

## 💡 常用命令

```bash
# 查看覆盖率报告（HTML）
open target/site/jacoco/index.html

# 只运行测试和覆盖率
mvn clean test

# 完整分析（测试 + SonarQube）
./run-sonar.sh YOUR_TOKEN

# 查看 SonarQube 日志
docker logs -f sonarqube

# 停止 SonarQube
docker stop sonarqube

# 启动已存在的 SonarQube
docker start sonarqube

# 完全删除 SonarQube
docker rm -f sonarqube
```

---

## 🐛 常见问题

### Q: 端口 9000 被占用？

```bash
# 使用不同端口
docker run -d --name sonarqube -p 9001:9000 sonarqube:latest
# 然后更新 pom.xml 中的 sonar.host.url
```

### Q: 分析失败 - 连接超时？

```bash
# 检查 SonarQube 是否运行
docker ps | grep sonarqube

# 查看启动日志
docker logs sonarqube
```

### Q: Token 在哪里生成？

1. 登录 SonarQube
2. 我的账户 → Security → Generate Tokens
3. 输入名称并生成

---

## 📈 下一步

完成首次分析后：

1. **查看报告**: 了解代码质量现状
2. **修复问题**: 优先处理 Bugs 和 Vulnerabilities
3. **提高覆盖率**: 为 Service 和 Controller 添加测试
4. **持续集成**: 配置 CI/CD 自动分析

详细信息请查看 **SONARQUBE.md**

---

## 🎓 报告指标说明

- **Bugs**: 代码错误，应立即修复
- **Vulnerabilities**: 安全漏洞
- **Code Smells**: 代码异味，影响可维护性
- **Coverage**: 测试覆盖率
- **Duplications**: 重复代码百分比
- **Security Hotspots**: 需要审查的安全敏感代码

---

**需要帮助？** 查看 SONARQUBE.md 获取完整文档。
