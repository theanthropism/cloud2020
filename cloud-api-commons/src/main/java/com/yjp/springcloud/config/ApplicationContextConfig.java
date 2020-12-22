package com.yjp.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * ClassName: ApplicationContextConfig
 * Description:
 * date: 2020/12/21 14:43
 *
 * @author yan
 */
@Configuration
public class ApplicationContextConfig {

    @Bean
    @LoadBalanced           //使用LoadBalanced注解赋予RestTemplate负载均衡的能力
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
