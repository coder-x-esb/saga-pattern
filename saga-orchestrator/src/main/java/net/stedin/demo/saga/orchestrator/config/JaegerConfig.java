package net.stedin.demo.saga.orchestrator.config;

import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.Configuration.SenderConfiguration;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class JaegerConfig {

    @Value("${camel.springboot.name}")
    private String appName;

    @Value("${opentracing.jaeger.http-sender.url}")
    private String jeagerUrl;

    @Bean
    public Tracer getTracer() {
        return new Configuration(this.appName)
            .withSampler(new SamplerConfiguration().withType("const").withParam(1))
            .withReporter(new ReporterConfiguration().withSender(SenderConfiguration.fromEnv().withEndpoint(this.jeagerUrl)))
            .getTracer();
    }
}
