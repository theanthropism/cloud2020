package com.yjp.springcloud.cloudproviderpayment8004.dao;

import com.yjp.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * ClassName: PaymentDao
 * Description:
 * date: 2020/12/17 14:56
 *
 * @author yan
 */
@Mapper
public interface PaymentDao {
    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);
}
