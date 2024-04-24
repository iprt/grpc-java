package org.iproute.grpc.boot.client.interceptor;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.grpc.boot.client.context.SharedOperator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * GrpcConnectionClientInterceptor
 *
 * @author devops@kubectl.net
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class GrpcConnectionClientInterceptor implements ClientInterceptor {

    private final SharedOperator sharedOperator;

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
                                                               CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                log.debug("GrpcConnectionClientInterceptor start");
                super.start(responseListener, headers);
            }

            @Override
            public void sendMessage(ReqT message) {
                log.debug("GrpcConnectionClientInterceptor sendMessage");
                super.sendMessage(message);
            }

            @Override
            public void cancel(@Nullable String message, @Nullable Throwable cause) {
                try {
                    super.cancel(message, cause);
                } finally {
                    sharedOperator.setServerNotReady();
                    log.error("GrpcConnectionClientInterceptor cancel {}", Objects.nonNull(cause) ? cause.getClass() : "NULL");
                }
            }
        };
    }

}
