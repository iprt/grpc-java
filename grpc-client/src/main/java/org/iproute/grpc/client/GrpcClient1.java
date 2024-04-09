package org.iproute.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.iproute.grpc.api.HelloProto;
import org.iproute.grpc.api.HelloServiceGrpc;

/**
 * GrpcClient1
 * <p>
 * newBlockingStub
 *
 * @author zhuzhenjie
 */
public class GrpcClient1 {

    public static void main(String[] args) {
        // 1 创建通信的管道
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();

        try {
            // 2. 获取代理对象 stub
            HelloServiceGrpc.HelloServiceBlockingStub helloService = HelloServiceGrpc.newBlockingStub(managedChannel);

            // 3. 完成 grpc 调用

            HelloProto.HelloRequest.Builder builder = HelloProto.HelloRequest.newBuilder();

            builder.setName("GrpcClient1");

            HelloProto.HelloRequest request = builder.build();

            HelloProto.HelloResponse response = helloService.hello(request);

            String result = response.getResult();

            System.out.println("result = " + result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            managedChannel.shutdown();
        }

    }

}
