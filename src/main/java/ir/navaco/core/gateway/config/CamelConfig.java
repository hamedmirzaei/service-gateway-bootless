package ir.navaco.core.gateway.config;

import ir.navaco.core.gateway.eureka.EurekaServiceDiscovery;
import ir.navaco.core.gateway.routebuilder.SetupRouteBuilder;
import ir.navaco.core.gateway.service.CamelRouteSetupRefresherService;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.cloud.ServiceCallConfigurationDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {

    @Bean(name = "camelContext")
    public CamelContext camelContext(CamelRouteSetupRefresherService camelRouteSetupRefresherService) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();

        // eureka service discovery
        EurekaServiceDiscovery eurekaServiceDiscovery = new EurekaServiceDiscovery();
        eurekaServiceDiscovery.setCamelContext(camelContext);
        // configure camel service call
        ServiceCallConfigurationDefinition config = new ServiceCallConfigurationDefinition();
        //config.setLoadBalancer(loadBalancer);
        config.setServiceDiscovery(eurekaServiceDiscovery);
        // register configuration
        camelContext.setServiceCallConfiguration(config);
        camelContext.addRoutes(new SetupRouteBuilder());
        camelRouteSetupRefresherService.setup(camelContext);
        camelContext.start();

        return camelContext;
    }
}
