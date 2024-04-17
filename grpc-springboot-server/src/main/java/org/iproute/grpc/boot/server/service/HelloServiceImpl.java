package org.iproute.grpc.boot.server.service;

import com.google.protobuf.ProtocolStringList;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.iproute.grpc.api.HelloProto;
import org.iproute.grpc.api.HelloServiceGrpc;

/**
 * HelloServiceImpl 业务处理
 *
 * @author zhuzhenjie
 */
@GrpcService
@Slf4j
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void hello(HelloProto.HelloRequest request, StreamObserver<HelloProto.HelloResponse> responseObserver) {

        // 1. 接收client的请求参数
        String name = request.getName();
        // 2. 业务处理
        log.info("接收到客户端 request | request.name = {}", name);

        // 3.1 封装响应
        HelloProto.HelloResponse.Builder builder = HelloProto.HelloResponse.newBuilder();

        // 3.2 填充数据
        builder.setResult("hello method invoke ok");

        HelloProto.HelloResponse helloResponse = builder.build();

        responseObserver.onNext(helloResponse); // 通过这个方法 把响应的消息 回传client
        responseObserver.onCompleted(); // 通知client 整个服务结束。底层返回标记
    }

    @Override
    public void hello1(HelloProto.HelloRequest1 request, StreamObserver<HelloProto.HelloResponse1> responseObserver) {

        ProtocolStringList nameList = request.getNameList();

        for (String name : nameList) {
            log.info("接收到客户客户端 request.nameList | request.name = {}", name);
        }


        HelloProto.HelloResponse1.Builder builder = HelloProto.HelloResponse1.newBuilder();
        builder.setResult("hello1 method invoke ok");
        HelloProto.HelloResponse1 response1 = builder.build();

        responseObserver.onNext(response1);
        responseObserver.onCompleted();

    }

    @Override
    public void c2ss(HelloProto.HelloRequest request, StreamObserver<HelloProto.HelloResponse> responseObserver) {
        // 1. 接收参数
        String name = request.getName();
        // 2. 处理业务
        log.info("client <===> server-stream | name = {}", name);
        // 3. 根据业务处理的结果，提供响应
        for (int i = 0; i < 10; i++) {
            HelloProto.HelloResponse.Builder builder = HelloProto.HelloResponse.newBuilder();
            builder.setResult("result " + i);
            HelloProto.HelloResponse response = builder.build();

            responseObserver.onNext(response);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // 整个操作结束
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<HelloProto.HelloRequest> cs2s(StreamObserver<HelloProto.HelloResponse> responseObserver) {
        // 监控请求消息
        return new StreamObserver<HelloProto.HelloRequest>() {

            private final StringBuilder nameBuilder = new StringBuilder();

            @Override
            public void onNext(HelloProto.HelloRequest request) {
                String name = request.getName();
                nameBuilder.append(name);
                log.info("接收到客户端发送的一条消息: {}", name);
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {
                // 客户端结束了
                String name = nameBuilder.toString();
                log.info("客户端 onCompleted | name = {}", name);

                HelloProto.HelloResponse.Builder builder = HelloProto.HelloResponse.newBuilder();
                builder.setResult("cs2s method invoke ok");

                HelloProto.HelloResponse response = builder.build();

                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<HelloProto.HelloRequest> cs2ss(StreamObserver<HelloProto.HelloResponse> responseObserver) {

        return new StreamObserver<HelloProto.HelloRequest>() {
            @Override
            public void onNext(HelloProto.HelloRequest request) {
                String name = request.getName();
                log.info("接受到client 提交的消息 | name =  {}", name);
                responseObserver.onNext(HelloProto.HelloResponse.newBuilder().setResult("response " + name + " result ").build());
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
