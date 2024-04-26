package org.iproute.grpc.boot.client.entities;

/**
 * MsgConvertor
 *
 * @author devops@kubectl.net
 */
public interface GrpcConvertor<To> {
    default To convert() {
        return null;
    }
}
