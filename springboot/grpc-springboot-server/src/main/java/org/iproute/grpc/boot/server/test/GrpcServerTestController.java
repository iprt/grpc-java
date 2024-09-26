package org.iproute.grpc.boot.server.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.grpc.boot.server.context.ClientConn;
import org.iproute.grpc.boot.server.context.GrpcServerApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * GrpcServerTestController
 *
 * @author tech@intellij.io
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@Slf4j
public class GrpcServerTestController {
    private final GrpcServerApplicationContext grpcServerApplicationContext;

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

    @GetMapping("/liveClients")
    public List<ClientConn> liveClients() {
        return grpcServerApplicationContext.liveClients();
    }

    @GetMapping("/historyClients")
    public List<ClientConn> historyClients() {
        return grpcServerApplicationContext.historyClients();
    }

}
