package com.yjp.springcloud.cloudproviderpayment8006.controller;

import com.yjp.springcloud.entities.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * className: PaymentController
 * description:
 * date: 2021/3/25 10:49
 *
 * @author yan
 */
@RestController
@Slf4j
public class PaymentController {

    @GetMapping(value="/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
       return new CommonResult("200","闫金鹏", UUID.randomUUID());
    }
}
