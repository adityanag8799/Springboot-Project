package com.programming.techie.api_gateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.*;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class Routes {

//    @Bean
//    public RouterFunction<ServerResponse> productServiceRoute() {
//        return GatewayRouterFunctions.route("product-service")
//                .route(RequestPredicates.path("/api/product"), HandlerFunctions.http("http://localhost:8081"))
//                build();
//
        @Bean
        public RouterFunction<ServerResponse> productServiceRoute() {
                return GatewayRouterFunctions.route("product-service")
                        .route(RequestPredicates.path("/api/product"),
                                HandlerFunctions.http())
                        .before(uri("http://localhost:9091"))
                        .build();
            }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute() {
        return GatewayRouterFunctions.route("order-service")
                .route(RequestPredicates.path("/api/order"),
                        HandlerFunctions.http())
                .before(uri("http://localhost:9092"))
                .build();
    }


    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoute() {
        return GatewayRouterFunctions.route("inventory-service")
                .route(RequestPredicates.path("/api/inventory"),
                        HandlerFunctions.http())
                .before(uri("http://localhost:9093"))
                .build();
    }


}


