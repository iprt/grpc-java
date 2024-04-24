package org.iproute.grpc.boot.client.context;

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
     * @param remoteHost  The host address of the remote server.
     * @param remotePort  The port number of the remote server.
     * @param localPort   The port number of the local server.
     */
    void connect(String remoteHost, int remotePort, int localPort);

    /**
     * Disconnects from the currently connected server.
     * <p>
     * This method is used to disconnect from the server that was previously connected using the {@link #connect(String, int, int)} method.
     * After calling this method, the connection to the server will be terminated and the server details will be reset to their default values.
     */
    void disconnect();

}