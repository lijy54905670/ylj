package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.common.util.HttpUtils;
import com.xinyuan.ms.common.util.ResultUtil;
import com.xinyuan.ms.common.web.Message;
import com.xinyuan.ms.config.api.wx.WXPay;
import com.xinyuan.ms.config.api.wx.WXPayConstants;
import com.xinyuan.ms.config.api.wx.WXPayUtil;
import com.xinyuan.ms.config.api.wx.impl.WXPayConfigImpl;
import com.xinyuan.ms.entity.ProductOrder;
import com.xinyuan.ms.entity.User;
import com.xinyuan.ms.exception.UserException;
import com.xinyuan.ms.service.ProductOrderService;
import com.xinyuan.ms.service.UserService;
import com.xinyuan.ms.web.request.WXPayRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liang
 */
@Slf4j
@RestController
@RequestMapping("/wxpay")
@Api(description = "支付相关")
public class WXPayController {

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "微信支付", notes = "微信支付")
    @RequestMapping(value = "pay", method = RequestMethod.POST)
    public ResponseEntity<Message> pay(@RequestBody WXPayRequest wxPayRequest) {
        Message message = new Message();

        try {
            WXPayConfigImpl wxPayConfig = WXPayConfigImpl.getInstance();

            WXPay wxPay = new WXPay(wxPayConfig);

            //查询订单信息
            ProductOrder productOrder = productOrderService.findByNumber(wxPayRequest.getNumber());

            Long userId = productOrder.getUserId();

            User user = userService.get(userId);

            String openId = user.getOpenId();

            System.out.println();
            System.out.println(openId);
            System.out.println();

            if (productOrder == null) {
                throw UserException.PARAMETER_ANOMALY;
            }
            String hostAddress = InetAddress.getLocalHost().getHostAddress();

            //获得支付金额
            Double payment = productOrder.getPayment();
            String payMentString = payMant2String(payment);

            Map<String, String> mapData = new HashMap<>(10);

            //支付时候逇说明
            mapData.put("body", "易健在线-商城支付");
            //订单Id
            mapData.put("out_trade_no", productOrder.getNumber());
            //设备号码 传入WEB
            mapData.put("device_info", "WEB");
            //支付类型 人民币
            mapData.put("fee_type", "CNY");
            //总价格
//            mapData.put("total_fee", "1");
            mapData.put("total_fee", String.valueOf(payMentString));

            //终端IP
            mapData.put("spbill_create_ip", hostAddress);
            //通知的回调URL
            mapData.put("notify_url", "http://service.yiruibio.com/yirui_health/wxpay/notify");
            //交易类型
            mapData.put("trade_type", "JSAPI");
            //open_id
            mapData.put("openid", openId);

            Map<String, String> map = wxPay.unifiedOrder(mapData);

            //预支付id
            String prepayId = map.get("prepay_id");
            Map<String, String> payMap = new HashMap<>(5);
            payMap.put("appId", wxPayConfig.getAppID());
            payMap.put("timeStamp", WXPayUtil.getCurrentTimestamp() + "");
            payMap.put("nonceStr", WXPayUtil.generateNonceStr());
            payMap.put("signType", "MD5");
            payMap.put("package", "prepay_id=" + prepayId);
            String paySign = WXPayUtil.generateSignature(payMap, wxPayConfig.getKey());
            payMap.put("paySign", paySign);
            message.setData(payMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(message);
    }

    @ApiOperation(value = "微信查询", notes = "微信支付")
    @RequestMapping(value = "orderquery", method = RequestMethod.POST)
    public ResponseEntity<Message> orderquery(@RequestBody WXPayRequest request) {
        Message message = new Message();

        try {
            ProductOrder productOrder = productOrderService.findByNumber(request.getNumber());

            WXPay wxPay = new WXPay(WXPayConfigImpl.getInstance());
            //获得支付金额
            Map<String, String> mapData = new HashMap<>(10);
            //订单Id
            mapData.put("out_trade_no", request.getNumber());

            Map<String, String> map = wxPay.orderQuery(mapData);
            String TRADE_STATE = "trade_state";
            String trade_state = null;

            if (map.containsKey(TRADE_STATE)) {

                String totalFee = map.get("total_fee");

                String payment = payMant2String(productOrder.getPayment());

                if (payment.equals(totalFee)) {
                    trade_state = map.get(TRADE_STATE);

                    System.out.println("================" + trade_state);

                    System.out.println("============" + map);
                    if (WXPayConstants.SUCCESS.equals(trade_state)) {
                        //支付
                        /*productOrderService.pay(productOrder);*/
                        message = ResultUtil.success();
                    } else {
                        //取消订单
                        /*productOrderService.cancel(productOrder);*/
                        message = ResultUtil.error(1111, "订单支付失败");
                    }
                } else {
                    message = ResultUtil.error(1112, "订单价格不同");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(message);
    }

    @ApiOperation(value = "微信回调", notes = "微信支付")
    @RequestMapping(value = "notify", method = RequestMethod.POST)
    public ResponseEntity<Message> wxNotify(HttpServletRequest request, HttpServletResponse response) {
        Message message = null;

        try {
            InputStream inStream = request.getInputStream();
            String resultStr = HttpUtils.inputSteam2String(inStream);
            Map<String, String> map = WXPayUtil.xmlToMap(resultStr);

            String RETURN_CODE = "return_code";
            String return_code = null;
            if (map.containsKey(RETURN_CODE)) {
                return_code = map.get(RETURN_CODE);
            }
            if (WXPayConstants.SUCCESS.equals(return_code)) {

                for (String s : map.keySet()) {
                    log.info(s + ".............." + map.get(s));
                }
                log.info("返回参数：" + resultStr);
                String xml = "<xml> <return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
                response.getWriter().write(xml);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(message);
    }


    public String payMant2String(Double payMent) {
        String returnValue = "";

        if (payMent != null) {
            BigDecimal b = new BigDecimal(payMent);
            b = b.multiply(new BigDecimal(100));
            int paymentInt = b.setScale(2, BigDecimal.ROUND_HALF_UP).intValue();
            returnValue = String.valueOf(paymentInt);
        }

        return returnValue;
    }
}
