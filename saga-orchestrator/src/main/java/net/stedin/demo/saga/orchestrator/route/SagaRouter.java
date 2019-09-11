package net.stedin.demo.saga.orchestrator.route;

import net.stedin.demo.saga.orchestrator.route.domain.SaveAndPlanWerkorder;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SagaRouter extends RouteBuilder {

    @Override
    public void configure() {
        //@formatter:off
        rest().post("/aanmakenEnPlannenWerkorder").route()
            .routeId("aanmakenEnPlannenWerkorderHttp")
            .onException(RuntimeException.class)
                .handled(true)
                .setBody(simple("Aanmaken en plannen van werkorder ${header.id} mislukt"))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
                .end()
            .to("direct:aanmakenEnPlannenWerkorder");

            from("direct:aanmakenEnPlannenWerkorder")
                .routeId("aanmakenEnPlannenWerkorder")
                .errorHandler(noErrorHandler())
                .unmarshal().json(JsonLibrary.Jackson, SaveAndPlanWerkorder.class)
                .setHeader("medewerkerId", simple("${body.medewerkerId}"))
                .process(e -> e.getIn().setHeader("id", UUID.randomUUID().toString()))
                .log("Uitvoeren saga ${header.id}")
                /*.process(e -> e.getIn().setBody(SaveAndPlanWerkorder.builder()
                        .medewerkerId(1L)
                        .datum(LocalDate.now())
                        .werkorder(Werkorder.builder()
                                .omschrijving("Mijn omschrijving").build()).build()))*/
                .setProperty("Request", body())
                .saga()
                    .completion("direct:complete")
                .multicast(new MulticastPropertyAggregator("Werkorder", "Medewerker"))
                    .to("direct:aanmakenWerkorder", "direct:reserveerMedewerker")
                    .end()
                .transform(method(Transformations.class, "transformToPlanWerkorder"))
                .to("direct:aanmakenPlanning");

        from("direct:complete")
                 .log("Saga voltooid");

        from("direct:aanmakenWerkorder").routeId("aanmakenWerkorder")
                .saga()
                    .propagation(SagaPropagation.MANDATORY)
                    .option("id", header("id"))
                    .compensation("direct:annuleerWerkorder")
                .choice()
                    .when(x -> Math.random() >= 0.85)
                        .throwException(new RuntimeException("Aanmaken werkorder gefaald"))
                    .otherwise()
                        .log("Werkorder ${header.id} aangemaakt")
                        .setBody(constant("{\"id\":1,\"omschrijving\":\"Mijn werkorder\"}"))
                        /*.to("http4://localhost:9080/werkorders")*/;
        from("direct:annuleerWerkorder").routeId("annulerenWerkorder")
                .log("Werkorder ${header.id} geannuleerd");

        from("direct:reserveerMedewerker").routeId("reserveerMedewerker")
                .saga()
                    .propagation(SagaPropagation.MANDATORY)
                    .option("id", header("id"))
                    .option("medewerkerId", header("medewerkerId"))
                    .compensation("direct:vrijgevenMedewerker")
                .setHeader("reserveeringsDatum", method(Transformations.class, "transformDate(${body.datum})"))
                .setBody(constant(null))
                .toD("http4://localhost:9081/medewerkers/${header.medewerkerId}/reserveer?datum=${header.reserveeringsDatum}" +
                        "&httpMethod=POST&bridgeEndpoint=true")
                .log("Medewerker ${header.medewerkerId} gereserveerd");
        from("direct:vrijgevenMedewerker").routeId("vrijgevenMedewerker")
                .setBody(constant(null))
                .toD("http4://localhost:9081/medewerkers/${header.medewerkerId}/vrijgeven" +
                        "?httpMethod=POST&bridgeEndpoint=true")
                .log("Medewerker ${header.medewerkerId} vrijgegeven");

        from("direct:aanmakenPlanning").routeId("aanmakenPlanning")
                .saga()
                    .propagation(SagaPropagation.MANDATORY)
                    .option("id", header("id"))
                    .compensation("direct:annulerenPlanning")
                .choice()
                    .when(x -> Math.random() >= 0.85)
                        .throwException(new RuntimeException("Aanmaken planning gefaald"))
                    .otherwise()
                        .log("Planning ${header.id} aangemaakt")
                        .setBody(constant("{\"medewerkerId\":1,\"datum\":\"2019-09-10\",\"werkorderId\":\"1\"}"))
                        /*.to("http4://localhost:9082/planningen")*/;
        from("direct:annulerenPlanning").routeId("annulerenPlanning")
                .log("Planning ${header.id} geannuleerd");
        //@formatter:on
    }
}