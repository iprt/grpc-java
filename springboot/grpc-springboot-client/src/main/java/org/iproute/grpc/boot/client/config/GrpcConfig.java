package org.iproute.grpc.boot.client.config;

import io.grpc.ClientInterceptor;
import io.grpc.ClientTransportFilter;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelConfigurer;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.iproute.grpc.boot.client.config.filter.MonitoringClientTransportFilter;
import org.iproute.grpc.boot.client.config.interceptor.GrpcConnectionClientInterceptor;
import org.iproute.grpc.boot.client.context.SharedOperator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * GrpcConfig
 *
 * @author tech@intellij.io
 */
@Configuration
@Slf4j
public class GrpcConfig {
    public static final String GRPC_SERVER_INSTANCE = "grpc-server";

    @Bean
    public ClientTransportFilter monitoringClientTransportFilter(SharedOperator sharedOperator) {
        return new MonitoringClientTransportFilter(sharedOperator);
    }

    /**
     * Adds a client transport filter to the channel builder, if it is an instance of NettyChannelBuilder.
     * <p>
     * <a href="https://yidongnan.github.io/grpc-spring-boot-starter/zh-CN/client/configuration.html#clientInterceptor">GrpcChannelConfigurer</a>
     *
     * @param monitoringClientTransportFilter the client transport filter to be added
     * @return the configured channel builder
     */
    @Bean
    public GrpcChannelConfigurer transportFilter(ClientTransportFilter monitoringClientTransportFilter) {
        return (channelBuilder, name) -> {
            if (channelBuilder instanceof NettyChannelBuilder) {
                ((NettyChannelBuilder) channelBuilder)
                        // .enableRetry()
                        // .maxHedgedAttempts(10)
                        .addTransportFilter(monitoringClientTransportFilter);
            }
        };
    }

    @GrpcGlobalClientInterceptor
    public ClientInterceptor grpcConnectionClientInterceptor(SharedOperator sharedOperator) {
        return new GrpcConnectionClientInterceptor(sharedOperator);
    }

}
