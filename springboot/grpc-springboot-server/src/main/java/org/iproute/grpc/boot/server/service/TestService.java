package org.iproute.grpc.boot.server.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.iproute.grpc.api.HelloServiceGrpc;
import org.iproute.grpc.api.TestProto;

/**
 * TestService
 *
 * @author devops@kubectl.net
 */
@GrpcService
@Slf4j
public class TestService extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void test(TestProto.HelloRequest request, StreamObserver<TestProto.HelloResponse> responseObserver) {
        String name = request.getName();
        log.debug("receive name is {}", name);
        responseObserver.onNext(
                TestProto.HelloResponse.newBuilder()
                        .setWelcome("Hello," + name)
                        .build()
        );

        responseObserver.onCompleted();
    }
}
