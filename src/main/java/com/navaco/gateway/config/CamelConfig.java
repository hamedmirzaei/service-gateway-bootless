package com.navaco.gateway.config;

import com.navaco.gateway.eureka.EurekaServiceDiscovery;
import com.navaco.gateway.routebuilder.SetupRouteBuilder;
import com.navaco.gateway.service.CamelRouteSetupRefresherService;
import org.apache.camel.CamelContext;
import org.apache.camel.cloud.ServiceChooser;
import org.apache.camel.cloud.ServiceDefinition;
import org.apache.camel.cloud.ServiceDiscovery;
import org.apache.camel.component.consul.ConsulConfiguration;
import org.apache.camel.component.consul.cloud.ConsulServiceDiscovery;
import org.apache.camel.component.ribbon.RibbonConfiguration;
import org.apache.camel.component.ribbon.cloud.RibbonServiceLoadBalancer;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.cloud.DefaultServiceDiscovery;
import org.apache.camel.impl.cloud.StaticServiceDiscovery;
import org.apache.camel.model.cloud.ServiceCallConfigurationDefinition;
import org.apache.camel.spring.cloud.CamelSpringCloudDiscoveryClient;
import org.apache.camel.spring.cloud.CamelSpringCloudServiceDiscovery;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CamelConfig {

    @Bean(name = "camelContext")
    public CamelContext camelContext(CamelRouteSetupRefresherService camelRouteSetupRefresherService) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();

        /*CamelSpringCloudServiceDiscovery camelSpringCloudServiceDiscovery =
                new CamelSpringCloudServiceDiscovery(camelSpringCloudDiscoveryClient);
        CamelSpringCloudDiscoveryClient camelSpringCloudDiscoveryClient =
                new CamelSpringCloudDiscoveryClient("", camelSpringCloudServiceDiscovery);*/


        /*DefaultServiceDiscovery defaultServiceDiscovery = new DefaultServiceDiscovery();
        defaultServiceDiscovery.setCamelContext(camelContext);*/

        EurekaServiceDiscovery eurekaServiceDiscovery = new EurekaServiceDiscovery();
        eurekaServiceDiscovery.setCamelContext(camelContext);

        /*SimpleDiscoveryProperties prop = new SimpleDiscoveryProperties();
        SimpleDiscoveryClient cc = new SimpleDiscoveryClient(prop);
        CamelSpringCloudServiceDiscovery camelSpringCloudServiceDiscovery =
                new CamelSpringCloudServiceDiscovery(cc);*/


        /*ConsulConfiguration c = new ConsulConfiguration();
        ConsulServiceDiscovery consulServiceDiscovery = new ConsulServiceDiscovery(c);*/


        //StaticServiceDiscovery servers = new StaticServiceDiscovery();
        //servers.addServer("localhost:8093");

        /*RibbonConfiguration configuration = new RibbonConfiguration();
        configuration.addProperty("ribbon.eureka.enabled", "true");
        RibbonServiceLoadBalancer loadBalancer = new RibbonServiceLoadBalancer(configuration);*/

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
