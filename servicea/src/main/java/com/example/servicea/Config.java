package com.example.servicea;

import io.opentracing.Tracer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public Tracer tracer(){
        io.jaegertracing.Configuration.SamplerConfiguration samplerConfiguration = new io.jaegertracing.Configuration.SamplerConfiguration()
                .withType("const").withParam(1);
        io.jaegertracing.Configuration.ReporterConfiguration reporterConfiguration = new io.jaegertracing.Configuration.ReporterConfiguration()
                .withLogSpans(true);

        return new io.jaegertracing.Configuration("post-service")
                .withSampler(samplerConfiguration)
                .withReporter(reporterConfiguration)
                .getTracer();

    }

}
