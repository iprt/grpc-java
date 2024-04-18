package org.iproute.grpc.boot.server.filter;

import io.grpc.Attributes;
import io.grpc.ServerTransportFilter;
import lombok.extern.slf4j.Slf4j;

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
        log.info("Transport is terminated: {}", transportAttrs);
        super.transportTerminated(transportAttrs);
    }

}
