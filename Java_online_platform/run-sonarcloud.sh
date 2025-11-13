#!/bin/bash

# SonarCloud 分析脚本
# 在线学习平台项目

echo "═══════════════════════════════════════════════════════════"
echo "  开始 SonarCloud 代码质量分析"
echo "═══════════════════════════════════════════════════════════"
echo ""

# SonarCloud 配置


echo "📋 配置信息:"
echo "   Organization: $SONAR_ORG"
echo "   Project Key: $SONAR_PROJECT_KEY"
echo "   Host: https://sonarcloud.io"
echo ""

echo "🔨 步骤 1/2: 清理并运行测试（生成覆盖率报告）..."
echo ""

mvn clean verify

if [ $? -ne 0 ]; then
    echo ""
    echo "❌ 测试失败，请检查并修复测试错误后重试"
    exit 1
fi

echo ""
echo "✅ 测试通过！"
echo ""

echo "🚀 步骤 2/2: 上传到 SonarCloud 分析..."
echo ""

mvn sonar:sonar \
  -Dsonar.token="$SONAR_TOKEN" \
  -Dsonar.organization="$SONAR_ORG" \
  -Dsonar.projectKey="$SONAR_PROJECT_KEY" \
  -Dsonar.projectName="Online Learning Platform" \
  -Dsonar.host.url="https://sonarcloud.io" \
  -Dsonar.java.source=11 \
  -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml

if [ $? -eq 0 ]; then
    echo ""
    echo "═══════════════════════════════════════════════════════════"
    echo "  ✅ 分析完成！"
    echo "═══════════════════════════════════════════════════════════"
    echo ""
    echo "🎉 查看分析结果:"
    echo "   https://sonarcloud.io/project/overview?id=$SONAR_PROJECT_KEY"
    echo ""
    echo "或访问:"
    echo "   https://sonarcloud.io/organizations/$SONAR_ORG/projects"
    echo ""
else
    echo ""
    echo "❌ 分析上传失败，请检查网络连接和配置"
    exit 1
fi

