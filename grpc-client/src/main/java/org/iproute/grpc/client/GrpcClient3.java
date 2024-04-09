package org.iproute.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.iproute.grpc.api.HelloProto;
import org.iproute.grpc.api.HelloServiceGrpc;

import java.util.Iterator;

/**
 * GrpcClient3 阻塞的
 * <p>
 * newBlockingStub
 *
 * @author zhuzhenjie
 */
public class GrpcClient3 {
    public static void main(String[] args) {
        // 1 创建通信的管道
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();

        try {
            // 2. 获取代理对象 stub
            HelloServiceGrpc.HelloServiceBlockingStub helloService = HelloServiceGrpc.newBlockingStub(managedChannel);

            // 3. 完成 grpc 调用

            HelloProto.HelloRequest.Builder builder = HelloProto.HelloRequest.newBuilder();

            builder.setName("GrpcClient3");

            HelloProto.HelloRequest request = builder.build();

            Iterator<HelloProto.HelloResponse> responseIterator = helloService.c2ss(request);

            while (responseIterator.hasNext()) {
                HelloProto.HelloResponse response = responseIterator.next();
                System.out.println("response.getResult() = " + response.getResult());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            managedChannel.shutdown();
        }

    }
}
