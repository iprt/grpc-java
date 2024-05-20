package org.iproute.grpc.boot.proto.task;

/**
 * CronTask
 *
 * @author devops@kubectl.net
 */
public interface CronTask {

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
