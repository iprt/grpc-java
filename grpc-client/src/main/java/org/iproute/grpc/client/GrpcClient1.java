package org.iproute.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.iproute.grpc.api.HelloProto;
import org.iproute.grpc.api.HelloServiceGrpc;

/**
 * GrpcClient1
 * <p>
 * newBlockingStub
 *
 * @author zhuzhenjie
 */
@Slf4j
public class GrpcClient1 {

    public static void main(String[] args) {
        // 1 创建通信的管道
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("127.0.0.1", 9090).usePlaintext().build();

        try {
            // 2. 获取代理对象 stub
            HelloServiceGrpc.HelloServiceBlockingStub helloService = HelloServiceGrpc.newBlockingStub(managedChannel);

            // 3. 完成 grpc 调用

            // 3.1 生成request
            HelloProto.HelloRequest.Builder builder = HelloProto.HelloRequest.newBuilder();
            builder.setName("GrpcClient1");
            HelloProto.HelloRequest request = builder.build();

            // 3.2 调用服务
            HelloProto.HelloResponse response = helloService.hello(request);

            String result = response.getResult();

            log.info("result: {}", result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            managedChannel.shutdown();
        }

    }

}
