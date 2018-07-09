package com.xinyuan.ms.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**

 * @author hwz
 */
@Data

public class UserVo  {

    @ApiModelProperty(value = "主键", name = "id", example = "0")
    private Long id;

    @ApiModelProperty(value = "昵称", name = "nickname", example = "张三")
    private String nickname;

    @ApiModelProperty(value = "用户名（唯一）", name = "username", example = "username")
    private String username;

    @ApiModelProperty(value = "密码", name = "password", example = "password")
    @Column(name = "password")
    private String password;

    @ApiModelProperty(value = "头像", name = "icon", example = "/upload/afd5c1ee-409e-43b2-86b4-b239e0e91fd6.jpg")
    private String icon;

    @ApiModelProperty(value = "简介", name = "introduction", example = "简介")
    private String introduction;

    @ApiModelProperty(value = "性别，1男2女", name = "sex", example = "1")
    private Integer sex;

    @ApiModelProperty(value = "年龄", name = "age", example = "18")
    private Integer age;

    @ApiModelProperty(value = "收藏话题数量", name = "collectTopicNum", example = "0")
    private Integer collectTopicNum;

    @ApiModelProperty(value = "我关注的人数量", name = "concertUserNum", example = "0")
    private Integer concertUserNum;

    @ApiModelProperty(value = "关注我的人数量", name = "concertedUserNum", example = "0")
    private Integer concertedUserNum;

    @ApiModelProperty(value = "我收到的赞数量", name = "likedNum", example = "0")
    private Integer likedNum;

    @ApiModelProperty(value = "我被收藏的数量", name = "collectedNum", example = "0")
    private Integer collectedNum;

    @ApiModelProperty(value = "登录次数", name = "login_count", example = "0")
    private Integer loginCount;

    @ApiModelProperty(value = "状态(0,1)，0停用，1启用", name = "status", example = "0")
    private Integer status;

    @ApiModelProperty(value = "最后登录时间", name = "loginTime", example = "2018-04-12 10:55:23")
    private Date loginTime;

    @ApiModelProperty(value = "注册时间", name = "createTime", example = "2018-04-12 10:55:23")
    private Date createTime;

    @ApiModelProperty(value = "会员等级（1.普通会员，2高级会员，3顶级会员）", name = "vip", example = "1")
    private Integer vip;

    @ApiModelProperty(value = "消费金额", name = "money", example = "0")
    private Double money ;

}
