package com.yjp.springcloud.service.impl;

import com.yjp.springcloud.dao.PaymentDao;
import com.yjp.springcloud.entities.Payment;
import com.yjp.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
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
