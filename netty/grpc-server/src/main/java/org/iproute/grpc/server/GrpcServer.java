package org.iproute.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.iproute.grpc.server.filter.MonitoringServerTransportFilter;
import org.iproute.grpc.server.service.HelloServiceImpl;
import org.iproute.grpc.server.service.TestServiceImpl;

import java.io.IOException;

/**
 * GrpcServer
 *
 * @author tech@intellij.io
 */
@Slf4j
public class GrpcServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        // 1. 绑定端口
        log.info("step 1 : 绑定端口");
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(9090)
                .addTransportFilter(new MonitoringServerTransportFilter());

        // 2. 添加服务
        log.info("step 2 : 添加服务");
        serverBuilder.addService(new HelloServiceImpl());
        serverBuilder.addService(new TestServiceImpl());

        // 3. 创建服务对象
        log.info("step 3 : 生成server");
        Server server = serverBuilder.build();

        // 启动
        log.info("step 4 : 启动服务");
        server.start();
        server.awaitTermination();

    }

}
