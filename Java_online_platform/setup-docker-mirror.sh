#!/bin/bash

# Docker 镜像源配置脚本 - macOS

echo "═══════════════════════════════════════════════════════════"
echo "  配置 Docker 国内镜像源"
echo "═══════════════════════════════════════════════════════════"
echo ""

echo "📝 步骤 1/4: 创建 Docker 配置目录"
echo ""
echo "执行以下命令（需要输入密码）:"
echo ""
echo "  sudo mkdir -p /etc/docker"
echo ""
read -p "按回车继续..."
sudo mkdir -p /etc/docker

echo ""
echo "✅ 目录创建成功"
echo ""

echo "📝 步骤 2/4: 创建配置文件"
echo ""
echo "将创建 /etc/docker/daemon.json 配置文件"
echo ""

# 创建配置文件内容
cat > /tmp/daemon.json << 'JSONEOF'
{
  "registry-mirrors": [
    "https://mirror.ccs.tencentyun.com",
    "https://docker.mirrors.ustc.edu.cn",
    "https://hub-mirror.c.163.com",
    "https://registry.docker-cn.com"
  ]
}
JSONEOF

echo "配置内容:"
cat /tmp/daemon.json
echo ""
read -p "确认写入配置？按回车继续..."

sudo cp /tmp/daemon.json /etc/docker/daemon.json
sudo chmod 644 /etc/docker/daemon.json

echo ""
echo "✅ 配置文件已创建"
echo ""

echo "📝 步骤 3/4: 重启 Docker"
echo ""
echo "⚠️  请手动重启 Docker Desktop:"
echo ""
echo "  1. 点击菜单栏的 Docker 图标"
echo "  2. 选择 'Restart'"
echo "  或者"
echo "  3. 完全退出 Docker，然后重新打开"
echo ""
read -p "重启完成后按回车继续..."

echo ""
echo "📝 步骤 4/4: 验证配置"
echo ""
echo "检查镜像源是否配置成功..."
docker info | grep -A 10 "Registry Mirrors" || echo "Docker 尚未就绪，请等待启动完成"

echo ""
echo "═══════════════════════════════════════════════════════════"
echo "✅ 配置完成！"
echo "═══════════════════════════════════════════════════════════"
echo ""
echo "🚀 现在可以尝试拉取 SonarQube 镜像："
echo ""
echo "  docker pull sonarqube:latest"
echo ""
echo "如果成功，运行："
echo ""
echo "  docker run -d --name sonarqube -p 9000:9000 sonarqube:latest"
echo ""

