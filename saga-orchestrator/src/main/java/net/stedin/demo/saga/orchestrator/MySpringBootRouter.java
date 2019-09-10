package net.stedin.demo.saga.orchestrator;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MySpringBootRouter extends RouteBuilder {

    @Override
    public void configure() {
        from("timer:hello?period=5s").routeId("timer")
            .saga()
            .setHeader("id", header(Exchange.TIMER_COUNTER))
            .setHeader(Exchange.HTTP_METHOD, constant("POST"))
            .log("Executing saga #${header.id}")
            .to("http4://localhost:9081/medewerkers?httpMethod=GET")
            .to("http4://localhost:8080/api/flight/buy");
    }

}
