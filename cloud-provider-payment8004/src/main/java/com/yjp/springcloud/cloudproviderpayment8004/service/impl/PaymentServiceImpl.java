package com.yjp.springcloud.cloudproviderpayment8004.service.impl;

import com.yjp.springcloud.cloudproviderpayment8004.dao.PaymentDao;
import com.yjp.springcloud.entities.Payment;
import com.yjp.springcloud.cloudproviderpayment8004.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ClassName: PaymentServiceImpl
 * Description:
 * date: 2020/12/17 15:12
 *
 * @author yan
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
