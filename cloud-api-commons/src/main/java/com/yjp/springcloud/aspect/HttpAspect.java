package com.yjp.springcloud.aspect;

/**
 * @Author yan
 * @Description //TODO 通用的请求、入参、出参
 * @Date 2020/12/23 11:58
 */
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Component
public class HttpAspect {

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);
    private static final String TRACE_ID = "traceId";
    @Pointcut("execution(public * com.*.*.controller.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (StringUtils.isBlank(MDC.get(TRACE_ID))) {
            String traceId = UUID.randomUUID().toString();
            traceId = traceId.replaceAll("-", "");
            MDC.put(TRACE_ID, traceId);
        }
        HttpServletRequest request = attributes.getRequest();
        //url
        logger.info("请求接口----request url={}", request.getRequestURL());
        //参数
        Object[] args = joinPoint.getArgs();
        Stream<?> stream = ArrayUtils.isEmpty(args) ? Stream.empty() : Arrays.asList(args).stream();
        List<Object> logArgs = stream
                .filter(arg->(arg instanceof java.io.Serializable)
                || (arg instanceof java.io.Externalizable)
                || (!(arg instanceof HttpServletRequest)
                                && !(arg instanceof HttpServletResponse)
                                && !(arg instanceof StandardMultipartHttpServletRequest)
                                && !(arg instanceof MultipartFile)
                                && !(arg instanceof MultipartFile[]))
                ).collect(Collectors.toList());
        //过滤后序列化无异常
        String string = null;
        try {
            string = JSON.toJSONString(logArgs);
        } catch (JSONException e) {
            logger.error("################!无法序列化#######################");
        } catch (Exception e) {
        e.printStackTrace();
        logger.error("################序列化出现异常#######################");
    }
        logger.info("请求的参数是----request args={}", ArrayUtils.isEmpty(args)?"": string);
    }

    @After("log()")
    public void doAfter() {
//        logger.info("请求方法结束");
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        if (!Objects.isNull(object)) {
            logger.info("返回的参数是----response={}", JSON.toJSONString(object));
        }
    }
}