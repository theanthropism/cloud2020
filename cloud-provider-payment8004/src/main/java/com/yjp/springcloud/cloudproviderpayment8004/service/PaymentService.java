package com.yjp.springcloud.cloudproviderpayment8004.service;

import com.yjp.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * ClassName: PaymentService
 * Description:
 * date: 2020/12/17 15:11
 *
 * @author yan
 */
public interface PaymentService {
    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);
}
