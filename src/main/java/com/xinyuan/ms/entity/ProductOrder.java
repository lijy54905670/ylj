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
@Table(name = "m_order")
public class ProductOrder implements Serializable {

    @Id
    @ApiModelProperty(value = "主键", name = "id", example = "0")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ApiModelProperty(value = "订单编号", name = "number", example = "12333112")
    @Column(name = "number")
    private String number;

    @ApiModelProperty(value = "原订单id", name = "oldId", example = "12333112")
    @Column(name = "old_id")
    private Long oldId;

    @ApiModelProperty(value = "采样盒编号", name = "collectionNumber", example = "12333112")
    @Column(name = "collection_number")
    private String collectionNumber;

    @ApiModelProperty(value = "用户id", name = "userId", example = "1")
    @Column(name = "user_id")
    private Long userId;
    @ApiModelProperty(value = "地址信息", name = "receiverId", example = "1")
    @Column(name = "receiver_id")
    private Long receiverId;

    @ApiModelProperty(value = "付款方式1.支付宝2.快钱", name = "paymentType", example = "0")
    @Column(name = "payment_type")
    private Integer paymentType;

    @ApiModelProperty(value = "应付款", name = "payment", example = "66")
    @Column(name = "payment")
    private Double payment;

    @ApiModelProperty(value = "邮费", name = "freight", example = "10")
    @Column(name = "freight")
    private Double freight;

    @ApiModelProperty(value = "付款时间", name = "paymentTime", example = "2018-04-12 10:55:23")
    @Column(name = "payment_time",columnDefinition="timestamp")
    private String paymentTime;

    @ApiModelProperty(value = "发货时间", name = "sendTime", example = "2018-04-12 10:55:23")
    @Column(name = "send_time",columnDefinition="timestamp")
    private String sendTime;

    @ApiModelProperty(value = "完成时间", name = "endTime", example = "2018-04-12 10:55:23")
    @Column(name = "end_time",columnDefinition="timestamp")
    private String endTime;

    @ApiModelProperty(value = "收货时间", name = "takeTime", example = "2018-04-12 10:55:23")
    @Column(name = "take_time",columnDefinition="timestamp")
    private String takeTime;

    @ApiModelProperty(value = "寄回时间", name = "sendBackTime", example = "2018-04-12 10:55:23")
    @Column(name = "send_back_time",columnDefinition="timestamp")
    private String sendBackTime;

    @ApiModelProperty(value = "检测时间", name = "checkTime", example = "2018-04-12 10:55:23")
    @Column(name = "check_time",columnDefinition="timestamp")
    private String checkTime;

    @ApiModelProperty(value = "报告时间", name = "reportTime", example = "2018-04-12 10:55:23")
    @Column(name = "report_time",columnDefinition="timestamp")
    private String reportTime;

    @ApiModelProperty(value = "下单时间", name = "ordersTime", example = "2018-04-12 10:55:23")
    @Column(name = "orders_time",columnDefinition="timestamp")
    private String ordersTime;

    @ApiModelProperty(value = "发起售后申请时间", name = "applyTime", example = "2018-04-12 10:55:23")
    @Column(name = "apply_time",columnDefinition="timestamp")
    private String applyTime;

    @ApiModelProperty(value = "申请理由", name = "applyReason", example = "2018-04-12 10:55:23")
    @Column(name = "apply_reason")
    private String applyReason;

    @ApiModelProperty(value = "售后回复", name = "applyReply", example = "2018-04-12 10:55:23")
    @Column(name = "apply_reply")
    private String applyReply;

    @ApiModelProperty(value = "快递公司代号", name = "shipperCode", example = "ZTO")
    @Column(name = "shipper_code")
    private String shipperCode;

    @ApiModelProperty(value = "快递单号", name = "logisticCode", example = "12345678")
    @Column(name = "logistic_code")
    private String logisticCode;

    @ApiModelProperty(value = "申请图片", name = "applyImages", example = "[{\"/upload/afd5c1ee-409e-43b2-86b4-b239e0e91fd6.jpg\"}]")
    @Column(name = "apply_images")
    private String applyImages;

    @ApiModelProperty(value = "状态（1下单/未付款，2付款，3发货，4收货，5寄回，6检测中，7报告已发布，8已完成，9已通过，10未通过，11发采样盒，12已取消，13待受理，14收采样盒）", name = "status", example = "1")
    @Column(name = "status")
    private Integer status;

    @ApiModelProperty(value = "订单类型1正常，2基因组，3换货，4退货", name = "type", example = "1")
    @Column(name = "type")
    private Integer type;

    @ApiModelProperty(value = "删除标识 0未删除 1已删除", name = "deleted", example = "0")
    @Column(name = "deleted")
    private Integer deleted = 0;
}
