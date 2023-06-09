package com.example.serviceb;

import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.TracerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {

    @Autowired
    private TracerProvider tracerProvider;


    @Bean
    public Tracer tracer(){
        Tracer tracer = tracerProvider.get("Post-Service");
        return tracer;
    }


}
