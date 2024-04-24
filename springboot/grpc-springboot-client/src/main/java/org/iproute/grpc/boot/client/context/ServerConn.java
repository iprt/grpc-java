package org.iproute.grpc.boot.client.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ServerConn
 *
 * @author devops@kubectl.net
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class ServerConn {
    private static final String DEFAULT_HOST = "unknown";
    private static final int DEFAULT_PORT = -1;
    private static final String LOCAL_HOST = "127.0.0.1";

    public static final ServerConn DEFAULT = ServerConn.builder()
            .connected(false)
            .remote(Address.builder().host(DEFAULT_HOST).port(DEFAULT_PORT).build())
            .local(Address.builder().host(LOCAL_HOST).port(DEFAULT_PORT).build())
            .build();

    private boolean connected;
    private Address remote;
    private Address local;

    public static ServerConn create(String remoteHost, int remotePort, int localPort) {
        return ServerConn.builder()
                .connected(true)
                .remote(Address.builder().host(remoteHost).port(remotePort).build())
                .local(Address.builder().host(LOCAL_HOST).port(localPort).build())
                .build();
    }

}
