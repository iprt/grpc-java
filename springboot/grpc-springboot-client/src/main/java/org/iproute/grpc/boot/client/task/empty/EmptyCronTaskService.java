package org.iproute.grpc.boot.client.task.empty;

import org.iproute.grpc.boot.client.task.AbstractCronTaskService;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

/**
 * EmptyCronTaskService
 *
 * @author devops@kubectl.net
 */
@Service
public class EmptyCronTaskService extends AbstractCronTaskService {

    @Override
    public TaskScheduler getTaskScheduler() {
        return null;
    }

    @Override
    public Runnable getRunnable() {
        return null;
    }

}
