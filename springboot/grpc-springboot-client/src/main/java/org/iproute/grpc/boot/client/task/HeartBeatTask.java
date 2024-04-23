package org.iproute.grpc.boot.client.task;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.iproute.grpc.api.ReportProto;
import org.iproute.grpc.api.ReportServiceGrpc;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * HeartBeatTask
 *
 * @author devops@kubectl.net
 */
@Service
@Slf4j
public class HeartBeatTask {

    @GrpcClient("grpc-server")
    private ReportServiceGrpc.ReportServiceBlockingStub reportServiceBlockingStub;

    @Scheduled(cron = "*/5 * * * * ?")
    private void report() {
        final String id = "hb";
        log.debug("HeartBeat Report id = {}", id);
        ReportProto.HeartBeatResponse heartBeatResponse = reportServiceBlockingStub.hb(
                ReportProto.HeartBeatRequest.newBuilder()
                        .setId(id)
                        .build()
        );
        log.debug("HeartBeat Down {}", heartBeatResponse);
    }

}
