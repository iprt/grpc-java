package org.iproute.grpc.boot.server.config;

import io.grpc.ServerInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.iproute.grpc.boot.server.interceptor.DisconnectInterceptor;
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
    public ServerInterceptor disconnectInterceptor() {
        return new DisconnectInterceptor();
    }

}
