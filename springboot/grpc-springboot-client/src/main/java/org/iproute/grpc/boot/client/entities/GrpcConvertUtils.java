package org.iproute.grpc.boot.client.entities;

import org.iproute.grpc.multi.GreetResponse;

/**
 * GrpcConvertUtils
 *
 * @author tech@intellij.io
 */
public class GrpcConvertUtils {

    public static GreetResp convert(final GreetResponse req) {
        return GreetResp.builder()
                .greeting(req.getMsg())
                .build();
    }

}
