package org.iproute.grpc.boot.server.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iproute.grpc.boot.proto.context.Address;

import java.util.Objects;

/**
 * ClientConn
 *
 * @author devops@kubectl.net
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ClientConn {
    private boolean connected;
    private Address remote;

    @Override
    public int hashCode() {
        return Objects.hash(connected, remote);
    }

    @Override
    public boolean equals(Object o) {
        if (Objects.isNull(o)) {
            return false;
        }
        if (o instanceof ClientConn other) {
            return this.connected == other.connected && this.remote.equals(other.remote);
        }
        return false;
    }

    public static ClientConn up(Address address) {
        return ClientConn.builder().connected(true).remote(address).build();
    }

    public static ClientConn down(Address address) {
        return ClientConn.builder().connected(false).remote(address).build();
    }

}
