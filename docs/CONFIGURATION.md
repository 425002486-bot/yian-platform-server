# 环境变量配置

以下敏感配置已改为从环境变量读取；未设置时为空。只配置实际启用的服务。

| 文件 | 配置项 | 环境变量 |
| --- | --- | --- |
| `yian-server\src\main\resources\application-dev.yaml` | `spring.datasource.dynamic.datasource.master.password` | `SPRING_DATASOURCE_DYNAMIC_DATASOURCE_MASTER_PASSWORD` |
| `yian-server\src\main\resources\application-dev.yaml` | `spring.datasource.dynamic.datasource.slave.password` | `SPRING_DATASOURCE_DYNAMIC_DATASOURCE_SLAVE_PASSWORD` |
| `yian-server\src\main\resources\application-dev.yaml` | `spring.rabbitmq.password` | `SPRING_RABBITMQ_PASSWORD` |
| `yian-server\src\main\resources\application-dev.yaml` | `spring.boot.admin.client.password` | `SPRING_BOOT_ADMIN_CLIENT_PASSWORD` |
| `yian-server\src\main\resources\application-dev.yaml` | `wx.mp.secret` | `WX_MP_SECRET` |
| `yian-server\src\main\resources\application-dev.yaml` | `wx.miniapp.secret` | `WX_MINIAPP_SECRET` |
| `yian-server\src\main\resources\application-dev.yaml` | `justauth.type.DINGTALK.client-secret` | `JUSTAUTH_TYPE_DINGTALK_CLIENT_SECRET` |
| `yian-server\src\main\resources\application-dev.yaml` | `justauth.type.WECHAT_ENTERPRISE.client-secret` | `JUSTAUTH_TYPE_WECHAT_ENTERPRISE_CLIENT_SECRET` |
| `yian-server\src\main\resources\application-dev.yaml` | `justauth.type.ALIPAY.client-secret` | `JUSTAUTH_TYPE_ALIPAY_CLIENT_SECRET` |
| `yian-server\src\main\resources\application-local.yaml` | `spring.datasource.dynamic.datasource.master.password` | `SPRING_DATASOURCE_DYNAMIC_DATASOURCE_MASTER_PASSWORD` |
| `yian-server\src\main\resources\application-local.yaml` | `spring.datasource.dynamic.datasource.slave.password` | `SPRING_DATASOURCE_DYNAMIC_DATASOURCE_SLAVE_PASSWORD` |
| `yian-server\src\main\resources\application-local.yaml` | `spring.rabbitmq.password` | `SPRING_RABBITMQ_PASSWORD` |
| `yian-server\src\main\resources\application-local.yaml` | `spring.boot.admin.client.password` | `SPRING_BOOT_ADMIN_CLIENT_PASSWORD` |
| `yian-server\src\main\resources\application-local.yaml` | `wx.mp.secret` | `WX_MP_SECRET` |
| `yian-server\src\main\resources\application-local.yaml` | `wx.miniapp.secret` | `WX_MINIAPP_SECRET` |
| `yian-server\src\main\resources\application-local.yaml` | `justauth.type.DINGTALK.client-secret` | `JUSTAUTH_TYPE_DINGTALK_CLIENT_SECRET` |
| `yian-server\src\main\resources\application-local.yaml` | `justauth.type.WECHAT_ENTERPRISE.client-secret` | `JUSTAUTH_TYPE_WECHAT_ENTERPRISE_CLIENT_SECRET` |
| `yian-server\src\main\resources\application-local.yaml` | `justauth.type.ALIPAY.client-secret` | `JUSTAUTH_TYPE_ALIPAY_CLIENT_SECRET` |
| `yian-server\src\main\resources\application.yaml` | `mybatis-plus.encryptor.password` | `MYBATIS_PLUS_ENCRYPTOR_PASSWORD` |
| `yian-server\src\main\resources\application.yaml` | `spring.ai.qianfan.api-key` | `SPRING_AI_QIANFAN_API_KEY` |
| `yian-server\src\main\resources\application.yaml` | `spring.ai.qianfan.secret-key` | `SPRING_AI_QIANFAN_SECRET_KEY` |
| `yian-server\src\main\resources\application.yaml` | `spring.ai.zhipuai.api-key` | `SPRING_AI_ZHIPUAI_API_KEY` |
| `yian-server\src\main\resources\application.yaml` | `spring.ai.openai.api-key` | `SPRING_AI_OPENAI_API_KEY` |
| `yian-server\src\main\resources\application.yaml` | `spring.ai.anthropic.api-key` | `SPRING_AI_ANTHROPIC_API_KEY` |
| `yian-server\src\main\resources\application.yaml` | `spring.ai.stabilityai.api-key` | `SPRING_AI_STABILITYAI_API_KEY` |
| `yian-server\src\main\resources\application.yaml` | `spring.ai.dashscope.api-key` | `SPRING_AI_DASHSCOPE_API_KEY` |
| `yian-server\src\main\resources\application.yaml` | `spring.ai.minimax.api-key` | `SPRING_AI_MINIMAX_API_KEY` |
| `yian-server\src\main\resources\application.yaml` | `spring.ai.moonshot.api-key` | `SPRING_AI_MOONSHOT_API_KEY` |
| `yian-server\src\main\resources\application.yaml` | `spring.ai.deepseek.api-key` | `SPRING_AI_DEEPSEEK_API_KEY` |
| `yian-server\src\main\resources\application.yaml` | `yudao.ai.gemini.api-key` | `YUDAO_AI_GEMINI_API_KEY` |
| `yian-server\src\main\resources\application.yaml` | `yudao.ai.doubao.api-key` | `YUDAO_AI_DOUBAO_API_KEY` |
| `yian-server\src\main\resources\application.yaml` | `yudao.ai.hunyuan.api-key` | `YUDAO_AI_HUNYUAN_API_KEY` |
| `yian-server\src\main\resources\application.yaml` | `yudao.ai.siliconflow.api-key` | `YUDAO_AI_SILICONFLOW_API_KEY` |
| `yian-server\src\main\resources\application.yaml` | `yudao.ai.xinghuo.secretKey` | `YUDAO_AI_XINGHUO_SECRETKEY` |
| `yian-server\src\main\resources\application.yaml` | `yudao.ai.baichuan.api-key` | `YUDAO_AI_BAICHUAN_API_KEY` |
| `yian-server\src\main\resources\application.yaml` | `yudao.ai.midjourney.api-key` | `YUDAO_AI_MIDJOURNEY_API_KEY` |
| `yian-server\src\main\resources\application.yaml` | `yudao.ai.web-search.api-key` | `YUDAO_AI_WEB_SEARCH_API_KEY` |
| `yian-server\src\main\resources\application.yaml` | `yudao.api-encrypt.request-key` | `YUDAO_API_ENCRYPT_REQUEST_KEY` |
| `yian-server\src\main\resources\application.yaml` | `yudao.api-encrypt.response-key` | `YUDAO_API_ENCRYPT_RESPONSE_KEY` |
| `yian-server\src\main\resources\application.yaml` | `yudao.trade.express.kd-niao.api-key` | `YUDAO_TRADE_EXPRESS_KD_NIAO_API_KEY` |
