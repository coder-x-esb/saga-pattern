package net.stedin.demo.saga.orchestrator;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public CamelContextConfiguration contextConfiguration() {
        return new CamelContextConfiguration() {
            public void beforeApplicationStart(CamelContext context) {
                try {
                    context.addService(new org.apache.camel.impl.saga.InMemorySagaService());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void afterApplicationStart(CamelContext camelContext) {
            }
        };
    }
}
