package cn.iocoder.yudao.framework.banner.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * Logs the application documentation entry point after startup.
 *
 * @author 芋道源码
 */
@Slf4j
public class BannerApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) {
        log.info("翼安智链启动成功；项目文档：https://github.com/425002486-bot/yian-platform-server#readme");
    }
}
