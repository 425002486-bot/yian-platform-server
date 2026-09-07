# 翼安智链 · 后端服务

**无人机资产与维修运维管理平台**

Java 17 · Spring Boot 3.5 · MyBatis Plus · MySQL · Redis · Spring AI

[后端仓库](https://github.com/425002486-bot/yian-platform-server) ·
[前端仓库](https://github.com/425002486-bot/yian-platform-web) ·
[部署说明](docs/GETTING_STARTED.md) · [第三方声明](THIRD_PARTY_NOTICES.md)

## 项目介绍


翼安智链面向无人机维修与运维团队，将设备档案、电池信息、维修工单、备件流转和操作留痕集中管理，
并提供资料解析、飞行日志解析及诊断草稿生成等 AI 辅助能力。

项目采用前后端分离架构，目前以无人机运维业务的 MVP 实现为主。AI 输出用于辅助人工判断，
不同能力的可用性取决于模型、OCR 服务及环境配置；模拟结果、真实模型结果与回退结果会区分标识。


## 业务能力


| 业务领域 | 当前代码包含的能力 |
| --- | --- |
| 资产档案 | 无人机台账、设备详情、电池档案及资料附件 |
| 维修工单 | 工单管理、维修处理、领料与退料记录 |
| 备件库存 | 物料、库存、出入库及工单关联的备件流转 |
| 规则与站点 | 站点管理、业务规则配置及电池规则评估 |
| 运维视图 | 业务工作台、统计看板及审计相关页面 |
| AI 辅助 | 资产资料解析、飞行日志解析、诊断草稿生成与结果查询 |
| 平台基础 | 用户、角色、菜单权限、多租户、文件管理和日志 |


## 业务流程

```mermaid
flowchart LR
  A[设备与电池建档] --> B[建立维修工单]
  B --> C[资料与飞行日志解析]
  C --> D[人工确认诊断与维修方案]
  D --> E[领料、维修与退料]
  E --> F[工单记录与运维追溯]
```

## 仓库职责

本仓库提供业务 API、权限控制、数据持久化及 AI 集成。

```text
yian-server/          服务启动入口与运行配置
yian-module-mes/      无人机运维、资产、工单、库存及业务 AI 集成
yian-module-ai/       模型接入和通用 AI 能力
yian-module-system/   用户、角色、权限和租户
yian-module-infra/    文件、日志和基础设施
yian-framework/       公共组件与 Spring Boot 自动配置
yian-dependencies/    Maven 依赖版本管理
sql/mysql/            基础数据库及翼安业务初始化脚本
docs/                 启动与迁移说明
```

其他保留模块是可选扩展，以根 `pom.xml` 实际启用的模块为准；它们不代表翼安智链已完成对应业务验收。

## 快速开始

准备 JDK 17、Maven 3.9、MySQL 8 和 Redis，先按[部署说明](docs/GETTING_STARTED.md)准备数据库及环境变量。

```bash
git clone --branch master-jdk17 https://github.com/425002486-bot/yian-platform-server.git
cd yian-platform-server
mvn -B -DskipTests package
java -jar yian-server/target/yian-server.jar
```

后端默认端口为 `48080`；管理 API 使用 `/admin-api` 前缀。接口文档默认入口为 `/swagger-ui`，
生产环境应按部署需要限制或关闭文档入口。

## 开发与贡献

提交问题时请说明页面或接口、复现步骤、期望结果和实际结果，并删除日志中的凭据与业务数据。
欢迎通过 Issue 讨论问题和改进建议，通过 Pull Request 提交变更。

## 许可证

本仓库沿用 [MIT License](LICENSE)。第三方代码及其衍生修改保留原有版权与许可声明，
详见 [Third-party notices](THIRD_PARTY_NOTICES.md)。
