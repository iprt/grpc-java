package org.iproute.grpc.boot.client.service;

import io.grpc.stub.StreamObserver;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.iproute.grpc.api.ExchangeProto;
import org.iproute.grpc.api.ExchangeServiceGrpc;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * HeartBeatRuner
 *
 * @author devops@kubectl.net
 */
@Component
@Slf4j
public class HeartBeatTask implements CommandLineRunner {

    @GrpcClient("grpc-server")
    private ExchangeServiceGrpc.ExchangeServiceStub exchangeServiceStub;

    @Resource
    private ThreadPoolTaskScheduler taskScheduler;

    @Override
    @Async
    public void run(String... args) throws Exception {
        report();
    }

    private void report() {
        taskScheduler.scheduleAtFixedRate(new Report(),
                Duration.of(5, ChronoUnit.SECONDS));
    }

    public class Report implements Runnable {
        final StreamObserver<ExchangeProto.HeartBeat> report = exchangeServiceStub.report(new StreamObserver<ExchangeProto.Empty>() {
            @Override
            public void onNext(ExchangeProto.Empty empty) {
                log.info("report finished");
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("report error", throwable);
            }

            @Override
            public void onCompleted() {
                log.info("report completed");
            }
        });

        @Override
        public void run() {
            report.onNext(ExchangeProto.HeartBeat.newBuilder()
                    .setId("hb")
                    .build());

            // todo complete ?
            // report.onCompleted();
        }
    }
}
