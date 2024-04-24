package org.iproute.grpc.boot.client.context.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.grpc.boot.client.context.GrpcApplicationContext;
import org.iproute.grpc.boot.client.context.ServerConn;
import org.iproute.grpc.boot.client.context.SharedOperator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * GrpcApplicationContextImpl
 *
 * @author devops@kubectl.net
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Slf4j
public class GrpcApplicationContextImpl implements GrpcApplicationContext {
    private final SharedOperator sharedOperator;

    @Value("${spring.application.name}")
    private String applicationName;

    private ApplicationContext applicationContext;

    @Override
    public String getApplicationName() {
        return this.applicationName;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        log.debug("ApplicationContext Aware");
        this.applicationContext = applicationContext;
    }

    @Override
    public ApplicationContext getSpringApplicationContext() {
        return this.applicationContext;
    }

    @Override
    public boolean serverReady() {
        return sharedOperator.getServerReady();
    }

    @Override
    public ServerConn serverConn() {
        return sharedOperator.getSeverConn();
    }

}
