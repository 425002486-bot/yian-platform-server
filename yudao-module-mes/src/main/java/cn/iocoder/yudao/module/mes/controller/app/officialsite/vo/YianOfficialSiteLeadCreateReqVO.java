package cn.iocoder.yudao.module.mes.controller.app.officialsite.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "用户 APP - 翼安智链官网咨询线索创建 Request VO")
@Data
public class YianOfficialSiteLeadCreateReqVO {

    @Schema(description = "联系人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张先生")
    @NotBlank(message = "联系人姓名不能为空")
    @Size(max = 30, message = "联系人姓名不能超过 30 个字符")
    private String contactName;

    @Schema(description = "公司名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "深圳市某某科技有限公司")
    @NotBlank(message = "公司名称不能为空")
    @Size(max = 100, message = "公司名称不能超过 100 个字符")
    private String companyName;

    @Schema(description = "联系电话", requiredMode = Schema.RequiredMode.REQUIRED, example = "13800138000")
    @NotBlank(message = "联系电话不能为空")
    @Pattern(regexp = "^[0-9+\\-\\s]{6,20}$", message = "联系电话格式不正确")
    private String phoneNumber;

    @Schema(description = "联系邮箱", example = "demo@yianchain.com")
    @Email(message = "联系邮箱格式不正确")
    @Size(max = 100, message = "联系邮箱不能超过 100 个字符")
    private String email;

    @Schema(description = "关注场景", example = "企业机队运营")
    @Size(max = 50, message = "关注场景不能超过 50 个字符")
    private String interestedScene;

    @Schema(description = "需求描述", example = "希望了解可信放飞与 AI 初诊能力的落地方案")
    @Size(max = 500, message = "需求描述不能超过 500 个字符")
    private String message;
}
