package org.iproute.grpc.boot.server.config;

import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.iproute.grpc.boot.server.interceptor.GRpcConnectionCountInterceptor;
import org.springframework.context.annotation.Configuration;

/**
 * GRpcConfig
 *
 * @author devops@kubectl.net
 */
@Configuration
@Slf4j
public class GRpcServerInterceptorConfig {

    @GrpcGlobalServerInterceptor
    public ServerInterceptor connectionCountInterceptor() {
        return new GRpcConnectionCountInterceptor();
    }

}
