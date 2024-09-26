package org.iproute.grpc.boot.client.entities;

/**
 * MsgConvertor
 *
 * @author tech@intellij.io
 */
public interface GrpcConvertor<To> {
    default To convert() {
        return null;
    }
}
