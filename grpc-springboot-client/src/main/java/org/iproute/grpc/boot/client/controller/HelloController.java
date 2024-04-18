package org.iproute.grpc.boot.client.controller;

import lombok.RequiredArgsConstructor;
import org.iproute.grpc.boot.client.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * SendController
 *
 * @author devops@kubectl.net
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class HelloController {

    private final HelloService helloService;

    @GetMapping("/grpc")
    public String grpc() {
        return helloService.sayHello(
                UUID.randomUUID().toString()
        );
    }

}