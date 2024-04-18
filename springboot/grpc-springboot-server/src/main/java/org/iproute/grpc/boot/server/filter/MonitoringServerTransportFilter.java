package org.iproute.grpc.boot.server.filter;

import io.grpc.Attributes;
import io.grpc.Grpc;
import io.grpc.ServerTransportFilter;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * MonitoringServerTransportFilter
 *
 * @author devops@kubectl.net
 */
@Slf4j
public class MonitoringServerTransportFilter extends ServerTransportFilter {

    @Override
    public Attributes transportReady(Attributes transportAttrs) {
        log.info("Transport is ready: {}", transportAttrs);
        return super.transportReady(transportAttrs);
    }

    @Override
    public void transportTerminated(Attributes transportAttrs) {
        if (transportAttrs.get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR) instanceof InetSocketAddress socketAddress) {
            log.error("Transport terminated: ip = {} ;port = {}", socketAddress.getHostString(), socketAddress.getPort());
        } else {
            log.error("Transport is terminated: {}", transportAttrs);
        }
        super.transportTerminated(transportAttrs);
    }

}
