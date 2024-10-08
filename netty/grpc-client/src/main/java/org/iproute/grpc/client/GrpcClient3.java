package org.iproute.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.iproute.grpc.api.HelloProto;
import org.iproute.grpc.api.HelloServiceGrpc;

import java.util.Iterator;

/**
 * GrpcClient3 阻塞的
 * <p>
 * client 2 server stream | newBlockingStub
 *
 * @author tech@intellij.io
 */
@Slf4j
public class GrpcClient3 {
    public static void main(String[] args) {
        // 1 创建通信的管道
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("127.0.0.1", 9090).usePlaintext().build();

        try {
            // 2. 获取代理对象 stub
            HelloServiceGrpc.HelloServiceBlockingStub helloService = HelloServiceGrpc.newBlockingStub(managedChannel);

            // 3. 完成 grpc 调用

            HelloProto.HelloRequest.Builder builder = HelloProto.HelloRequest.newBuilder();

            builder.setName("newBlockingStub");

            HelloProto.HelloRequest request = builder.build();

            Iterator<HelloProto.HelloResponse> responseIterator = helloService.c2ss(request);

            while (responseIterator.hasNext()) {
                HelloProto.HelloResponse response = responseIterator.next();
                log.info("response.getResult() = {}", response.getResult());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            managedChannel.shutdown();
        }

    }
}
