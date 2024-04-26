package org.iproute.grpc.boot.client.service;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.iproute.grpc.api.HelloServiceGrpc;
import org.iproute.grpc.api.TestProto;
import org.iproute.grpc.boot.client.config.GrpcConfig;
import org.iproute.grpc.boot.client.entities.GreetReq;
import org.iproute.grpc.boot.client.entities.GreetResp;
import org.iproute.grpc.boot.client.entities.GrpcConvertUtils;
import org.iproute.grpc.multi.MultiServiceGrpc;
import org.springframework.stereotype.Service;

/**
 * TestService
 *
 * @author devops@kubectl.net
 */
public interface TestService {
    /**
     * Performs a test operation using the given name.
     *
     * @param name the name to be used for testing
     * @return the result of the test operation as a string
     */
    String test(String name);

    /**
     * Performs a greet operation using the given GreetReq object.
     *
     * @param req the GreetReq object containing the necessary parameters for the greet operation
     * @return the GreetResp object containing the greeting
     */
    GreetResp greet(GreetReq req);

    @Service
    @Slf4j
    class DefaultTestService implements TestService {

        @GrpcClient(GrpcConfig.GRPC_SERVER_INSTANCE)
        private HelloServiceGrpc.HelloServiceBlockingStub helloServiceBlockingStub;

        @GrpcClient(GrpcConfig.GRPC_SERVER_INSTANCE)
        private MultiServiceGrpc.MultiServiceBlockingStub multiServiceBlockingStub;

        @Override
        public String test(String name) {
            TestProto.HelloResponse helloResponse = helloServiceBlockingStub.test(
                    TestProto.HelloRequest.newBuilder().setName(name).build()
            );
            return helloResponse.getWelcome();
        }

        @Override
        public GreetResp greet(GreetReq req) {
            return GrpcConvertUtils.convert(
                    multiServiceBlockingStub.sayHello(
                            req.convert()
                    )
            );
        }

    }

}
