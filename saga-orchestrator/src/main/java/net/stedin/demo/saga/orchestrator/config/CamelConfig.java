package net.stedin.demo.saga.orchestrator.config;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
public class CamelConfig {
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

    @Controller
    class SwaggerWelcome {
        @RequestMapping("/swagger-ui")
        public String redirectToUi() {
            return "redirect:/webjars/swagger-ui/index.html?url=/camel/swagger&validatorUrl=";
        }
    }
}
