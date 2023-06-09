package com.example.serviceb;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.context.propagation.TextMapGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    private CommentRepo commentRepo;

    public CommentController(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    @Autowired
    private ContextPropagators contextPropagators;

    @Autowired
    private Tracer tracer;

    @Value("${spring.application.name}")
    private String applicationName;
    @GetMapping("{postId}")
    public List<Comment>getPostComments(@PathVariable("postId") Long postId, @RequestHeader("traceparent")String traceParentHeader){


        Context extractedContext = contextPropagators.getTextMapPropagator().extract(Context.current(), new HttpHeaders(), getter);

        Span span = tracer.spanBuilder("Received external http request")
                .setParent(extractedContext)
                .setSpanKind(SpanKind.SERVER)
                .startSpan();

        try{
            var traceId = span.getSpanContext().getTraceId();

            logger.info(applicationName+" - "+traceId+" - Received request from post service ");
            return commentRepo.getByPostId(postId);
        }finally {
            span.end();
        }
    }

    TextMapGetter<HttpHeaders> getter =
            new TextMapGetter<>() {
                @Override
                public String get(HttpHeaders carrier, String key) {
                    if (carrier.containsKey(key)) {
                        return carrier.get(key).get(0);
                    }
                    return null;
                }

                @Override
                public Iterable<String> keys(HttpHeaders carrier) {
                    return carrier.keySet();
                }
            };

}
