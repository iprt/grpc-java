package org.iproute.grpc.boot.client.task.empty;

import org.iproute.grpc.boot.proto.task.AbstractCronTask;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

/**
 * EmptyCronTaskService
 *
 * @author tech@intellij.io
 */
@Service
public class EmptyCronTask extends AbstractCronTask {

    @Override
    public TaskScheduler getTaskScheduler() {
        return null;
    }

    @Override
    public Runnable getRunnable() {
        return null;
    }

}
