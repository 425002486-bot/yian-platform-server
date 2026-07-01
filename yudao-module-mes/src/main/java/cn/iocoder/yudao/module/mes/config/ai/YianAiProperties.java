package cn.iocoder.yudao.module.mes.config.ai;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "yian.ai")
public class YianAiProperties {

    /**
     * Enable deterministic mock results by default so the workflow can run before a real model is configured.
     */
    private boolean mockEnabled = true;

    /**
     * Optional explicit chat model id. If empty, the AI module default chat model will be used.
     */
    private Long chatModelId;

    /**
     * Maximum extracted file preview length sent to the model.
     */
    private int maxPreviewChars = 4000;
}
