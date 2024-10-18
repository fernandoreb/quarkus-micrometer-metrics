package org.farg;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/hello")
@Produces("text/plain")
public class HelloMetrics {
    
    private final MeterRegistry registry;
    private Counter counterTotal;
    private Counter counterOk;
    private Counter counterErro;

    static int count = 0;

    HelloMetrics(MeterRegistry registry) {
        this.registry = registry;
        this.counterTotal = registry.counter("hellometrics.calls","result","total");
        this.counterOk = registry.counter("hellometrics.calls","result","ok");
        this.counterErro = registry.counter("hellometrics.calls","result","erro");
    }

    @GET()
    @Path("counter")
    public String helloCounterMetrics(){
        
        count++;
        counterTotal.increment();
        if(count % 2 == 0)
            counterOk.increment();
        else
            counterErro.increment();

        return "Hello World Counter Metrics";
    }
}