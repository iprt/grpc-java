package org.iproute.grpc.server.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.iproute.grpc.api.TestProto;
import org.iproute.grpc.api.TestServiceGrpc;

/**
 * TestServiceImpl
 *
 * @author zhuzhenjie
 */
@Slf4j
public class TestServiceImpl extends TestServiceGrpc.TestServiceImplBase {
    @Override
    public void test(TestProto.TestRequest request, StreamObserver<TestProto.TestResponse> responseObserver) {
        String name = request.getName();
        log.info("name = {}", name);

        TestProto.TestResponse.Builder builder = TestProto.TestResponse.newBuilder();
        builder.setResult("【 result is " + name + " 】");
        TestProto.TestResponse response = builder.build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
