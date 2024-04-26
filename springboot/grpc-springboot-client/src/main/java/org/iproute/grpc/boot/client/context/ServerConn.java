package org.iproute.grpc.boot.client.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.iproute.grpc.boot.context.Address;

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
    public static final ServerConn DEFAULT = ServerConn.builder()
            .connected(false)
            .remote(Address.UNKNOWN_REMOTE)
            .local(Address.UNKNOWN_LOCAL)
            .build();

    private boolean connected;
    private Address remote;
    private Address local;

    public static ServerConn create(String remoteHost, int remotePort, int localPort) {
        return ServerConn.builder()
                .connected(true)
                .remote(Address.builder().host(remoteHost).port(remotePort).build())
                .local(Address.builder().host(Address.LOCAL_HOST).port(localPort).build())
                .build();
    }

}
