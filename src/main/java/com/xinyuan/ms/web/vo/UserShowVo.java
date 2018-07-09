package com.xinyuan.ms.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**

 * @author hwz
 */
@Data
public class UserShowVo {

    @ApiModelProperty(value = "用户信息", name = "userVo", example = "0")
    private UserVo userVo;

    @ApiModelProperty(value = "是否已关注", name = "isConcert", example = "false")
    private String isConcert;

}
