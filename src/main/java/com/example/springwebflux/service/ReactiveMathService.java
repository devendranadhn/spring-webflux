package com.example.springwebflux.service;


import com.example.springwebflux.dto.MultiplyRequestDto;
import com.example.springwebflux.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ReactiveMathService {


    public Mono<Response> findSquare(int number) {

        return Mono.fromSupplier(() -> number * number)
                .map(Response::new);

    }



    public Flux<Response> multiplicationTable(int number) {


        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
                //.doOnNext(i -> SleepUtil.sleepSeconds(1))
                .doOnNext(i -> System.out.println("Reactive Math Service processing element " + i))
                .map(i -> new Response(number * i));

    }


    public Mono<Response> multiply(Mono<MultiplyRequestDto> dtoMono) {

        return dtoMono
                .map(dto -> dto.getFirst() * dto.getSecond())
                .map(Response::new);

    }





}
