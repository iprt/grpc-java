package org.iproute.grpc.boot.client.task.hb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.iproute.grpc.api.HeartBeatProto;
import org.iproute.grpc.api.HeartBeatServiceGrpc;
import org.iproute.grpc.boot.client.config.GrpcConfig;
import org.iproute.grpc.boot.client.context.SharedOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * HeartBeatRunnable
 *
 * @author devops@kubectl.net
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
@Slf4j
public class HeartBeatRunnable implements Runnable {
    private final SharedOperator sharedOperator;

    @GrpcClient(GrpcConfig.GRPC_SERVER_INSTANCE)
    private HeartBeatServiceGrpc.HeartBeatServiceBlockingStub heartBeatServiceBlockingStub;

    @Override
    public void run() {
        report();
    }

    /**
     * Sends a HeartBeat report to the server and updates the server readiness status.
     * <p>
     * {@link org.iproute.grpc.boot.client.interceptor.GrpcConnectionClientInterceptor} cancel
     */
    private void report() {
        String id = UUID.randomUUID().toString();
        log.debug("HeartBeat Report; id = {}", id);
        try {
            HeartBeatProto.Pong pong = heartBeatServiceBlockingStub.report(
                    HeartBeatProto.Ping.newBuilder()
                            .setId(id)
                            .build()
            );
            log.debug("HeartBeat Down Success; {}", pong.getRes());
            sharedOperator.setServerReady();
        } catch (Exception e) {
            log.debug("HeartBeat Down Failed !");
            // {@link org.iproute.grpc.boot.client.interceptor.GrpcConnectionClientInterceptor} cancel
            // sharedOperator.setServerNotReady();
        }

    }

}
