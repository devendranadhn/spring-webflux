package com.example.springwebflux;

import com.example.springwebflux.dto.MultiplyRequestDto;
import com.example.springwebflux.dto.Response;
import com.example.springwebflux.exception.InputValidationFailedResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec01BaseTest extends BaseTest {

    @Autowired
    WebClient webClient;


    @Test
    public void blockTest() {

        Response response = this.webClient
                .get()
                .uri("reactive-math/square/{number}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .block();

        System.out.println(response);

    }


    @Test
    public void stepVerifierTest() {

        Mono<Response> response = this.webClient
                .get()
                .uri("reactive-math/square/{number}", 5)
                .retrieve()
                .bodyToMono(Response.class);

        StepVerifier.create(response)
                        //.expectNextCount(1)
                .expectNextMatches(response1 -> response1.getOutput() == 25)
                .verifyComplete();

        System.out.println(response);

    }


    @Test
    public void fluxTest() {

        Flux<Response> response = this.webClient
                .get()
                .uri("reactive-math/table/{number}", 5)
                .retrieve()
                .bodyToFlux(Response.class);

        StepVerifier.create(response)
                .expectNextCount(10)
                //.expectNextMatches(response1 -> response1.getOutput() == 25)
                .verifyComplete();

        System.out.println(response);

    }


    @Test
    public void fluxStreamTest() {

        Flux<Response> response = this.webClient
                .get()
                .uri("reactive-math/table/{number}/stream", 5)
                .retrieve()
                .bodyToFlux(Response.class);

        StepVerifier.create(response)
                .expectNextCount(10)
                //.expectNextMatches(response1 -> response1.getOutput() == 25)
                .verifyComplete();

        System.out.println(response);

    }



    @Test
    public void postRequestTest() {

        Mono<Response> response = this.webClient
                .post()
                .uri("reactive-math/multiply")
                .bodyValue(multiplyRequestDto(2,5))
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(response)
                .expectNextCount(1)
                //.expectNextMatches(response1 -> response1.getOutput() == 25)
                .verifyComplete();

        System.out.println(response);

    }


    @Test
    public void stepVerifierErrorTest() {

        Mono<Response> response = this.webClient
                .get()
                .uri("reactive-math/square/{number}/throw", 5)
                .retrieve()
                .bodyToMono(Response.class);

        StepVerifier.create(response)
                //.expectNextCount(1)
                        .verifyError();

        System.out.println(response);

    }


    @Test
    public void stepVerifierErrorExchangeTest() {

        Mono<Object> response = this.webClient
                .get()
                .uri("reactive-math/square/{number}/throw", 5)
                .attribute("auth", "oauth")
                .exchangeToMono(this::exchange);

        StepVerifier.create(response)
                .expectNextCount(1)
                        .verifyComplete();

        System.out.println(response);

    }


    private Mono<Object> exchange(ClientResponse clientResponse) {

        if (clientResponse.statusCode().value() == 400) {
            return clientResponse.bodyToMono(InputValidationFailedResponse.class);
        }else {
            return clientResponse.bodyToMono(Response.class);
        }

    }


    private MultiplyRequestDto multiplyRequestDto(int a, int b) {
        MultiplyRequestDto dto =  new MultiplyRequestDto();
        dto.setFirst(a);
        dto.setSecond(b);
        return dto;
    }

}
