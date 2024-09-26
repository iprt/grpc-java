package org.iproute.grpc.boot.proto.context;

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
 * @author tech@intellij.io
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class Address {
    private static final String UNKNOWN_HOST = "unknown";
    private static final int UNKNOWN_PORT = -1;
    public static final String LOCAL_HOST = "127.0.0.1";

    public static final Address UNKNOWN_REMOTE = Address.builder().host(UNKNOWN_HOST).port(UNKNOWN_PORT).build();
    public static final Address UNKNOWN_LOCAL = Address.builder().host(LOCAL_HOST).port(UNKNOWN_PORT).build();

    private String host;
    private int port;

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Address other) {
            return this.host.equals(other.host) && this.port == other.port;
        } else {
            return false;
        }
    }

    public Address copy() {
        return Address.builder()
                .host(this.host).port(this.port)
                .build();
    }

    /**
     * Creates an Address object from a given SocketAddress.
     *
     * @param socketAddress The SocketAddress to convert to an Address.
     * @param local         Whether the SocketAddress is local or not.
     * @return The Address object created from the SocketAddress, or an unknown Address if the SocketAddress is null or not an instance of InetSocketAddress.
     */
    public static Address from(SocketAddress socketAddress, boolean local) {
        return Objects.isNull(socketAddress)
                ? unknown(local)
                :
                (
                        (socketAddress instanceof InetSocketAddress address)
                                ? Address.builder().host(address.getHostString()).port(address.getPort()).build()
                                : unknown(local)
                );

    }

    private static Address unknown(boolean local) {
        return local ? UNKNOWN_LOCAL.copy() : UNKNOWN_REMOTE.copy();
    }

}
