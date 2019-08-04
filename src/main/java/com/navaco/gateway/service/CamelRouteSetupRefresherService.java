package com.navaco.gateway.service;

import org.apache.camel.CamelContext;

public interface CamelRouteSetupRefresherService {

    void setup(CamelContext camelContext);

    void refresh();
}
