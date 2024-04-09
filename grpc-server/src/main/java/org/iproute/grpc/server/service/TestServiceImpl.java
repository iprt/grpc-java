package org.iproute.grpc.server.service;

import io.grpc.stub.StreamObserver;
import org.iproute.grpc.api.TestProto;
import org.iproute.grpc.api.TestServiceGrpc;

/**
 * TestServiceImpl
 *
 * @author zhuzhenjie
 */
public class TestServiceImpl extends TestServiceGrpc.TestServiceImplBase {
    @Override
    public void test(TestProto.TestRequest request, StreamObserver<TestProto.TestResponse> responseObserver) {
        String name = request.getName();
        System.out.println("name = " + name);

        TestProto.TestResponse.Builder builder = TestProto.TestResponse.newBuilder();
        builder.setResult("【 result is " + name + " 】");
        TestProto.TestResponse response = builder.build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
