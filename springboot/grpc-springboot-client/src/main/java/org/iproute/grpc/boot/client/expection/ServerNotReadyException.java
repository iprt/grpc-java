package org.iproute.grpc.boot.client.expection;

/**
 * ServerNotReadyException
 *
 * @author tech@intellij.io
 */
public class ServerNotReadyException extends RuntimeException {
    private static final String MSG = "Grpc Server Not Ready";

    public ServerNotReadyException() {
        super(MSG);
    }

    public static ServerNotReadyException create() {
        return new ServerNotReadyException();
    }

}
