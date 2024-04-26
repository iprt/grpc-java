package org.iproute.grpc.boot.client.test;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.grpc.boot.client.config.anno.ServerReadyThen;
import org.iproute.grpc.boot.client.context.GrpcApplicationContext;
import org.iproute.grpc.boot.client.context.ServerConn;
import org.iproute.grpc.boot.client.service.TestService;
import org.iproute.grpc.boot.client.task.CronTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * HttpTestController
 *
 * @author devops@kubectl.net
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@Slf4j
public class HttpTestController {
    private final TestService testService;
    private final GrpcApplicationContext grpcApplicationContext;

    @ControllerAdvice
    @Slf4j
    public static class GlobalExceptionHandler {

        @ExceptionHandler(Exception.class)
        @ResponseBody
        public ResponseEntity<String> handleException(Exception e) {
            log.error("handleException {}", e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }

    }

    @GetMapping("/")
    @ServerReadyThen
    public String grpc() {
        return testService.test(
                grpcApplicationContext.getApplicationName()
        );
    }

    @GetMapping("/serverReady")
    public boolean serverReady() {
        return grpcApplicationContext.serverReady();
    }

    @GetMapping("/serverConn")
    public ServerConn serverConn() {
        return grpcApplicationContext.serverConn();
    }

    @GetMapping("/startTasks")
    public List<TaskStatus> startTasks() {
        grpcApplicationContext.getSpringApplicationContext()
                .getBeansOfType(CronTaskService.class)
                .values().forEach(CronTaskService::start);

        return taskStatus();
    }

    @GetMapping("/stopTasks")
    public List<TaskStatus> stopTasks() {
        grpcApplicationContext.getSpringApplicationContext()
                .getBeansOfType(CronTaskService.class)
                .values().forEach(CronTaskService::stop);
        return taskStatus();
    }

    @GetMapping("/tasks")
    public List<TaskStatus> taskStatus() {
        Map<String, CronTaskService> tasks = grpcApplicationContext.getSpringApplicationContext().getBeansOfType(CronTaskService.class);
        return tasks.keySet().stream()
                .map(name -> {
                            CronTaskService task = tasks.get(name);
                            return TaskStatus.builder()
                                    .name(name)
                                    .className(task.getClass().getName())
                                    .cron(task.cron())
                                    .running(task.running())
                                    .build();
                        }
                )
                .collect(Collectors.toList());
    }

    @Builder
    @Data
    public static class TaskStatus {
        private String name;
        private String className;
        private String cron;
        private boolean running;
    }

}