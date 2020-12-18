package com.yjp.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.io.Serializable;

/**
 * ClassName: Payment
 * Description:
 * date: 2020/12/17 13:54
 *
 * @author yan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {

    private Long id;
    /**支付流水号*/
    private String serial;
}
