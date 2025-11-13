#!/bin/bash

# SonarQube 代码质量检测脚本
# 使用方法: ./run-sonar.sh [sonar_token]

echo "========================================="
echo "  Online Learning Platform"
echo "  SonarQube 代码质量分析"
echo "========================================="
echo ""

# 检查 SonarQube 是否运行
echo "🔍 检查 SonarQube 服务状态..."
SONAR_URL="http://localhost:9000"
if curl -s "$SONAR_URL/api/system/status" > /dev/null 2>&1; then
    echo "✅ SonarQube 服务正在运行"
else
    echo "❌ SonarQube 服务未运行"
    echo ""
    echo "请先启动 SonarQube:"
    echo "  方法1 - Docker: docker run -d --name sonarqube -p 9000:9000 sonarqube:latest"
    echo "  方法2 - 本地: 下载并运行 SonarQube"
    echo ""
    echo "启动后访问: http://localhost:9000"
    echo "默认账号: admin/admin"
    exit 1
fi

echo ""
echo "🧹 清理旧的构建产物..."
mvn clean

echo ""
echo "🧪 运行测试并生成覆盖率报告..."
mvn test

echo ""
echo "📊 运行 SonarQube 分析..."

# 如果提供了 token
if [ -n "$1" ]; then
    echo "使用提供的 token 进行分析..."
    mvn sonar:sonar -Dsonar.login="$1"
else
    echo "提示: 您可以在 SonarQube 中生成 token 并使用:"
    echo "  ./run-sonar.sh YOUR_TOKEN"
    echo ""
    echo "现在使用默认配置运行..."
    mvn sonar:sonar
fi

echo ""
echo "========================================="
echo "✅ 分析完成!"
echo "========================================="
echo ""
echo "📊 查看报告: http://localhost:9000/dashboard?id=online-learning-platform"
echo ""

