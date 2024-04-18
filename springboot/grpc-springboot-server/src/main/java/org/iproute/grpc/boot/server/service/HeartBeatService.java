package org.iproute.grpc.boot.server.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.iproute.grpc.api.ExchangeProto;
import org.iproute.grpc.api.ExchangeServiceGrpc;

/**
 * HeartBeatService
 *
 * @author devops@kubectl.net
 */
@GrpcService
@Slf4j
public class HeartBeatService extends ExchangeServiceGrpc.ExchangeServiceImplBase {

    @Override
    public void report(ExchangeProto.HeartBeat request, StreamObserver<ExchangeProto.Empty> responseObserver) {
        log.info("received heartbeat for id: {}", request.getId());
        responseObserver.onNext(ExchangeProto.Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
