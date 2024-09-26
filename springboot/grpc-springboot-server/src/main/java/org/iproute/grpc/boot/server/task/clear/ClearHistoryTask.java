package org.iproute.grpc.boot.server.task.clear;

import lombok.RequiredArgsConstructor;
import org.iproute.grpc.boot.proto.task.AbstractCronTask;
import org.iproute.grpc.boot.server.context.SharedOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

/**
 * ClearHistoryTask
 *
 * @author tech@intellij.io
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class ClearHistoryTask extends AbstractCronTask {
    private final TaskScheduler taskScheduler;
    private final SharedOperator sharedOperator;

    @Override
    public TaskScheduler getTaskScheduler() {
        return this.taskScheduler;
    }

    @Override
    public String cron() {
        // 每一分钟执行一次
        return "0 * * * * ?";
    }

    @Override
    public Runnable getRunnable() {
        return sharedOperator::clearHistoryClients;
    }
}
