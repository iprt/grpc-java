package org.iproute.grpc.boot.client.task;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.iproute.grpc.api.ExchangeProto;
import org.iproute.grpc.api.ExchangeServiceGrpc;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * HeartBeatTask
 *
 * @author devops@kubectl.net
 */
@Component
@Slf4j
public class HeartBeatTask {

    @GrpcClient("grpc-server")
    private ExchangeServiceGrpc.ExchangeServiceBlockingStub exchangeServiceBlockingStub;

    @Scheduled(fixedRate = 5000)
    private void report() {
        log.info("HeartBeat Report");
        ExchangeProto.Empty reported = exchangeServiceBlockingStub.report(
                ExchangeProto.HeartBeat.newBuilder()
                        .setId("HeartBeatTask")
                        .build()
        );
        log.info("HeartBeat Down {}", reported);
    }

}
