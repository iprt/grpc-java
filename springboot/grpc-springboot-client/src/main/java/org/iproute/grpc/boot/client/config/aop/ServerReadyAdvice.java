package org.iproute.grpc.boot.client.config.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.iproute.grpc.boot.client.context.GrpcApplicationContext;
import org.iproute.grpc.boot.client.expection.ServerNotReadyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ServerReadyAdvice
 *
 * @author tech@intellij.io
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
@Aspect
@Slf4j
public class ServerReadyAdvice {
    private final GrpcApplicationContext grpcApplicationContext;

    @Pointcut(
            "("
                    + "execution(* org.iproute.grpc.boot.client..*.*(..))"
                    + "&&"
                    + "@annotation(org.iproute.grpc.boot.client.config.anno.ServerReadyThen)"
                    + ")"
    )
    public void pointCut() {
    }


    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        if (grpcApplicationContext.serverReady()) {
            return joinPoint.proceed();
        } else {
            log.error("Grpc Server Not Ready;AroundAdvice on {}#{}", joinPoint.getTarget().getClass(), joinPoint.getSignature().getName());
            throw ServerNotReadyException.create();
        }
    }

}
