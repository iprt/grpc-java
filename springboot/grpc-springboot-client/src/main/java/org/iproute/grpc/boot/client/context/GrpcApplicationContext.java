package org.iproute.grpc.boot.client.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * GrpcApplicationContext
 *
 * @author devops@kubectl.net
 */
public interface GrpcApplicationContext extends ApplicationContextAware {

    /**
     * Retrieves the name of the application.
     *
     * @return The name of the application as a String.
     */
    String getApplicationName();


    /**
     * Retrieves the Spring ApplicationContext.
     *
     * @return The Spring ApplicationContext.
     */
    ApplicationContext getSpringApplicationContext();

    /**
     * Determines if the server is ready to accept requests.
     *
     * @return {@code true} if the server is ready, {@code false} otherwise
     */
    boolean serverReady();


    /**
     * Retrieves the server connection information.
     *
     * @return The server connection information as a ServerConn object.
     */
    ServerConn serverConn();

}
