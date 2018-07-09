package com.xinyuan.ms.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liang
 */
@Data
public class WXPayRequest {

    @ApiModelProperty(value = "订单编号", name = "number")
    private String number;

}
