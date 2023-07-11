package com.example.springwebflux.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientTestConfig {


    @Bean
    public WebClient getWebClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .filter(this::generateSessionToken)
                .build();
    }


    private Mono<ClientResponse> generateSessionToken(ClientRequest request, ExchangeFunction exchangeFunction) {

        ClientRequest clientRequest = request.attribute("auth")
                .map(v ->  v.equals("basic") ? withBasicAuth(request) : withOAuth(request))
                .orElse(request);

        return exchangeFunction.exchange(clientRequest);

    }


    private ClientRequest withBasicAuth(ClientRequest clientRequest) {
        return ClientRequest.from(clientRequest).headers(h -> h.setBearerAuth("basic-auth")).build();
    }

    private ClientRequest withOAuth(ClientRequest clientRequest) {
        return ClientRequest.from(clientRequest).headers( h -> h.setBearerAuth("bearer-token")).build();
    }

}
