package org.iproute.grpc.boot.server.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.iproute.grpc.api.ReportProto;
import org.iproute.grpc.api.ReportServiceGrpc;

/**
 * HeartBeatService
 *
 * @author devops@kubectl.net
 */
@GrpcService
@Slf4j
public class HeartBeatService extends ReportServiceGrpc.ReportServiceImplBase {

    @Override
    public void hb(ReportProto.HeartBeatRequest request, StreamObserver<ReportProto.HeartBeatResponse> responseObserver) {
        String id = request.getId();
        log.info("received heartbeat id: {}", id);
        responseObserver.onNext(ReportProto.HeartBeatResponse.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
