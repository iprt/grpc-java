package org.iproute.grpc.boot.client.context;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * GrpcApplicationContext
 *
 * @author tech@intellij.io
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


    @RequiredArgsConstructor(onConstructor = @__(@Autowired))
    @Service
    @Slf4j
    class DefaultGrpcApplicationContextImpl implements GrpcApplicationContext {
        private final SharedOperator sharedOperator;
        @Value("${spring.application.name}")
        private String applicationName;

        private ApplicationContext applicationContext;

        @Override
        public String getApplicationName() {
            return this.applicationName;
        }

        @Override
        public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
            log.debug("ApplicationContext Aware");
            this.applicationContext = applicationContext;
        }

        @Override
        public ApplicationContext getSpringApplicationContext() {
            return this.applicationContext;
        }

        @Override
        public boolean serverReady() {
            return sharedOperator.getServerReady();
        }

        @Override
        public ServerConn serverConn() {
            return sharedOperator.getSeverConn();
        }
    }

}
