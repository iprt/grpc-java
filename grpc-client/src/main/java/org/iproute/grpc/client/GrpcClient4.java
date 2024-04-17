package org.iproute.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.iproute.grpc.api.HelloProto;
import org.iproute.grpc.api.HelloServiceGrpc;

import java.util.concurrent.TimeUnit;

/**
 * GrpcClient4
 * <p>
 * 关键 newStub
 *
 * @author zhuzhenjie
 */
@Slf4j
public class GrpcClient4 {

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 9090).usePlaintext().build();

        try {
            // 异步的关键
            HelloServiceGrpc.HelloServiceStub helloServiceStub = HelloServiceGrpc.newStub(channel);

            // 异步回调
            helloServiceStub.c2ss(
                    HelloProto.HelloRequest.newBuilder()
                            .setName("client <===> server-stream")
                            .build(),

                    new StreamObserver<HelloProto.HelloResponse>() {

                        @Override
                        public void onNext(HelloProto.HelloResponse response) {
                            // 每次流的数据
                            log.info("Received response | result = {}", response.getResult());
                        }

                        @Override
                        public void onError(Throwable throwable) {

                        }

                        @Override
                        public void onCompleted() {
                            log.info("服务端响应结束，在这里统一处理服务端响应的内容");
                        }
                    }
            );

            channel.awaitTermination(12, TimeUnit.SECONDS);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            channel.shutdown();
        }
    }
}
