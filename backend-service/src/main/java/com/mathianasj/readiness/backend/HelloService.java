package com.mathianasj.readiness.backend;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;

import io.quarkus.example.EmptyRequest;
import io.quarkus.example.Greeter;
import io.quarkus.example.HelloReply;
import io.quarkus.example.HelloRequest;
import io.quarkus.example.LoadReply;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;

@GrpcService
public class HelloService implements Greeter {
    @Inject
    LoadService loadService;

    @Override
    public Uni<HelloReply> sayHello(HelloRequest request) {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        return Uni.createFrom().item(() ->
                 HelloReply.newBuilder().setMessage("Hello " + request.getName() + " from " + inetAddress.getHostName()).build()
        );
    }

    @Override
    public Multi<LoadReply> listenToLoad(EmptyRequest request) {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        return Multi.createFrom().ticks().every(Duration.ofSeconds(5))
            .select().when(e -> Uni.createFrom().item(true)).map(l -> 
            LoadReply.newBuilder()
                .setLoad(loadService.getLoad())
                .setHostname(inetAddress.getHostName())
            .build()
        );
    }
}
