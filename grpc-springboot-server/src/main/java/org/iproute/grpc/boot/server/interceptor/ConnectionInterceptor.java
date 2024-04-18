package org.iproute.grpc.boot.server.interceptor;

import io.grpc.ForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;

/**
 * ConnectionInterceptor
 *
 * @author devops@kubectl.net
 */
@Slf4j
public class ConnectionInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
                                                                 Metadata requestHeaders,
                                                                 ServerCallHandler<ReqT, RespT> next) {
        ServerCall.Listener<ReqT> delegate = next.startCall(call, requestHeaders);

        log.info("Connection opened by client.");

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(delegate) {

            @Override
            public void onCancel() {
                log.info("Connection cancelled by client.");
                super.onCancel();
            }

            @Override
            public void onComplete() {
                log.info("Connection completed by client.");
                super.onComplete();

            }

        };
    }

}
