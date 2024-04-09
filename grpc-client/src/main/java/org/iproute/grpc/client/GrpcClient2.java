package org.iproute.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.iproute.grpc.api.HelloProto;
import org.iproute.grpc.api.HelloServiceGrpc;

/**
 * GrpcClient2
 * <p>
 * newBlockingStub
 *
 * @author zhuzhenjie
 */
public class GrpcClient2 {

    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("127.0.0.1", 9000).usePlaintext().build();

        try {
            HelloServiceGrpc.HelloServiceBlockingStub helloServiceBlockingStub = HelloServiceGrpc.newBlockingStub(managedChannel);

            HelloProto.HelloRequest1 request1 = HelloProto.HelloRequest1.newBuilder()
                    .addName("NameList_First")
                    .addName("NameList_Second")
                    .build();

            HelloProto.HelloResponse1 response1 = helloServiceBlockingStub.hello1(request1);

            String result = response1.getResult();

            System.out.println("result = " + result);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            managedChannel.shutdown();
        }
    }

}