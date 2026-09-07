# 验证记录

执行日期：2026-09-07。

| 检查 | 结果 |
| --- | --- |
| JDK 17，`mvn -B -DskipTests package` | 23 个启用模块打包成功 |
| AI 集成控制器与服务测试 | 14 项通过 |
| 工单退料控制器与服务测试 | 4 项通过 |
| Maven 模块与自动配置检查 | 40 个 POM、5111 个 Java 文件、32 个 Spring 自动配置注册通过 |
| 原始 MIT 许可证 | 与整理前文件保持一致 |

复现命名检查：

```bash
python script/verify-branding.py
```

复现本次业务测试：

```bash
mvn -B -pl yian-module-mes -am test \
  -Dtest=YianAiIntegrationControllerTest,YianAiIntegrationServiceImplTest,YianWorkorderMaterialReturnControllerTest,YianWorkorderMaterialReturnServiceImplTest \
  -Dsurefire.failIfNoSpecifiedTests=false
```

PowerShell 中请将每个 `-D...` 参数用单引号包围。
测试使用模拟依赖，未访问真实模型、OCR、生产数据库或生产 Redis。
未执行线上部署、数据库迁移、历史重写或仓库公开操作。
