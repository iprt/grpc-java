package org.iproute.grpc.boot.client.service;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.iproute.grpc.api.TestProto;
import org.iproute.grpc.api.TestServiceGrpc;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * TestClientService
 *
 * @author devops@kubectl.net
 */
@Service
@Slf4j
public class TestClientService implements CommandLineRunner {

    @GrpcClient("grpc-server")
    private TestServiceGrpc.TestServiceBlockingStub testServiceBlockingStub;

    @Override
    public void run(String... args) throws Exception {
        TestProto.TestResponse testResponse = testServiceBlockingStub.test(
                TestProto.TestRequest.newBuilder()
                        .setName("springboot")
                        .build()
        );

        log.info("grpc result {}", testResponse.getResult());
    }
}
