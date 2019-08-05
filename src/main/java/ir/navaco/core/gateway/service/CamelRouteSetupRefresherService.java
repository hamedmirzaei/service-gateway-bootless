package ir.navaco.core.gateway.service;

import org.apache.camel.CamelContext;

public interface CamelRouteSetupRefresherService {

    void setup(CamelContext camelContext);

    void refresh();
}
