package ir.navaco.core.gateway.eureka;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.navaco.core.gateway.eureka.model.Application;
import ir.navaco.core.gateway.eureka.model.InstanceInfo;
import org.apache.camel.cloud.ServiceDefinition;
import org.apache.camel.impl.cloud.DefaultServiceDefinition;
import org.apache.camel.impl.cloud.DefaultServiceDiscovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EurekaServiceDiscovery extends DefaultServiceDiscovery {

    private List<ServiceDefinition> services = new ArrayList<>();

    //TODO read from config file
    private String serverUrl = "http://localhost:8090/eureka/";

    @Autowired
    Environment environment;

    @Override
    public List<ServiceDefinition> getServices(String name) {

        List<ServiceDefinition> serviceDefinitions = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Stream.of(MediaType.APPLICATION_JSON).collect(Collectors.toList()));
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                String.format("%sapps/%s", serverUrl, name),
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<String>() {
                });

        ObjectMapper mapper = new ObjectMapper();
        try {
            Application application = mapper.convertValue(mapper.readTree(response.getBody()).with("application"), Application.class);
            for (InstanceInfo instanceInfo : application.getInstances()) {
                if (instanceInfo.getStatus() == InstanceInfo.InstanceStatus.UP)
                    services.add(DefaultServiceDefinition
                            .builder()
                            .withName(instanceInfo.getAppName())
                            .withHost(instanceInfo.getIpAddr())
                            .withPort(instanceInfo.getPort().getValue())
                            .build());
            }
        } catch (IOException e) {
            return Collections.EMPTY_LIST;
        }

        return services;
    }
}
