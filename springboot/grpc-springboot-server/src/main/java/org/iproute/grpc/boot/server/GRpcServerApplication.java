package org.iproute.grpc.boot.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * GRpcServerApplication
 *
 * @author devops@kubectl.net
 */
@SpringBootApplication
public class GRpcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GRpcServerApplication.class, args);
    }

}
