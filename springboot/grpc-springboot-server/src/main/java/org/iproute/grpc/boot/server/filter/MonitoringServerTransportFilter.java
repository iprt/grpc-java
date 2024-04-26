package org.iproute.grpc.boot.server.filter;

import io.grpc.Attributes;
import io.grpc.Grpc;
import io.grpc.ServerTransportFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.grpc.boot.context.Address;
import org.iproute.grpc.boot.server.context.SharedOperator;

import java.net.InetSocketAddress;

/**
 * MonitoringServerTransportFilter
 *
 * @author devops@kubectl.net
 */
@RequiredArgsConstructor
@Slf4j
public class MonitoringServerTransportFilter extends ServerTransportFilter {
    private final SharedOperator sharedOperator;

    @Override
    public Attributes transportReady(Attributes transportAttrs) {
        log.info("Transport is ready: {}", transportAttrs);
        sharedOperator.up(
                Address.from(transportAttrs.get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR), false)
        );
        return super.transportReady(transportAttrs);
    }

    @Override
    public void transportTerminated(Attributes transportAttrs) {
        sharedOperator.down(Address.from(transportAttrs.get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR), false));

        if (transportAttrs.get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR) instanceof InetSocketAddress socketAddress) {
            log.error("Transport terminated: ip = {} ;port = {}", socketAddress.getHostString(), socketAddress.getPort());
        } else {
            log.error("Transport is terminated: {}", transportAttrs);
        }


        super.transportTerminated(transportAttrs);
    }

}
