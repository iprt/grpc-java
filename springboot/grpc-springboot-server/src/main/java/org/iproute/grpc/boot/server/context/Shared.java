package org.iproute.grpc.boot.server.context;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.iproute.grpc.boot.proto.context.Address;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Shared
 *
 * @author devops@kubectl.net
 */
@Repository
@Getter
public class Shared {
    private final Map<Address, ClientConn> live = Maps.newConcurrentMap();
    private final List<ClientConn> history = Lists.newCopyOnWriteArrayList();
}
