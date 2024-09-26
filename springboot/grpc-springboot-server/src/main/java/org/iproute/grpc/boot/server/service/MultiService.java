package org.iproute.grpc.boot.server.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.iproute.grpc.multi.Gender;
import org.iproute.grpc.multi.GreetRequest;
import org.iproute.grpc.multi.GreetResponse;
import org.iproute.grpc.multi.MultiServiceGrpc;

import java.util.List;

/**
 * MultiService
 *
 * @author tech@intellij.io
 */
@GrpcService
@Slf4j
public class MultiService extends MultiServiceGrpc.MultiServiceImplBase {

    @Override
    public void sayHello(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        int id = request.getId();
        String name = request.getName();
        Gender gender = request.getGender();
        List<String> emails = request.getEmailsList().stream().toList();
        log.info("id: {}, name: {}, gender: {}, emails: {}", id, name, gender, emails);

        responseObserver.onNext(
                GreetResponse.newBuilder()
                        .setMsg("Hello," + name)
                        .build()
        );

        responseObserver.onCompleted();
    }
}
