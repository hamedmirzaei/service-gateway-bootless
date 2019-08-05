package ir.navaco.core.gateway.api;

import ir.navaco.core.gateway.service.CamelRouteSetupRefresherService;
import ir.navaco.core.gateway.service.ContextPathEurekaServiceMappingService;
import ir.navaco.core.gateway.service.EurekaServiceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/camel")
public class CamelController {

    private ContextPathEurekaServiceMappingService contextPathEurekaServiceMappingService;
    private EurekaServiceStatusService eurekaServiceStatusService;
    private CamelRouteSetupRefresherService camelRouteSetupRefresherService;

    @Autowired
    public CamelController(ContextPathEurekaServiceMappingService contextPathEurekaServiceMappingService,
                           EurekaServiceStatusService eurekaServiceStatusService,
                           CamelRouteSetupRefresherService camelRouteSetupRefresherService) {
        this.contextPathEurekaServiceMappingService = contextPathEurekaServiceMappingService;
        this.eurekaServiceStatusService = eurekaServiceStatusService;
        this.camelRouteSetupRefresherService = camelRouteSetupRefresherService;
    }

    @GetMapping("/refresh")
    public ResponseEntity refreshRoutes() {
        camelRouteSetupRefresherService.refresh();
        return ResponseEntity.ok("CamelContext Refreshed Successfully");
    }

    @GetMapping("/health")
    public ResponseEntity healthCheck() {
        return ResponseEntity.ok("Server is UP");
    }
}
