package org.iproute.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.iproute.grpc.api.HelloProto;
import org.iproute.grpc.api.HelloServiceGrpc;

import java.util.concurrent.TimeUnit;

/**
 * GrpcClient6
 *
 * @author zhuzhenjie
 */
@Slf4j
public class GrpcClient6 {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 9000).usePlaintext().build();

        try {
            HelloServiceGrpc.HelloServiceStub helloServiceStub = HelloServiceGrpc.newStub(channel);

            StreamObserver<HelloProto.HelloRequest> helloRequestStreamObserver = helloServiceStub.cs2ss(new StreamObserver<HelloProto.HelloResponse>() {
                @Override
                public void onNext(HelloProto.HelloResponse response) {
                    log.info("响应的结果 | result = {}", response.getResult());
                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onCompleted() {
                    log.info("响应全部结束...");
                }
            });

            for (int i = 0; i < 10; i++) {
                helloRequestStreamObserver.onNext(HelloProto.HelloRequest.newBuilder().setName("client-stream <===> server-stream " + i).build());
            }

            // 主动告知结束
            helloRequestStreamObserver.onCompleted();

            channel.awaitTermination(12, TimeUnit.SECONDS);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            channel.shutdown();
        }

    }
}
