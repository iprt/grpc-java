package org.iproute.grpc.boot.client.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.iproute.grpc.api.ExchangeProto;
import org.iproute.grpc.api.HelloServiceGrpc;
import org.iproute.grpc.boot.client.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * HelloServiceImpl
 *
 * @author devops@kubectl.net
 */
@Service
@Slf4j
public class HelloServiceImpl implements HelloService {

    @GrpcClient("grpc-server")
    private HelloServiceGrpc.HelloServiceBlockingStub helloServiceBlockingStub;

    @Override
    public String sayHello(String name) {
        ExchangeProto.HelloResponse helloResponse = helloServiceBlockingStub.sayHello(
                ExchangeProto.HelloRequest.newBuilder()
                        .setName(name)
                        .build()
        );
        return helloResponse.getResult();
    }

}
