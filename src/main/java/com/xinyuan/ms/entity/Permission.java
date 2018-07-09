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
@Table(name = "sys_permission")
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @ApiModelProperty(value = "权限id", name = "id", example = "0")
    private Long id;

    @ApiModelProperty(value = "父id", name = "pid", example = "1")
    @Column(name = "p_id")
    private Long pid;

    @ApiModelProperty(value = "权限名称", name = "name", example = "name")
    @Column(name = "name")
    private String name;

    @ApiModelProperty(value = "授权链接", name = "url", example = "url")
    @Column(name = "url")
    private String url;

    @ApiModelProperty(value = "删除标识 0未删除 1已删除", name = "deleted", example = "0")
    @Column(name = "deleted")
    private Integer deleted = 0;

}
