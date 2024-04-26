package org.iproute.grpc.boot.task;

/**
 * CronTaskService
 *
 * @author devops@kubectl.net
 */
public interface CronTaskService {

    /**
     * Returns the cron expression for scheduling a task.
     *
     * @return the cron expression in the form of a string
     */
    default String cron() {
        return "*/5 * * * * ?";
    }

    boolean running();

    void start();

    void stop();

}
