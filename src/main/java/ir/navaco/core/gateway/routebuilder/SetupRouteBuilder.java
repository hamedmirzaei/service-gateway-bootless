package ir.navaco.core.gateway.routebuilder;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.core.env.Environment;

public class SetupRouteBuilder extends RouteBuilder {

    private Environment env;

    public SetupRouteBuilder(Environment environment) {
        this.env = environment;
    }

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .host(env.getProperty("rest.host"))
                .port(env.getProperty("rest.port"))
                .bindingMode(RestBindingMode.json)
                .componentProperty("matchOnUriPrefix", "true");

        from("spark-rest:get:health")
                .transform(simple("Server is UP"));

    }
}
