package org.iproute.grpc.boot.server.config;

import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.iproute.grpc.boot.server.interceptor.GrpcConnectionCountInterceptor;
import org.springframework.context.annotation.Configuration;

/**
 * GrpcServerInterceptorConfig
 *
 * @author devops@kubectl.net
 */
@Configuration
@Slf4j
public class GrpcServerInterceptorConfig {

    @GrpcGlobalServerInterceptor
    public ServerInterceptor connectionCountInterceptor() {
        return new GrpcConnectionCountInterceptor();
    }

}
