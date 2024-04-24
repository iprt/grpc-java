package org.iproute.grpc.boot.client.task;

import org.springframework.beans.factory.InitializingBean;

/**
 * CronTaskService
 *
 * @author devops@kubectl.net
 */
public interface CronTaskService extends InitializingBean {

    /**
     * Returns the cron expression for scheduling a task.
     *
     * @return the cron expression in the form of a string
     */
    default String cron() {
        return "*/5 * * * * ?";
    }

    /**
     * Determines whether the implementing class should start running the task on initialization.
     *
     * @return true if the task should start on initialization, false otherwise.
     */
    default boolean startOnInitializing() {
        return false;
    }

    boolean running();

    void start();

    void stop();

}
