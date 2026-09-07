# 后端启动说明

## 环境

- JDK 17、Maven 3.9、MySQL 8、Redis。
- 前端位于 https://github.com/425002486-bot/yian-platform-web 。
- 当前主分支为 `master-jdk17`；启用模块以根目录 `pom.xml` 为准。

## 数据库准备

在独立空数据库中检查并执行 `sql/mysql/ruoyi-vue-pro.sql` 基础脚本，再检查并依次执行：

1. `sql/mysql/yian-mes-bootstrap.sql`
2. `sql/mysql/yian-ai-bootstrap.sql`
3. `sql/mysql/yian-return-material-bootstrap.sql`
4. `sql/mysql/yian-menus.sql`

基础脚本包含演示数据；菜单脚本包含更新和删除操作。已有业务数据库应先备份并评审脚本，
不要将这些初始化脚本当作无条件可重复执行的生产迁移。
当前仓库尚未提供经过完整全新部署验收的一键安装程序。

## 运行配置

默认启用 `local` profile。数据库、Redis、模型服务及第三方集成的配置位于
`yian-server/src/main/resources/application*.yaml`。
凭据通过环境变量注入，变量清单见 [CONFIGURATION.md](CONFIGURATION.md)。
也可在仓库外准备自己的 YAML，并通过 Spring Boot 的 `spring.config.additional-location` 加载。

数据库 URL、用户名、密码、Redis 地址及密码应填写为自己的环境。
`yudao.api-encrypt` 若启用，前后端的算法与请求、响应密钥必须一致；密钥不得写入仓库。
前端构建变量会进入浏览器，不能用来保存服务端私钥或第三方 API 密钥。

## 构建与启动

```bash
mvn -B -DskipTests package
java -jar yian-server/target/yian-server.jar
```

默认端口 `48080`，管理 API 前缀 `/admin-api`，接口文档 `/swagger-ui`。

## AI 接入

翼安业务的配置入口为 `yian.ai`。先通过 `GET /admin-api/yian/ai/capabilities` 检查能力状态，
再配置模型、OCR 或模拟模式。前端会区分 `mock`、`model`、`fallback` 结果。
资料解析和诊断草稿不等同于经过人工确认的维修结论。

## 验证

命名迁移的自动检查和本次实际执行结果见 [VALIDATION.md](VALIDATION.md)。
单元测试通过不代表已连接真实数据库、OCR 或大模型完成部署验收。
