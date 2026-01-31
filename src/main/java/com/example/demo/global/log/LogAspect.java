package com.example.demo.global.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LogAspect {

    // 컨트롤러, 서비스, 레포지토리 모두 감시
    @Pointcut("execution(* com.example.demo.domain..*(..))")
    public void allDomain() {}

    @Around("allDomain()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        // 실행되는 메소드 이름과 파라미터 값 가져오기
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        try {
            log.info("▶ [START] {} | Args: {}", methodName, Arrays.toString(args));

            // 실제 메소드 실행
            Object result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis() - start;

            // 결과값 로그 (너무 길면 잘라도 됨)
            log.info("◀ [END] {} | Time: {}ms | Result: {}", methodName, executionTime, result);
            return result;

        } catch (Throwable e) {
            long executionTime = System.currentTimeMillis() - start;
            // 에러 발생 시 빨간색(Error 레벨)으로 어떤 값 때문에 터졌는지 기록
            log.error("🚨 [EXCEPTION] {} | Time: {}ms | Args: {} | Exception: {}",
                    methodName, executionTime, Arrays.toString(args), e.getMessage());
            throw e;
        }
    }
}