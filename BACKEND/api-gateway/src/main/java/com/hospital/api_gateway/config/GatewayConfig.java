package com.hospital.api_gateway.config;
import com.hospital.api_gateway.filter.JwtFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final JwtFilter jwtFilter;

    public GatewayConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                // User service route with JWT filter
                .route("user-service", r -> r.path("/users/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://USER-SERVICE"))

                // Doctor service route with JWT filter
                .route("doctor-service", r -> r.path("/api/doctors/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://DOCTOR-SERVICE"))

                // Feedback service route with JWT filter
                .route("feedback-service", r -> r.path("/feedbacks/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("lb://FEEDBACK-SERVICE"))

                // Auth service route (no JWT filter)
                .route("auth-service", r -> r.path("/auth/**")
                        .uri("lb://AUTH-SERVICE"))

                .build();
    }
}