package org.iproute.grpc.boot.client.context.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.grpc.boot.client.context.ServerConn;
import org.iproute.grpc.boot.client.context.Shared;
import org.iproute.grpc.boot.client.context.SharedOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SharedOperator
 *
 * @author devops@kubectl.net
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Slf4j
public class SharedOperatorImpl implements SharedOperator {
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
