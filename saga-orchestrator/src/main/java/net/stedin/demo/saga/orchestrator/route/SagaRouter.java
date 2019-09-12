package net.stedin.demo.saga.orchestrator.route;

import net.stedin.demo.saga.orchestrator.route.domain.Planning;
import net.stedin.demo.saga.orchestrator.route.domain.SaveAndPlanWerkorder;
import net.stedin.demo.saga.orchestrator.route.domain.Werkorder;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class SagaRouter extends RouteBuilder {

    static Map<String, Long> werkorders = new HashMap<>();
    static Map<String, Long> planningen = new HashMap<>();

    @Override
    public void configure() {
        //@formatter:off
        onException(Exception.class)
            .maximumRedeliveries(0);

        restConfiguration().bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true")
            .contextPath("/camel")
            .apiContextPath("/swagger")
                .apiProperty("api.title", "Werkorder aanmaken en plannen API")
                .apiProperty("api.version", "1.0")
                .apiProperty("api.description", "Maakt werkorder aan en plant deze voor medewerker in op de aangegeven datum")
                .apiProperty("api.contact.name", "Serkan en Alvin");

        rest().post("/aanmakenEnPlannenWerkorder")
                .type(SaveAndPlanWerkorder.class).outType(Planning.class).apiDocs(true).route()
            .routeId("aanmakenEnPlannenWerkorderHttp")
            .onException(Exception.class)
                .handled(true)
                .setBody(simple("{\"error\": \"Aanmaken en/of plannen van werkorder mislukt\"}"))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
                .end()
            .to("direct:aanmakenEnPlannenWerkorder");

            from("direct:aanmakenEnPlannenWerkorder")
                .routeId("aanmakenEnPlannenWerkorder")
                .errorHandler(noErrorHandler())
                .setHeader("medewerkerId", simple("${body.medewerkerId}"))
                .process(e -> e.getIn().setHeader("id", UUID.randomUUID().toString()))
                .log("Uitvoeren saga ${header.id}")
                .setProperty("Request", body())
                .saga()
                .multicast(new MulticastPropertyAggregator("Werkorder", "Medewerker"))
                    .to("direct:aanmakenWerkorder", "direct:reserveerMedewerker")
                    .end()
                .transform(method(Transformations.class, "transformToPlanWerkorder"))
                .to("direct:aanmakenPlanning");

        from("direct:aanmakenWerkorder").routeId("aanmakenWerkorder")
                .saga()
                    .propagation(SagaPropagation.MANDATORY)
                    .option("id", header("id"))
                    .compensation("direct:annuleerWerkorder")
                .transform(method(Transformations.class, "transformToWerkorder"))
                .marshal().json(JsonLibrary.Jackson)
                .toD("http4://localhost:9080/werkorders?httpMethod=POST&bridgeEndpoint=true")
                .unmarshal().json(JsonLibrary.Jackson, Werkorder.class)
                .process(e -> werkorders.put(e.getIn().getHeader("id",
                        String.class), e.getIn().getBody(Werkorder.class).getId()))
                .log("Werkorder ${body.id} aangemaakt");
        from("direct:annuleerWerkorder").routeId("annulerenWerkorder")
                .saga()
                    .option("id", header("id"))
                .process(e -> e.getIn().setHeader("werkorderId", werkorders.get(e.getIn().getHeader("id", String.class))))
                .filter(header("werkorderId").isNotNull())
                .toD("http4://localhost:9080/werkorders/${header.werkorderId}?httpMethod=DELETE&bridgeEndpoint=true")
                .log("Werkorder ${header.werkorderId} verwijderd");

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
                .errorHandler(noErrorHandler())
                .saga()
                    .propagation(SagaPropagation.MANDATORY)
                    .option("id", header("id"))
                    .compensation("direct:annulerenPlanning")
                .marshal().json(JsonLibrary.Jackson)
                .toD("http4://localhost:9082/planningen?httpMethod=POST&bridgeEndpoint=true")
                .unmarshal().json(JsonLibrary.Jackson, Planning.class)
                .process(e -> planningen.put(e.getIn().getHeader("id",
                        String.class), e.getIn().getBody(Planning.class).getId()))
                .log("Planning ${body.id} aangemaakt");
        from("direct:annulerenPlanning").routeId("annulerenPlanning")
                .process(e -> e.getIn().setHeader("planningId", planningen.get(e.getIn().getHeader("id", String.class))))
                .filter(header("planningId").isNotNull())
                .log("Planning ${header.planningId} geannuleerd");
        //@formatter:on
    }
}
