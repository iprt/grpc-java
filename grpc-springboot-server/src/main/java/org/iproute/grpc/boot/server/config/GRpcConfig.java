package org.iproute.grpc.boot.server.config;

import io.grpc.ServerInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.iproute.grpc.boot.server.interceptor.ConnectionCountInterceptor;
import org.iproute.grpc.boot.server.interceptor.ConnectionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * GRpcConfig
 *
 * @author devops@kubectl.net
 */
@Configuration
public class GRpcConfig {

    @Bean
    @GrpcGlobalServerInterceptor
    public ServerInterceptor connectInterceptor() {
        return new ConnectionInterceptor();
    }

    @Bean
    @GrpcGlobalServerInterceptor
    public ServerInterceptor connectionCountInterceptor() {
        return new ConnectionCountInterceptor();
    }

}
