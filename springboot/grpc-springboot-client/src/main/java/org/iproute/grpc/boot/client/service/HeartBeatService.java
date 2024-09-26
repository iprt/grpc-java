package org.iproute.grpc.boot.client.service;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.iproute.grpc.api.HeartBeatProto;
import org.iproute.grpc.api.HeartBeatServiceGrpc;
import org.iproute.grpc.boot.client.config.GrpcConfig;
import org.iproute.grpc.boot.client.config.interceptor.GrpcConnectionClientInterceptor;
import org.iproute.grpc.boot.client.context.SharedOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * HeartBeatService
 *
 * @author tech@intellij.io
 */
public interface HeartBeatService {

    void doHeartBeat(String content);

    @RequiredArgsConstructor(onConstructor = @__(@Autowired))
    @Service
    class DefaultHeartBeatService implements HeartBeatService {
        private static final Logger log = LoggerFactory.getLogger("org.iproute.grpc.boot.client.service.HeartBeatService.DefaultHeartBeatService");

        private final SharedOperator sharedOperator;

        @GrpcClient(GrpcConfig.GRPC_SERVER_INSTANCE)
        private HeartBeatServiceGrpc.HeartBeatServiceBlockingStub heartBeatServiceBlockingStub;

        /**
         * Sends a HeartBeat report to the server and updates the server readiness status.
         * <p>
         * {@link GrpcConnectionClientInterceptor} cancel
         */
        @Override
        public void doHeartBeat(String content) {
            log.debug("HeartBeat Report. Ping. Content={}", content);
            try {
                HeartBeatProto.Pong pong = heartBeatServiceBlockingStub.report(
                        HeartBeatProto.Ping.newBuilder()
                                .setId(content)
                                .build()
                );
                log.debug("HeartBeat Down. Pong; Resp={}", pong.getRes());
                sharedOperator.setServerReady();
            } catch (Exception e) {
                // {@link org.iproute.grpc.boot.client.interceptor.GrpcConnectionClientInterceptor} cancel
                // sharedOperator.setServerNotReady();
                log.debug("HeartBeat Down Failed !");
            }

        }
    }

}
