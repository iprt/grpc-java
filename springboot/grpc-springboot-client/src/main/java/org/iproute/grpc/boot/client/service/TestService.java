package org.iproute.grpc.boot.client.service;

/**
 * TestService
 *
 * @author devops@kubectl.net
 */
public interface TestService {
    /**
     * Performs a test operation using the given name.
     *
     * @param name the name to be used for testing
     * @return the result of the test operation as a string
     */
    String test(String name);
}
