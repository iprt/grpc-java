package org.iproute.grpc.boot.client.context;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SharedOperator
 *
 * @author devops@kubectl.net
 */
public interface SharedOperator {

    /**
     * Sets the server to a ready state.
     */
    void setServerReady();

    /**
     * Sets the server to a not ready state.
     */
    void setServerNotReady();

    /**
     * Returns the status of the server readiness.
     *
     * @return {@code true} if the server is ready, {@code false} otherwise.
     */
    boolean getServerReady();

    /**
     * Sets the server connection details.
     *
     * @param remoteHost The host address of the remote server.
     * @param remotePort The port number of the remote server.
     * @param localPort  The port number of the local server.
     */
    void setServerConn(String remoteHost, int remotePort, int localPort);

    /**
     * Clears the server connection details.
     * <p>
     * This method clears the server connection details stored in the SharedOperator interface. After calling this
     * method, the server connection details will be reset to their default values.
     */
    void clearServerConn();

    /**
     * Retrieves the server connection details.
     *
     * @return The ServerConn object representing the server connection details.
     */
    ServerConn getSeverConn();


    /**
     * Connects to a remote host at the specified remote port, using the specified local port.
     *
     * @param remoteHost The host address of the remote server.
     * @param remotePort The port number of the remote server.
     * @param localPort  The port number of the local server.
     */
    void connect(String remoteHost, int remotePort, int localPort);

    /**
     * Disconnects from the currently connected server.
     * <p>
     * This method is used to disconnect from the server that was previously connected using the {@link #connect(String, int, int)} method.
     * After calling this method, the connection to the server will be terminated and the server details will be reset to their default values.
     */
    void disconnect();


    @RequiredArgsConstructor(onConstructor = @__(@Autowired))
    @Service
    @Slf4j
    class DefaultSharedOperator implements SharedOperator {
        private final Shared shared;
        private volatile ServerConn currentConn = ServerConn.DEFAULT;
        private final Lock connLock = new ReentrantLock();

        @Override
        public void setServerReady() {
            shared.getServerReady()
                    .compareAndSet(false, true);
        }

        @Override
        public void setServerNotReady() {
            shared.getServerReady()
                    .compareAndSet(true, false);
        }

        @Override
        public boolean getServerReady() {
            return shared.getServerReady().get();
        }

        @Override
        public void setServerConn(String remoteHost, int remotePort, int localPort) {
            ServerConn serverConn = ServerConn.create(remoteHost, remotePort, localPort);
            if (shared.getServerConn().compareAndSet(
                    currentConn, serverConn
            )) {
                log.debug("ServerConn {}", serverConn);
                currentConn = serverConn;
            }
        }

        @Override
        public void clearServerConn() {
            if (shared.getServerConn().compareAndSet(
                    currentConn, ServerConn.DEFAULT
            )) {
                log.debug("Clear ServerConn {}", currentConn);
                currentConn = ServerConn.DEFAULT;
            }
        }

        @Override
        public ServerConn getSeverConn() {
            return shared.getServerConn().get();
        }

        @Override
        public void connect(String remoteHost, int remotePort, int localPort) {
            connLock.lock();
            try {
                this.setServerConn(remoteHost, remotePort, localPort);
                this.setServerReady();
            } finally {
                connLock.unlock();
            }
        }

        @Override
        public void disconnect() {
            connLock.lock();
            try {
                this.clearServerConn();
                this.setServerNotReady();
            } finally {
                connLock.unlock();
            }
        }
    }

}