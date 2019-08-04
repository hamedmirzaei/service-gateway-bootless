package com.navaco.gateway.routebuilder;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@PropertySource("classpath:application.properties")
public class SetupRouteBuilder extends RouteBuilder {

    @Autowired
    Environment env;

    public SetupRouteBuilder() {
    }

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .host("localhost")
                .port(8089)
                .bindingMode(RestBindingMode.json)
                .componentProperty("matchOnUriPrefix", "true");

        from("spark-rest:get:health")
                .transform(simple("Server is UP"));

    }
}
