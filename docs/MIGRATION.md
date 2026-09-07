# 翼安智链命名迁移

## 本次变更

- GitHub 仓库：`425002486-bot/yian-platform-server`、`425002486-bot/yian-platform-web`。
- 后端 Maven groupId：`cn.yian.platform`；根 artifactId：`yian`。
- 后端模块目录和 artifactId 由 `yudao-*` 改为 `yian-*`；自动配置类等 `Yudao*` 类改为 `Yian*`，引用同步更新。
- 前端包名改为 `yian-platform-web`；README、启动标识、文档入口及登录页使用翼安智链品牌。
- 去除原框架产品截图和页面推广条，保留第三方许可证、作者声明与技术参考链接。

## 兼容边界

底层 Java 包 `cn.iocoder.yudao`、已有 `yudao.*` 配置键、数据库名称与表结构保留。
这些标识可能被 Redis 类型信息、已有部署配置、数据库记录或外部集成引用。
本次不清空 Redis、不重建数据库、不改写已有 Git 历史。
因此这次是项目品牌和工程命名迁移，不是隐藏第三方来源。

## 升级已有环境

1. 在独立目录构建新版本；旧版本的 target、IDE 启动配置和 Docker 构建路径不能直接复用。
2. 部署产物改为 `yian-server/target/yian-server.jar`；Java 主类改为
   `cn.iocoder.yudao.server.YianServerApplication`。
3. 后端应用名改为 `yian-server`；如果监控、消息队列分组或服务发现依赖应用名，升级时显式设置
   `SPRING_APPLICATION_NAME` 为现有值，或统一迁移消费分组及监控规则。不要在未规划时混用新旧消费分组。
4. 根据 `docs/CONFIGURATION.md` 注入实际凭据。旧 YAML 中的明文凭据不再作为分发默认值。
5. 前端 `.env.local` 不再跟踪。先保留自己的本地文件，再参照 `.env.local.example` 检查环境变量。
6. 前后端 API 加密设置必须一致；本地默认登录账号及密码不再随构建提供。

## 开源前的剩余工作

仓库历史与其他演示配置仍需单独进行敏感信息审查。当前文件清理不等于历史密钥撤销。
在确认曾提交的真实凭据已轮换、业务数据允许公开之前，仓库继续保持原有可见性。

## 本地工作副本

本次变更在独立 Git worktree 中完成，原开发目录及其未提交登录配置保留。
切换新版本时请使用整理后的工作副本，或在处理好本地配置后拉取远端更新。
