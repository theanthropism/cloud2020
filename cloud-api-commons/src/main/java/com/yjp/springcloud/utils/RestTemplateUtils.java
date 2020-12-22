package com.yjp.springcloud.utils;

import com.yjp.springcloud.entities.CommonResult;
import com.yjp.springcloud.entities.Payment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * ClassName: RestTemplateUtils
 * Description:
 * date: 2020/12/21 15:28
 *
 * @author yan
 */
@Component
public class RestTemplateUtils<T> {
    @Resource
    private RestTemplate restTemplate;

    private static RestTemplateUtils restTemplateUtils;

    @PostConstruct
    public void init() {
        restTemplateUtils = this;
    }
    
    /**
     * @Author yan
     * @Description //TODO 普通get
     * @Date 2020/12/21 17:30
     */
    public static <T> T getForObject(String url, Class<T> responseType, Object... uriVariables) throws org.springframework.web.client.RestClientException{
        return restTemplateUtils.restTemplate.getForObject(url,responseType);
    }
    /**
     * @Author yan
     * @Description //TODO 带token的get
     * @Date 2020/12/22 11:55
     */
    public static <T> T getExchange(String url, String token, Class<T> responseType, java.lang.Object... uriVariables) throws org.springframework.web.client.RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        return restTemplateUtils.restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(headers), responseType).getBody();
    }
    /**
     * @Author yan
     * @Description //TODO 普通post
     * @Date 2020/12/22 16:53
     */
    public static <T> T restTemplate(String url,Object request, java.lang.Class<T> responseType, java.lang.Object... uriVariables) throws org.springframework.web.client.RestClientException {
        return restTemplateUtils.restTemplate.postForObject(url,request,responseType);
    }
    /**
     * @Author yan
     * @Description //TODO 带token的post
     * @Date 2020/12/22 17:00
     */
    public static <T> T postForEntity(String url,String token, Object request, java.lang.Class<T> responseType, java.lang.Object... uriVariables) throws org.springframework.web.client.RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        return restTemplateUtils.restTemplate.postForEntity(url,new HttpEntity<Object>(request, headers),responseType).getBody();
    }
}

