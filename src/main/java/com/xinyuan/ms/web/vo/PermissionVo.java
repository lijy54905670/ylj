package com.xinyuan.ms.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**

 * @author hwz
 */
@Data
public class PermissionVo {

    @ApiModelProperty(value = "权限id", name = "id", example = "0")
    private Long id;

    @ApiModelProperty(value = "父id", name = "pid", example = "1")
    private Long pid;

    @ApiModelProperty(value = "权限名称", name = "name", example = "name")
    private String name;

    @ApiModelProperty(value = "授权链接", name = "url", example = "url")
    private String url;

    @ApiModelProperty(value = "是否有权限，1是0否", name = "state", example = "0")
    private int status;

    @ApiModelProperty(value = "子模块", name = "child", example = "0")
    private List<PermissionVo> child;

}
