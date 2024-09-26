package org.iproute.grpc.boot.server.interceptor;

import io.grpc.ForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * GrpcConnectionCountInterceptor
 *
 * @author tech@intellij.io
 */
@Slf4j
public class GrpcConnectionCountInterceptor implements ServerInterceptor {

    private final AtomicInteger connectionCount = new AtomicInteger(0);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
                                                                 Metadata headers,
                                                                 ServerCallHandler<ReqT, RespT> next) {
        connectionCount.incrementAndGet();

        log.debug("Current Connection Count: {}", connectionCount.get());
        MethodDescriptor<ReqT, RespT> methodDescriptor = call.getMethodDescriptor();

        log.debug("MethodDescriptor | {}", methodDescriptor.getFullMethodName());

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(next.startCall(call, headers)) {

            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } finally {
                    connectionCount.decrementAndGet();
                    log.debug("Current Connection Count: {}", connectionCount.get());
                }
            }

            @Override
            public void onCancel() {
                log.debug("Connection cancelled by client.");
                super.onCancel();
            }

            @Override
            public void onComplete() {
                log.debug("Connection completed by client.");
                super.onComplete();

            }
        };
    }

}