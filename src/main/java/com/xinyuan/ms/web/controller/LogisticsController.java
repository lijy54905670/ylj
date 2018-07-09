package com.xinyuan.ms.web.controller;
import com.xinyuan.ms.service.LogisticsService;
import com.xinyuan.ms.web.request.LogisticsRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: hwz
 * @Date: Created in 15:15 2018/4/10.
 */
@Api(description = "物流管理")
@RestController
@RequestMapping("/backstage/logistics")
public class LogisticsController {

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "物流查询", notes = "物流")
    @ApiImplicitParam(name = "logisticsRequest", value = "logisticsRequest", required = true, dataType = "LogisticsRequest")
    @RequestMapping(value = "logistics", method = RequestMethod.POST)
    public ResponseEntity<String> logistics(@RequestBody LogisticsRequest logisticsRequest) {

        String logistics = LogisticsService.Logistics(logisticsRequest);

        return ResponseEntity.ok(logistics);
    }

}
