package org.iproute.grpc.boot.client.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.iproute.grpc.api.HelloServiceGrpc;
import org.iproute.grpc.api.TestProto;
import org.iproute.grpc.boot.client.config.GrpcConfig;
import org.iproute.grpc.boot.client.service.TestService;
import org.springframework.stereotype.Service;

/**
 * TestServiceImpl
 *
 * @author devops@kubectl.net
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @GrpcClient(GrpcConfig.GRPC_SERVER_INSTANCE)
    private HelloServiceGrpc.HelloServiceBlockingStub helloServiceBlockingStub;

    @Override
    public String test(String name) {
        TestProto.HelloResponse helloResponse = helloServiceBlockingStub.test(
                TestProto.HelloRequest.newBuilder().setName(name).build()
        );
        return helloResponse.getWelcome();
    }

}
