package com.xinyuan.ms.web.vo;
import com.xinyuan.ms.entity.UserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**

 * @author hwz
 */
@Data
public class UserBackstageVo {

    @ApiModelProperty(value = "用户信息", name = "userVo")
    private UserVo userVo;

    @ApiModelProperty(value = "角色信息，rid为（1管理员，2医生，3用户，4客服）", name = "userRole")
    private UserRole userRole;

    @ApiModelProperty(value = "信息完整度", name = "percent")
    private Integer percent;

}
