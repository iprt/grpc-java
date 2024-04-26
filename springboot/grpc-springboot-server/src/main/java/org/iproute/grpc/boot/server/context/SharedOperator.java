package org.iproute.grpc.boot.server.context;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.grpc.boot.context.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SharedOperator
 *
 * @author devops@kubectl.net
 */
public interface SharedOperator {

    /**
     * Moves the given address up.
     *
     * @param address the address to be moved up
     */
    void up(Address address);

    /**
     * Moves the given address down.
     *
     * @param address the address to be moved down
     */
    void down(Address address);

    /**
     * Retrieves the list of connected clients.
     *
     * @return A List of ClientConn objects representing the connected clients.
     */
    List<ClientConn> getLiveClients();

    /**
     * Retrieves the list of ClientConn objects representing the historical connected clients.
     *
     * @return A List of ClientConn objects representing the historical connected clients.
     */
    List<ClientConn> getHistoryClients();

    /**
     * Clears the history of clients.
     */
    void clearHistoryClients();

    @RequiredArgsConstructor(onConstructor = @__(@Autowired))
    @Component
    @Slf4j
    class DefaultSharedOperator implements SharedOperator {
        private final Shared shared;
        private final Lock lock = new ReentrantLock();

        @Override
        public void up(Address address) {
            lock.lock();
            try {
                shared.getLive().put(address, ClientConn.up(address));
            } finally {
                lock.unlock();
            }
        }

        @Override
        public void down(Address address) {
            lock.lock();
            try {
                shared.getLive().remove(address);
                log.info("add history");
                shared.getHistory().add(ClientConn.down(address));
            } finally {
                lock.unlock();
            }
        }

        @Override
        public List<ClientConn> getLiveClients() {
            return shared.getLive().values().stream().toList();
        }

        @Override
        public List<ClientConn> getHistoryClients() {
            return shared.getHistory().stream().toList();
        }

        @Override
        public void clearHistoryClients() {
            lock.lock();
            try {
                shared.getHistory().clear();
            } finally {
                lock.unlock();
            }
        }
    }

}
