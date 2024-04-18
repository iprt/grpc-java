package org.iproute.grpc.boot.client.service;

/**
 * HelloService
 *
 * @author devops@kubectl.net
 */
public interface HelloService {
    /**
     * Returns a greeting message containing "Hello" and the given name.
     *
     * @param name the name to greet
     * @return a greeting message containing "Hello" and the given name
     */
    String sayHello(String name);
}
