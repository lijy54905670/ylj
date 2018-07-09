package com.xinyuan.ms.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: hwz
 * @Date: Created in 11:59 2018/4/26.
 */
@Data
public class LogisticsRequest{

    @ApiModelProperty(value = "快递单号", name = "logisticCode", example = "111")
    public String logisticCode;

    @ApiModelProperty(value = "快递公司代号", name = "shipperCode", example = "ZTO")
    private String shipperCode;

}
