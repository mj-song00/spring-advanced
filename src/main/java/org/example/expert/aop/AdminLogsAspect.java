package org.example.expert.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;


@Slf4j
@Component
@Aspect
public class AdminLogsAspect {


    @Pointcut("@annotation(org.example.expert.annotation.AdminLogs)")
    private void AdminLogsAnnotation() {
    }

    @Around("AdminLogsAnnotation()")
    public Object adminAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        LocalDateTime startTime = LocalDateTime.now();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String userId = String.valueOf(request.getAttribute("userId"));
            log.info("userId ={} ", userId);
            String url = String.valueOf(request.getAttribute("requestURL"));
            log.info("url = {} ", url);
        }

        Object proceed = joinPoint.proceed();

        log.info("startTime = {}", String.valueOf(startTime));
        return proceed;
    }
}
