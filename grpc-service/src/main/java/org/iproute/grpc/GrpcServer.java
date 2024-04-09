package org.iproute.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.iproute.grpc.service.HelloServiceImpl;
import org.iproute.grpc.service.TestServiceImpl;

import java.io.IOException;

/**
 * GrpcServer
 *
 * @author zhuzhenjie
 */
public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {

        // 1. 绑定端口
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(9000);

        // 2. 发布服务
        serverBuilder.addService(new HelloServiceImpl());
        serverBuilder.addService(new TestServiceImpl());
        // 3. 创建服务对象
        Server server = serverBuilder.build();

        // 启动
        server.start();
        server.awaitTermination();
    }
}
