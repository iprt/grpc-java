package org.iproute.grpc.boot.server.task.print;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.grpc.boot.proto.task.AbstractCronTask;
import org.iproute.grpc.boot.server.context.GrpcServerApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

/**
 * ClientConnPrintTask
 *
 * @author devops@kubectl.net
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
@Slf4j
public class ClientConnPrintTask extends AbstractCronTask {
    private final TaskScheduler taskScheduler;
    private final GrpcServerApplicationContext grpcServerApplicationContext;

    @Override
    public boolean startOnInitializing() {
        return true;
    }

    @Override
    public TaskScheduler getTaskScheduler() {
        return this.taskScheduler;
    }

    @Override
    public Runnable getRunnable() {
        return () -> {
            log.debug("List Client's Connections .START");
            grpcServerApplicationContext
                    .liveClients()
                    .forEach(client -> log.debug("up client :{}", client));

            log.debug("List Client's Connections .END");
        };

    }

}
