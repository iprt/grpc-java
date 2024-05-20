package org.iproute.grpc.boot.client.test;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.grpc.boot.client.config.anno.ServerReadyThen;
import org.iproute.grpc.boot.client.context.GrpcApplicationContext;
import org.iproute.grpc.boot.client.context.ServerConn;
import org.iproute.grpc.boot.client.entities.GreetReq;
import org.iproute.grpc.boot.client.entities.GreetResp;
import org.iproute.grpc.boot.client.service.TestService;
import org.iproute.grpc.boot.proto.task.CronTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GrpcClientTestController
 *
 * @author devops@kubectl.net
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@Slf4j
public class GrpcClientTestController {

    private final GrpcApplicationContext grpcApplicationContext;
    private final TestService testService;

    @GetMapping("/")
    @ServerReadyThen
    public Map<String, Object> grpc() {
        String grpc = testService.test(
                grpcApplicationContext.getApplicationName()
        );
        return Map.of(
                "grpc", grpc,
                "serverReady", serverReady(),
                "serverConn", serverConn()
        );
    }

    @GetMapping("/greet")
    public GreetResp greet(@RequestBody GreetReq req) {
        return testService.greet(req);
    }

    @GetMapping("/serverReady")
    public boolean serverReady() {
        return grpcApplicationContext.serverReady();
    }

    @GetMapping("/serverConn")
    public ServerConn serverConn() {
        return grpcApplicationContext.serverConn();
    }

    private final List<CronTask> tasks;

    @GetMapping("/startTasks")
    public List<TaskStatus> startTasks() {
        tasks.forEach(CronTask::start);
        return taskStatus();
    }

    @GetMapping("/stopTasks")
    public List<TaskStatus> stopTasks() {
        tasks.forEach(CronTask::stop);
        return taskStatus();
    }

    @GetMapping("/tasks")
    public List<TaskStatus> taskStatus() {
        return tasks.stream()
                .map(task -> TaskStatus.builder()
                        .className(task.getClass().getName())
                        .cron(task.cron())
                        .running(task.running())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Builder
    @Data
    public static class TaskStatus {
        private String className;
        private String cron;
        private boolean running;
    }


    @ControllerAdvice
    @Slf4j
    public static class GlobalExceptionHandler {

        @ExceptionHandler(Exception.class)
        @ResponseBody
        public ResponseEntity<Map<String, Object>> handleException(Exception e) {
            log.error("handleException {}", e.getMessage());
            return ResponseEntity.ok(Map.of("code", 500, "msg", e.getMessage()));
        }
    }
}