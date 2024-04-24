package org.iproute.grpc.boot.client.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Objects;

/**
 * Address
 *
 * @author devops@kubectl.net
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class Address {
    private String host;
    private int port;

    public Address copy() {
        return Address.builder()
                .host(this.host).port(this.port)
                .build();
    }

    public static Address from(SocketAddress socketAddress, boolean local) {
        if (Objects.isNull(socketAddress)) {
            return local ? ServerConn.DEFAULT.getLocal().copy() : ServerConn.DEFAULT.getRemote().copy();
        }

        if (socketAddress instanceof InetSocketAddress address) {
            return Address.builder()
                    .host(address.getHostString())
                    .port(address.getPort())
                    .build();
        } else {
            return local ? ServerConn.DEFAULT.getLocal().copy() : ServerConn.DEFAULT.getRemote().copy();
        }
    }
}
