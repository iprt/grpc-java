package org.iproute.grpc.boot.server.interceptor;

import io.grpc.ForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ConnectionCountInterceptor
 *
 * @author devops@kubectl.net
 */
@Slf4j
public class ConnectionCountInterceptor implements ServerInterceptor {

    private final AtomicInteger connectionCount = new AtomicInteger(0);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
                                                                 Metadata headers,
                                                                 ServerCallHandler<ReqT, RespT> next) {
        connectionCount.incrementAndGet();
        log.info("Current Connection Count: {}", connectionCount.get());

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(next.startCall(call, headers)) {
            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } finally {
                    connectionCount.decrementAndGet();
                    log.info("Current Connection Count: {}", connectionCount.get());
                }
            }
        };
    }
}