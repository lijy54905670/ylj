package com.xinyuan.ms.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liang
 */
@Data
public class BoundWXRequest {

    @ApiModelProperty(value = "微信code", name = "code")
    private String code;

    @ApiModelProperty(value = "用户id", name = "userId")
    private Long userId;
}
