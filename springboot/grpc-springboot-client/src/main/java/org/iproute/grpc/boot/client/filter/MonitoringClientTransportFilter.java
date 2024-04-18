package org.iproute.grpc.boot.client.filter;

import io.grpc.Attributes;
import io.grpc.ClientTransportFilter;
import lombok.extern.slf4j.Slf4j;

/**
 * MonitoringClientTransportFilter
 *
 * @author devops@kubectl.net
 */
@Slf4j
public class MonitoringClientTransportFilter extends ClientTransportFilter {

    @Override
    public Attributes transportReady(Attributes transportAttrs) {
        log.info("Transport is ready {}", transportAttrs);
        return super.transportReady(transportAttrs);
    }

    @Override
    public void transportTerminated(Attributes transportAttrs) {
        log.info("Transport terminated {}", transportAttrs);
        super.transportTerminated(transportAttrs);
    }

}
