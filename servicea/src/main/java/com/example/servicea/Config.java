package com.example.servicea;

import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.TracerProvider;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.context.propagation.TextMapPropagator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Objects;

@Configuration
public class Config {

    @Autowired
    private TracerProvider tracerProvider;

    @Bean
    public RestTemplate restTemplate() {

        RestTemplate restTemplate = new RestTemplateBuilder().build();
        restTemplate.setInterceptors(Collections.singletonList(
                new RestTemplateInterceptor(W3CTraceContextPropagator.getInstance())
        ));

        return restTemplate;
    }

    public ContextPropagators contextPropagators(){
        return ContextPropagators.create(W3CTraceContextPropagator.getInstance());
    }


    @Bean
    public Tracer tracer(){
        Tracer tracer = tracerProvider.get("Post-Service");
        return tracer;
    }

}

