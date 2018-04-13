package com.xinyuan.ms.web.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hzx
 * @Description:
 * @date 2018/4/918:27
 */
@Data
public class Conditions {

    @ApiModelProperty(value = "参数键(属性名)", name = "key" ,example = "id")
    private String key;

    @ApiModelProperty(value = "参数值(属性值)", name = "value" ,example = "5")
    private Object value;

    @ApiModelProperty(value = "查询条件（EQUAL:等于 , GREATERTHAN:大于 , LESSTHAN:小于 , LIKE:模糊查询 , GREATERTHANEQUAL:大于等于 , LESSTHANEQUAL:小于等于）", name = "condition" ,example = "EQUAL")
    private String condition;
}
