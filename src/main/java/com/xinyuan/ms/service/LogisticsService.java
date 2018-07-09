package com.xinyuan.ms.service;

import com.xinyuan.ms.common.account.LogisticsAccount;
import com.xinyuan.ms.common.util.HttpUtils;
import com.xinyuan.ms.entity.Logistics;
import com.xinyuan.ms.mapper.LogisticsRepository;
import com.xinyuan.ms.web.request.LogisticsRequest;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hwz
 */
@Service
public class LogisticsService extends BaseService<LogisticsRepository, Logistics, Long> {

    public static String Logistics(LogisticsRequest logisticsRequest) {
        String host = "http://wdexpress.market.alicloudapi.com";
        String path = "/gxali";
        String method = "GET";
        System.out.println("请先替换成自己的AppCode");
        Map<String, String> headers = new HashMap<>(5);

        //格式为:Authorization:APP_CODE 83359fd73fe11248385f570e3c139xxx
        headers.put("Authorization", "APPCODE " + LogisticsAccount.APP_CODE);

        Map<String, String> querys = new HashMap<>(5);
        querys.put("n", logisticsRequest.getLogisticCode());
        querys.put("t", logisticsRequest.getShipperCode());

        String logistics = null;
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //System.out.println(response.toString()); //输出头部
            logistics = EntityUtils.toString(response.getEntity());
            //System.out.println(logistics); //输出json
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logistics;
    }

}
