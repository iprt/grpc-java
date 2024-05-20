package org.iproute.grpc.boot.proto.task;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Objects;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * CronTaskService
 *
 * @author devops@kubectl.net
 */
public abstract class AbstractCronTask implements CronTask, InitializingBean {

    private final AtomicBoolean status = new AtomicBoolean(false);

    private ScheduledFuture<?> future;

    @Override
    public boolean running() {
        return status.get();
    }

    public abstract TaskScheduler getTaskScheduler();

    public abstract Runnable getRunnable();

    /**
     * Determines whether the implementing class should start running the task on initialization.
     *
     * @return true if the task should start on initialization, false otherwise.
     */
    public boolean startOnInitializing() {
        return false;
    }

    @Override
    public void start() {
        if (!status.compareAndSet(false, true)) {
            return;
        }

        TaskScheduler taskScheduler = getTaskScheduler();
        Runnable runnable = getRunnable();
        if (Objects.isNull(taskScheduler) || Objects.isNull(runnable)) {
            return;
        }
        future = taskScheduler.schedule(runnable, new CronTrigger(cron()));
    }

    @Override
    public void stop() {
        if (!status.compareAndSet(true, false)) {
            return;
        }
        if (future != null) {
            future.cancel(true);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (startOnInitializing()) {
            start();
        }
    }

}
