package com.example.servicea;

import io.opentelemetry.context.Context;
import io.opentelemetry.context.propagation.TextMapPropagator;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Objects;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    private final TextMapPropagator propagator;

    public RestTemplateInterceptor(TextMapPropagator propagator) {
        this.propagator = Objects.requireNonNull(propagator, "propagator must not be null");
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // Get the current context
        Context currentContext = Context.current();

        // Inject the context into the request headers
        propagator.inject(currentContext, request.getHeaders(), (headers, key, value) -> headers.set(key, value));

        // Proceed with the request execution
        return execution.execute(request, body);
    }

}
