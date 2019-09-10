package net.stedin.demo.saga.orchestrator;

import org.apache.camel.opentracing.starter.CamelOpenTracing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@CamelOpenTracing
@SpringBootApplication
public class CamelSagaApp {

    public static void main(String[] args) {
        SpringApplication.run(CamelSagaApp.class, args);
    }

}
