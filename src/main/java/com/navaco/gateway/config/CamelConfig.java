package com.navaco.gateway.config;

import com.navaco.gateway.routebuilder.SetupRouteBuilder;
import com.navaco.gateway.service.CamelRouteSetupRefresherService;
import com.navaco.gateway.service.ContextPathEurekaServiceMappingService;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {

    @Bean(name = "camelContext")
    public CamelContext camelContext(ContextPathEurekaServiceMappingService contextPathEurekaServiceMappingService,
                                     CamelRouteSetupRefresherService camelRouteSetupRefresherService) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        camelContext.addRoutes(new SetupRouteBuilder());
        camelRouteSetupRefresherService.setup(camelContext);

        camelContext.start();

        return camelContext;
    }
}
