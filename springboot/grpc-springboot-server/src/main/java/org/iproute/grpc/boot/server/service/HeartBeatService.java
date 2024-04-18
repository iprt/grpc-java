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
    public StreamObserver<ExchangeProto.HeartBeat> report(StreamObserver<ExchangeProto.Empty> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(ExchangeProto.HeartBeat value) {
                log.info("Received heartbeat: {}", value.getId());
            }

            @Override
            public void onError(Throwable t) {
                log.error(t.getMessage(), t);
            }

            @Override
            public void onCompleted() {
                // 理论上不会进入这里
                responseObserver.onNext(ExchangeProto.Empty.getDefaultInstance());
                responseObserver.onCompleted();
            }
        };
    }
}
