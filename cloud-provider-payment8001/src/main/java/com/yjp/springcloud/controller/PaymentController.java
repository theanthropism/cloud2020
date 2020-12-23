package com.yjp.springcloud.controller;

import cn.hutool.http.HttpRequest;
import com.yjp.springcloud.Enums.JsonResultEnum;
import com.yjp.springcloud.entities.CommonResult;
import com.yjp.springcloud.entities.Payment;
import com.yjp.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClassName: PaymentController
 * Description:
 * date: 2020/12/17 15:15
 *
 * @author yan
 */
@Slf4j
@RestController
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value="/payment/create")
    public CommonResult create(@RequestBody Payment payment,HttpServletRequest request){
        int result = paymentService.create(payment);
        log.info("+++++++++++=插入结果："+result);
        if(result>0){
            return new CommonResult("200","插入成功!serverPort:"+serverPort,result);
        }else {
            return new CommonResult("444","插入失败!",null);
        }
    }

    @GetMapping(value="/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id,HttpServletRequest request){
        log.info("jsfksflk"+request.getHeader("Authorization"));
        Payment payment = paymentService.getPaymentById(id);
        log.info("+++++++++++=查询结果："+payment);
        if(payment != null){
            return new CommonResult("200","查询成功!!serverPort:"+serverPort,payment);
        }else {
            return new CommonResult("444","没有对应的记录!查询id:"+id,null);
        }
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery(HttpServletRequest request){
        log.info("jsfksflk"+request.getHeader("Authorization"));
        List<String> service = discoveryClient.getServices();
        service.forEach(x->{
            log.info("+++++++++++++"+x);
        });
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        instances.forEach(x->{
            log.info(x.getServiceId()+"\t"+x.getHost()+"\t"+x.getPort()+"\t"+x.getUri());
        });
        return this.discoveryClient;

    }
}
