package com.yjp.springcloud.controller;

import com.yjp.springcloud.entities.CommonResult;
import com.yjp.springcloud.entities.Payment;
import com.yjp.springcloud.utils.RestTemplateUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * ClassName: OrderController
 * Description: 订单系统
 * date: 2020/12/18 14:42
 *
 * @author yan
 */
@RestController
@Slf4j
@Api(tags = "支付相关-api")
public class OrderController {

    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @PostMapping("/consumer/payment/create")
    public CommonResult<Payment> create(@RequestBody Payment payment){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token");
        return RestTemplateUtils.postForEntity(PAYMENT_URL+"/payment/create","token",payment,CommonResult.class);
    }

    @ApiOperation("查询用户")
    @GetMapping("/consumer/payment/get/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token鉴权",paramType = "header",dataType = "string",required = true),
            @ApiImplicitParam(name = "id", value = "账户id",dataType = "long",required = true, paramType = "query")
    })
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return RestTemplateUtils.getExchange(PAYMENT_URL+"/payment/get/"+id,"sjfjs",CommonResult.class);
    }
}
