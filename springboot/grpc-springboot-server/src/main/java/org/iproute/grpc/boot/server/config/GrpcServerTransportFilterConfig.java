package org.iproute.grpc.boot.server.config;

import io.grpc.CompressorRegistry;
import io.grpc.ServerBuilder;
import io.grpc.ServerTransportFilter;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.common.codec.GrpcCodecDiscoverer;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.iproute.grpc.boot.server.context.SharedOperator;
import org.iproute.grpc.boot.server.filter.MonitoringServerTransportFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * GrpcServerTransportFilterConfig
 * <p>
 * {@link GrpcServerFactoryAutoConfiguration#shadedNettyGrpcServerFactory}
 *
 * @author tech@intellij.io
 */
@Configuration
@Slf4j
public class GrpcServerTransportFilterConfig {


    @Bean
    public ServerTransportFilter monitoringServerTransportFilter(SharedOperator sharedOperator) {
        return new MonitoringServerTransportFilter(sharedOperator);
    }

    /**
     * Creates a GrpcServerConfigurer that adds the given monitoringServerTransportFilter to the GrpcServerBuilder.
     * <p>
     * {@link net.devh.boot.grpc.server.serverfactory.AbstractGrpcServerFactory#configure(ServerBuilder)}
     * <p>
     * {@link net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration#compressionServerConfigurer(CompressorRegistry)}
     * <p>
     * {@link net.devh.boot.grpc.common.autoconfigure.GrpcCommonCodecAutoConfiguration#defaultCompressorRegistry(GrpcCodecDiscoverer)}
     *
     * @param monitoringServerTransportFilter The ServerTransportFilter to be added to the GrpcServerBuilder
     * @return The GrpcServerConfigurer that adds the monitoringServerTransportFilter
     */
    @Bean
    public GrpcServerConfigurer monitoringServerTransportFilterConfigurer(
            ServerTransportFilter monitoringServerTransportFilter) {
        log.info("GrpcServerConfigurer add MonitoringServerTransportFilter");
        return builder -> builder.addTransportFilter(monitoringServerTransportFilter);
    }

}
