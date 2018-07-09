package com.xinyuan.ms.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author hwz
 */
@Data
@Entity
@Table(name = "sys_permission_role")
public class PermissionRole implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @ApiModelProperty(value = "角色对权限id", name = "id", example = "0")
    private Long id;

    @Column(name = "p_id")
    @ApiModelProperty(value = "权限ID", name = "p_id", example = "0")
    private Long pid;

    @Column(name = "r_id")
    @ApiModelProperty(value = "角色ID", name = "r_id", example = "0")
    private Long rid;

    @ApiModelProperty(value = "删除标识 0未删除 1已删除", name = "deleted", example = "0")
    @Column(name = "deleted")
    private Integer deleted = 0;
}
