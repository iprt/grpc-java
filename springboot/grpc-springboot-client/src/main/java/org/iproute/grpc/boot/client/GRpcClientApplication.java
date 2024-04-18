package org.iproute.grpc.boot.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * GRpcClientApplication
 *
 * @author devops@kubectl.net
 */
@SpringBootApplication
public class GRpcClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(GRpcClientApplication.class, args);
    }

}
