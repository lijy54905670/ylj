package com.xinyuan.ms.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**

 * @author hwz
 */
@Data

public class UserConcertVo {

    @ApiModelProperty(value = "用户信息", name = "userVo", example = "0")
    private UserVo userVo;

    @ApiModelProperty(value = "是否已关注", name = "isConcert", example = "false")
    private String isConcert;

    @ApiModelProperty(value = "是否已邀请", name = "isInvite", example = "false")
    private String isInvite;

}
