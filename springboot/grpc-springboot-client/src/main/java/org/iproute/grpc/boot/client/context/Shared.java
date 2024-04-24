package org.iproute.grpc.boot.client.context;

import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Shared
 *
 * @author devops@kubectl.net
 */
@Repository
@Getter
public class Shared {
    private final AtomicBoolean serverReady = new AtomicBoolean(false);
    private final AtomicReference<ServerConn> serverConn = new AtomicReference<>(ServerConn.DEFAULT);
}
