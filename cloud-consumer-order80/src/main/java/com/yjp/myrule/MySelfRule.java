package com.yjp.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yan
 * @description  自定义ribon访问方式(不能放到启动目录下)
 * @date 2021/3/25 17:03
 */
@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule(){
        return new RandomRule();    //随机访问
    }
}
