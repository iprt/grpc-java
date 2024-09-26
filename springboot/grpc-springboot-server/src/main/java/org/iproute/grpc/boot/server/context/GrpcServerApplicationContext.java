package org.iproute.grpc.boot.server.context;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * GrpcServerApplicationContext
 *
 * @author tech@intellij.io
 */
public interface GrpcServerApplicationContext extends ApplicationContextAware {

    /**
     * Retrieves the Spring application context associated with the GrpcServerApplicationContext.
     *
     * @return The Spring application context.
     */
    ApplicationContext getSpringApplicationContext();

    /**
     * Retrieves the list of connected clients.
     *
     * @return A List of ClientConn objects representing the connected clients.
     */
    List<ClientConn> liveClients();

    /**
     * Retrieves the list of historical clients.
     *
     * @return A List of ClientConn objects representing the historical clients.
     */
    List<ClientConn> historyClients();

    @RequiredArgsConstructor(onConstructor = @__(@Autowired))
    @Component
    @Slf4j
    class DefaultGrpcServerApplicationContext implements GrpcServerApplicationContext {
        private final SharedOperator sharedOperator;

        private ApplicationContext applicationContext;

        @Override
        public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
            log.info("ApplicationContext Aware");
            this.applicationContext = applicationContext;
        }

        @Override
        public ApplicationContext getSpringApplicationContext() {
            return this.applicationContext;
        }

        @Override
        public List<ClientConn> liveClients() {
            return sharedOperator.getLiveClients();
        }

        @Override
        public List<ClientConn> historyClients() {
            return sharedOperator.getHistoryClients();
        }

    }

}
