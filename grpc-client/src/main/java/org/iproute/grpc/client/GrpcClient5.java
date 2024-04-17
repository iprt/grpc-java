package org.iproute.grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.iproute.grpc.api.HelloProto;
import org.iproute.grpc.api.HelloServiceGrpc;

import java.util.concurrent.TimeUnit;

/**
 * GrpcClient5
 *
 * @author zhuzhenjie
 */
@Slf4j
public class GrpcClient5 {

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 9090).usePlaintext().build();

        try {

            HelloServiceGrpc.HelloServiceStub helloServiceStub = HelloServiceGrpc.newStub(channel);

            StreamObserver<HelloProto.HelloRequest> helloRequestStreamObserver = helloServiceStub.cs2s(

                    new StreamObserver<HelloProto.HelloResponse>() {
                        @Override
                        public void onNext(HelloProto.HelloResponse response) {
                            // 监控响应
                            log.info("服务端 响应 数据内容为 {}", response.getResult());
                        }

                        @Override
                        public void onError(Throwable throwable) {

                        }

                        @Override
                        public void onCompleted() {
                            log.info("服务端响应结束 ... ");
                        }
                    }

            );

            for (int i = 0; i < 10; i++) {
                HelloProto.HelloRequest request = HelloProto.HelloRequest.newBuilder()
                        .setName("client-stream  <===>  server " + i)
                        .build();

                helloRequestStreamObserver.onNext(request);
                Thread.sleep(1000);
            }

            helloRequestStreamObserver.onCompleted();

            channel.awaitTermination(12, TimeUnit.SECONDS);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            channel.shutdown();
        }

    }
}
