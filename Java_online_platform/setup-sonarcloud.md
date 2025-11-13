# SonarCloud 快速配置指南

## 🚀 5 分钟快速开始

### 步骤 1: 注册 SonarCloud

1. 访问: https://sonarcloud.io
2. 点击 "Sign up" 或 "Log in"
3. 选择 "Sign up with GitHub" (使用 GitHub 账号登录)
4. 授权 SonarCloud 访问您的 GitHub

---

### 步骤 2: 创建组织和项目

**选项 A: 导入 GitHub 仓库（推荐）**

1. 登录后，点击右上角 "+" 图标
2. 选择 "Analyze new project"
3. 选择您的 GitHub 组织或个人账号
4. 找到并选择 `-Online-Learning-Platform` 仓库
5. 点击 "Set Up"

**选项 B: 手动创建项目**

1. 点击 "+" → "Create new organization"
2. 输入组织名称（如: `your-username`）
3. 点击 "+" → "Analyze new project"
4. 选择 "Manually"
5. 输入项目信息：
   - Project key: `online-learning-platform`
   - Display name: `Online Learning Platform`

---

### 步骤 3: 获取访问令牌 (Token)

1. 点击右上角头像 → "My Account"
2. 选择 "Security" 标签
3. 在 "Generate Tokens" 部分：
   - Token name: `online-learning-platform-token`
   - Type: `Project Analysis Token` 或 `Global Analysis Token`
   - Expires in: 选择过期时间（建议 90 天或更长）
4. 点击 "Generate"
5. **立即复制并保存这个 Token**（只显示一次！）

Token 格式类似：`squ_1234567890abcdef1234567890abcdef12345678`

---

### 步骤 4: 配置项目

更新 `pom.xml`，添加 SonarCloud 特定配置：

```xml
<properties>
    <!-- 现有配置保持不变 -->

    <!-- SonarCloud 配置 -->
    <sonar.organization>YOUR_ORGANIZATION_KEY</sonar.organization>
    <sonar.projectKey>online-learning-platform</sonar.projectKey>
    <sonar.projectName>Online Learning Platform</sonar.projectName>
    <sonar.host.url>https://sonarcloud.io</sonar.host.url>
</properties>
```

**注意**: 将 `YOUR_ORGANIZATION_KEY` 替换为您在 SonarCloud 上的组织 key（可在组织设置中找到）

---

### 步骤 5: 运行分析

**方式 A: 使用脚本（推荐）**

创建 `run-sonarcloud.sh`:

```bash
#!/bin/bash

# 替换为您的 Token
SONAR_TOKEN="YOUR_SONAR_TOKEN_HERE"

# 替换为您的组织 key
SONAR_ORG="YOUR_ORGANIZATION_KEY"

echo "开始 SonarCloud 分析..."

mvn clean verify sonar:sonar \
  -Dsonar.token="$SONAR_TOKEN" \
  -Dsonar.organization="$SONAR_ORG" \
  -Dsonar.projectKey="online-learning-platform" \
  -Dsonar.projectName="Online Learning Platform" \
  -Dsonar.host.url="https://sonarcloud.io"

echo "分析完成！访问 https://sonarcloud.io 查看结果"
```

运行:

```bash
chmod +x run-sonarcloud.sh
./run-sonarcloud.sh
```

**方式 B: 直接命令行**

```bash
mvn clean verify sonar:sonar \
  -Dsonar.token="YOUR_TOKEN" \
  -Dsonar.organization="YOUR_ORG_KEY" \
  -Dsonar.projectKey="online-learning-platform" \
  -Dsonar.projectName="Online Learning Platform" \
  -Dsonar.host.url="https://sonarcloud.io"
```

---

### 步骤 6: 查看分析结果

1. 分析完成后（约 2-5 分钟）
2. 访问: https://sonarcloud.io
3. 进入您的项目
4. 查看：
   - 代码质量评级（A-E）
   - Bug、漏洞、代码异味数量
   - 代码覆盖率
   - 重复代码统计
   - 详细问题列表

---

## ✅ 优势

- ✅ **无需本地安装** - 完全云端运行
- ✅ **永久免费** - 公开项目完全免费
- ✅ **自动化集成** - 可配置 GitHub Actions 自动分析
- ✅ **实时分析** - 每次 push 自动触发分析
- ✅ **详细报告** - 比本地 SonarQube 更快更直观

---

## 🔧 故障排除

### 问题 1: Token 权限不足

**解决**: 确保 Token 类型为 "Project Analysis Token" 或 "Global Analysis Token"

### 问题 2: 组织 key 错误

**解决**:

1. 访问 https://sonarcloud.io/account/organizations
2. 找到您的组织
3. 复制正确的 organization key

### 问题 3: Maven 构建失败

**解决**: 先运行 `mvn clean test` 确保所有测试通过

---

## 📚 后续步骤

1. **配置质量门禁**: 在 SonarCloud 项目设置中配置质量标准
2. **集成 CI/CD**: 配置 GitHub Actions 自动分析
3. **团队协作**: 邀请团队成员查看和修复问题
4. **持续改进**: 根据分析结果逐步提升代码质量

---

## 🎯 下一步

完成配置后，您可以：

- 查看详细的代码质量报告
- 修复发现的 Bug 和漏洞
- 提高代码覆盖率
- 减少技术债务

祝您使用愉快！ 🎉
