package org.iproute.grpc.boot.server.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.iproute.grpc.api.ExchangeProto;
import org.iproute.grpc.api.HelloServiceGrpc;

/**
 * HelloService
 *
 * @author devops@kubectl.net
 */
@GrpcService
@Slf4j
public class HelloService extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void sayHello(ExchangeProto.HelloRequest request,
                         StreamObserver<ExchangeProto.HelloResponse> responseObserver) {

        String name = request.getName();
        log.info("receive from client | name = {}", name);

        responseObserver.onNext(
                ExchangeProto.HelloResponse.newBuilder()
                        .setResult("Hello " + name + " !")
                        .build()
        );

        responseObserver.onCompleted();
    }

}
