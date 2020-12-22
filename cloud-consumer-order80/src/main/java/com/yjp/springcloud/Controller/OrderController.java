package com.yjp.springcloud.Controller;

import com.yjp.springcloud.entities.CommonResult;
import com.yjp.springcloud.entities.Payment;
import com.yjp.springcloud.utils.RestTemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: OrderController
 * Description: 订单系统
 * date: 2020/12/18 14:42
 *
 * @author yan
 */
@RestController
@Slf4j
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

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return RestTemplateUtils.getExchange(PAYMENT_URL+"/payment/get/"+id,"sjfjs",CommonResult.class);
    }
}
