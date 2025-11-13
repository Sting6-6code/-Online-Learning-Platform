# SonarQube 安装替代方案

## 问题: Docker 镜像下载超时

```
failed to resolve reference "docker.io/library/sonarqube:latest":
context deadline exceeded
```

这是网络连接 Docker Hub 超时的问题。以下是几个解决方案：

---

## 🚀 方案 1: 使用国内 Docker 镜像源（推荐）

### 配置 Docker 镜像加速器

**步骤 1: 创建或编辑 Docker 配置文件**

```bash
# macOS
sudo mkdir -p /etc/docker
sudo nano /etc/docker/daemon.json
```

**步骤 2: 添加镜像源配置**

在 `daemon.json` 中添加（如果文件为空，直接粘贴）：

```json
{
  "registry-mirrors": [
    "https://mirror.ccs.tencentyun.com",
    "https://docker.mirrors.ustc.edu.cn",
    "https://hub-mirror.c.163.com"
  ]
}
```

**步骤 3: 重启 Docker**

```bash
# macOS: 从菜单栏重启 Docker Desktop
# 或者
killall Docker && open /Applications/Docker.app
```

**步骤 4: 再次尝试拉取镜像**

```bash
docker pull sonarqube:latest
docker run -d --name sonarqube -p 9000:9000 sonarqube:latest
```

---

## 🚀 方案 2: 使用指定版本镜像（更小更快）

不使用 `latest` 标签，使用社区版指定版本：

```bash
# 使用社区版 LTS 版本
docker pull sonarqube:9.9-community

# 运行
docker run -d --name sonarqube \
  -p 9000:9000 \
  sonarqube:9.9-community
```

---

## 🚀 方案 3: 手动下载镜像（离线方式）

如果网络一直不稳定，可以：

1. **找有稳定网络的机器下载**

   ```bash
   docker pull sonarqube:latest
   docker save sonarqube:latest -o sonarqube.tar
   ```

2. **传输到您的机器**

   ```bash
   # 使用 USB 或其他方式传输 sonarqube.tar
   ```

3. **导入镜像**
   ```bash
   docker load -i sonarqube.tar
   docker run -d --name sonarqube -p 9000:9000 sonarqube:latest
   ```

---

## 🚀 方案 4: 使用本地 SonarQube（不使用 Docker）

### macOS 使用 Homebrew

```bash
# 安装 SonarQube
brew install sonarqube

# 启动 SonarQube
brew services start sonarqube

# 或者前台运行
sonar console
```

访问: http://localhost:9000

### 手动安装

1. **下载 SonarQube**

   ```bash
   # 从清华镜像下载（更快）
   wget https://mirrors.tuna.tsinghua.edu.cn/sonarqube/sonarqube-9.9.0.65466.zip

   # 或从官网
   # https://www.sonarsource.com/products/sonarqube/downloads/
   ```

2. **解压并运行**

   ```bash
   unzip sonarqube-9.9.0.65466.zip
   cd sonarqube-9.9.0.65466/bin/macosx-universal-64
   ./sonar.sh start
   ```

3. **查看日志**
   ```bash
   ./sonar.sh status
   tail -f ../../logs/sonar.log
   ```

---

## 🚀 方案 5: 使用 SonarCloud（在线服务，最简单）

完全避免本地安装，直接使用在线服务：

### 步骤：

1. **注册 SonarCloud**

   - 访问: https://sonarcloud.io
   - 使用 GitHub 账号登录（免费）

2. **创建组织和项目**

   - 点击 "+" → "Analyze new project"
   - 可以导入 GitHub 仓库或手动创建

3. **获取 Token**

   - My Account → Security → Generate Tokens

4. **更新项目配置**

   编辑 `pom.xml`:

   ```xml
   <properties>
       <sonar.host.url>https://sonarcloud.io</sonar.host.url>
       <sonar.organization>your-org-key</sonar.organization>
   </properties>
   ```

5. **运行分析**
   ```bash
   mvn clean verify sonar:sonar \
     -Dsonar.organization=your-org-key \
     -Dsonar.login=YOUR_TOKEN
   ```

**优点**:

- ✅ 无需本地安装
- ✅ 永久免费（公开项目）
- ✅ 自动更新
- ✅ CI/CD 集成简单

---

## 🔧 调试 Docker 网络问题

### 检查 Docker 配置

```bash
# 查看当前镜像源
docker info | grep -A 10 "Registry Mirrors"

# 测试网络连接
ping registry-1.docker.io
curl -I https://registry-1.docker.io/v2/
```

### 使用代理

如果您有代理：

```bash
# 临时设置代理
docker pull sonarqube:latest \
  --network host \
  --env HTTP_PROXY=http://proxy.example.com:8080
```

---

## 💡 推荐方案对比

| 方案               | 难度   | 速度       | 推荐度     |
| ------------------ | ------ | ---------- | ---------- |
| 方案 1: 国内镜像源 | ⭐⭐   | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ |
| 方案 2: 指定版本   | ⭐     | ⭐⭐⭐⭐   | ⭐⭐⭐⭐   |
| 方案 3: 离线安装   | ⭐⭐⭐ | ⭐⭐⭐     | ⭐⭐⭐     |
| 方案 4: 本地安装   | ⭐⭐   | ⭐⭐⭐⭐   | ⭐⭐⭐⭐   |
| 方案 5: SonarCloud | ⭐     | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ |

---

## 🎯 建议

**如果时间紧急**: 使用 **方案 5 (SonarCloud)**，最快最简单

**如果需要本地**: 优先尝试 **方案 1 (配置镜像源)**

**如果网络一直不稳定**: 使用 **方案 4 (Homebrew 本地安装)**

---

## 📞 需要帮助？

告诉我您想用哪个方案，我可以提供详细的操作步骤！
