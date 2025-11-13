#!/bin/bash

# SonarQube 状态检查脚本

echo "========================================="
echo "  SonarQube 状态检查"
echo "========================================="
echo ""

# 检查 Docker 容器状态
echo "📦 Docker 容器状态:"
if docker ps -a | grep -q sonarqube; then
    docker ps -a | grep sonarqube | awk '{print "   状态: " $7 " " $8 " " $9}'
    CONTAINER_STATUS=$(docker ps | grep sonarqube | wc -l)
    if [ "$CONTAINER_STATUS" -gt 0 ]; then
        echo "   ✅ 容器正在运行"
    else
        echo "   ⏸️  容器已停止"
        echo ""
        echo "💡 启动容器: docker start sonarqube"
        exit 1
    fi
else
    echo "   ❌ 容器不存在"
    echo ""
    echo "💡 创建容器: docker run -d --name sonarqube -p 9000:9000 sonarqube:latest"
    exit 1
fi

echo ""
echo "🌐 服务状态:"

# 检查服务是否响应
HTTP_STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:9000/api/system/status 2>/dev/null)

if [ "$HTTP_STATUS" == "200" ]; then
    echo "   ✅ SonarQube 服务正在运行"
    
    # 获取详细状态
    STATUS_RESPONSE=$(curl -s http://localhost:9000/api/system/status 2>/dev/null)
    if echo "$STATUS_RESPONSE" | grep -q "UP"; then
        echo "   ✅ 状态: UP (就绪)"
        echo ""
        echo "========================================="
        echo "✅ SonarQube 已就绪！"
        echo "========================================="
        echo ""
        echo "🔗 访问地址: http://localhost:9000"
        echo "👤 默认账号: admin / admin"
        echo ""
        echo "📝 下一步:"
        echo "   1. 在浏览器中打开 http://localhost:9000"
        echo "   2. 使用 admin/admin 登录"
        echo "   3. 修改密码（首次登录要求）"
        echo "   4. 创建项目并生成 Token"
        echo "   5. 运行: ./run-sonar.sh YOUR_TOKEN"
        echo ""
    else
        echo "   ⏳ 状态: 正在初始化..."
        echo ""
        echo "💡 查看启动日志: docker logs -f sonarqube"
    fi
else
    echo "   ⏳ 服务尚未就绪（HTTP $HTTP_STATUS）"
    echo "   ⏰ SonarQube 正在启动中，通常需要 1-2 分钟..."
    echo ""
    echo "💡 实时查看启动日志:"
    echo "   docker logs -f sonarqube"
    echo ""
    echo "💡 再次检查状态:"
    echo "   ./check-sonar-status.sh"
fi

echo ""

