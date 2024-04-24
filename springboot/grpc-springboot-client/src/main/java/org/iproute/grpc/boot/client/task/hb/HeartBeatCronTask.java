package org.iproute.grpc.boot.client.task.hb;

import lombok.RequiredArgsConstructor;
import org.iproute.grpc.boot.client.task.AbstractCronTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

/**
 * HeartBeatCronTask
 *
 * @author devops@kubectl.net
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class HeartBeatCronTask extends AbstractCronTaskService {
    private final TaskScheduler taskScheduler;
    private final HeatBeatRunnable heatBeatRunnable;

    @Override
    public String cron() {
        return "*/3 * * * * ?";
    }

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
        return this.heatBeatRunnable;
    }

}
