package net.stedin.werkorderservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import javax.ws.rs.Produces;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

/**
 * JacksonConfig
 */
@ApplicationScoped
public class JacksonConfig {

    @Produces
    @Singleton
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new StdDateFormat()
            .withLocale(new Locale("nl", "NL"))
            .withTimeZone(TimeZone.getTimeZone(ZoneId.of("Europe/Amsterdam")))
            .withColonInTimeZone(true));
        return mapper;
    }
}