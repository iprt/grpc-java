package org.iproute.grpc.boot.server.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.iproute.grpc.api.HeartBeatProto;
import org.iproute.grpc.api.HeartBeatServiceGrpc;

/**
 * HeartBeatService
 *
 * @author devops@kubectl.net
 */
@GrpcService
@Slf4j
public class HeartBeatService extends HeartBeatServiceGrpc.HeartBeatServiceImplBase {

    @Override
    public void report(HeartBeatProto.Ping request, StreamObserver<HeartBeatProto.Pong> responseObserver) {
        String id = request.getId();
        log.debug("received heartbeat from {}", id);
        responseObserver.onNext(
                HeartBeatProto.Pong.newBuilder()
                        .setRes("pong " + id)
                        .build()
        );

        responseObserver.onCompleted();

    }
}
