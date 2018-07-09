package com.xinyuan.ms.web.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: hwz
 * @Date: Created in 11:59 2018/4/26.
 */
@Data
public class MessagesRequest {

    @ApiModelProperty(value = "电话号码", name = "mobile", example = "15666666666")
    public String mobile;

}
