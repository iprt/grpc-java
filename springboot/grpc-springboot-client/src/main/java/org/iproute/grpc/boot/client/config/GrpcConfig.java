package org.iproute.grpc.boot.client.config;

import io.grpc.ClientTransportFilter;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelConfigurer;
import org.iproute.grpc.boot.client.filter.MonitoringClientTransportFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * GrpcConfig
 *
 * @author devops@kubectl.net
 */
@Configuration
@Slf4j
public class GrpcConfig {

    @Bean
    public ClientTransportFilter monitoringClientTransportFilter() {
        return new MonitoringClientTransportFilter();
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
                        .addTransportFilter(monitoringClientTransportFilter);
            }
        };
    }


}
