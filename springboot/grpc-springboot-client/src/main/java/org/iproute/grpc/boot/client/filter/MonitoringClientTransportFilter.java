package org.iproute.grpc.boot.client.filter;

import io.grpc.Attributes;
import io.grpc.ClientTransportFilter;
import io.grpc.Grpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.grpc.boot.client.context.Address;
import org.iproute.grpc.boot.client.context.SharedOperator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * MonitoringClientTransportFilter
 *
 * @author devops@kubectl.net
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MonitoringClientTransportFilter extends ClientTransportFilter {

    private final SharedOperator sharedOperator;

    @Override
    public Attributes transportReady(Attributes transportAttrs) {
        log.debug("transport ready; {}", transportAttrs);
        Address remote = Address.from(transportAttrs.get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR), false);
        Address local = Address.from(transportAttrs.get(Grpc.TRANSPORT_ATTR_LOCAL_ADDR), true);
        sharedOperator.connect(remote.getHost(), remote.getPort(), local.getPort());
        return super.transportReady(transportAttrs);
    }

    @Override
    public void transportTerminated(Attributes transportAttrs) {
        log.debug("transport terminated; {}", transportAttrs);
        sharedOperator.disconnect();
    }

}
