package com.powerGateway.configuration;

import com.powerGateway.repository.ApiKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ApiKeyFilter implements GlobalFilter
{

    private static final String API_KEY_HEADER = "X-API-KEY";
    private static final String ROLE_HEADER = "X-USER-ROLE";

    private final ApiKeyRepository apiKeyRepository;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(API_KEY_HEADER))
                .flatMap(key -> Mono.justOrEmpty(apiKeyRepository.getRoleIfValid(key)))
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid API Key")))
                .flatMap(role ->
                        chain.filter(
                                exchange.mutate()
                                        .request(request -> request.headers(
                                                headers -> headers.set(ROLE_HEADER, role)))
                                        .build()
                        )
                );
    }
}
