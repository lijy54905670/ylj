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
@Table(name = "sys_user_role")
public class UserRole implements Serializable {
    @Id
    @ApiModelProperty(value = "主键", name = "id", example = "0")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "u_id")
    @ApiModelProperty(value = "用户ID", name = "u_id", example = "0")
    private Long uid;

    @Column(name = "r_id")
    @ApiModelProperty(value = "角色的ID", name = "r_id", example = "0")
    private Long rid;

    @ApiModelProperty(value = "删除标识 0未删除 1已删除", name = "deleted", example = "0")
    @Column(name = "deleted")
    private Integer deleted = 0;
}
