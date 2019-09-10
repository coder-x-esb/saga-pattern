package net.stedin.demo.saga.orchestrator;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.Arrays;
import java.util.List;

public class MulticastPropertyAggregator implements AggregationStrategy {

    private List<String> indexNames;

    public MulticastPropertyAggregator() {
    }

    public MulticastPropertyAggregator(String... properties) {
        this.indexNames = Arrays.asList(properties);
    }

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (newExchange.getException() != null) {
            if (newExchange.getException() instanceof RuntimeException) {
                throw (RuntimeException) newExchange.getException();
            }
            throw new RuntimeException(newExchange.getException());
        }
        Integer multicastIndex = newExchange.getProperty(Exchange.MULTICAST_INDEX, Integer.class);
        if (indexNames.size() > multicastIndex) {
            newExchange.setProperty(indexNames.get(multicastIndex), newExchange.getIn().getBody());
        }
        if (oldExchange != null) {
            newExchange.getProperties().putAll(oldExchange.getProperties());
        }
        newExchange.getIn().setBody(newExchange.getProperty("Request"));
        return newExchange;
    }
}
