package com.example.servicea;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.TracerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PostRepo {

    @Autowired
    private TracerProvider tracerProvider;

    public List<Post>posts = Arrays.asList(
            new Post(1L, "post 1", ""),
            new Post(2L, "post 2", ""),
            new Post(3L, "post 3", ""),
            new Post(4L, "post 4", ""),
            new Post(5L, "post 5", "")
    );

    public Post getPost(Long postId){
        Tracer tracer = tracerProvider.get("Get-Post");

        Span span = tracer.spanBuilder("DB query").startSpan();

        try{
            return posts.stream().filter(p -> p.getId()==postId).findFirst().get();
        }finally {
            span.end();
        }

    }

}
