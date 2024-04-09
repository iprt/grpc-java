package org.iproute.grpc.client;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.iproute.grpc.api.TestProto;
import org.iproute.grpc.api.TestServiceGrpc;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * GrpcClient7
 *
 * @author zhuzhenjie
 */
public class GrpcClient7 {

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();

        try {
            TestServiceGrpc.TestServiceFutureStub testServiceFutureStub = TestServiceGrpc.newFutureStub(channel);

            ListenableFuture<TestProto.TestResponse> future = testServiceFutureStub.test(
                    TestProto.TestRequest.newBuilder().setName("GrpcClient7").build()
            );


            Futures.addCallback(future, new FutureCallback<TestProto.TestResponse>() {
                @Override
                public void onSuccess(TestProto.TestResponse result) {
                    System.out.println("result.getResult() = " + result.getResult());
                }

                @Override
                public void onFailure(Throwable t) {

                }
            }, Executors.newCachedThreadPool());

            System.out.println("后续的操作。。。");

            channel.awaitTermination(12, TimeUnit.SECONDS);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            channel.shutdown();
        }

    }
}
