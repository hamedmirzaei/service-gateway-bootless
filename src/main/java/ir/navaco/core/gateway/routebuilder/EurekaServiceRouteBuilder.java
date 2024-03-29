package ir.navaco.core.gateway.routebuilder;

import ir.navaco.core.gateway.entity.ContextPathEurekaServiceMappingEntity;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpMethods;

public class EurekaServiceRouteBuilder extends RouteBuilder {

    private ContextPathEurekaServiceMappingEntity contextPathEurekaServiceMappingEntity;

    public EurekaServiceRouteBuilder(ContextPathEurekaServiceMappingEntity contextPathEurekaServiceMappingEntity) {
        this.contextPathEurekaServiceMappingEntity = contextPathEurekaServiceMappingEntity;
    }

    @Override
    public void configure() throws Exception {

        // redirect Http.GET requests to appropriate micro-service
        from("spark-rest:get:services/" + contextPathEurekaServiceMappingEntity.getContextPath())
                .routeId("get_" + contextPathEurekaServiceMappingEntity.getContextPath())
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
                .setHeader("in_uri", simple("${header[CamelHttpUri].replace('/services', '')}"))
                .removeHeader("CamelHttp*")
                .removeHeader(Exchange.HTTP_PATH)
                .removeHeader(Exchange.HTTP_URI)
                .serviceCall(contextPathEurekaServiceMappingEntity.getEurekaServiceName() + "/" + "${header[in_uri]}");

        // redirect Http.POST requests to appropriate micro-service
        from("spark-rest:post:services/" + contextPathEurekaServiceMappingEntity.getContextPath())
                .routeId("post_" + contextPathEurekaServiceMappingEntity.getContextPath())
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST))
                .setHeader("in_uri", simple("${header[CamelHttpUri].replace('/services', '')}"))
                .removeHeader("CamelHttp*")
                .removeHeader(Exchange.HTTP_PATH)
                .removeHeader(Exchange.HTTP_URI)
                .serviceCall(contextPathEurekaServiceMappingEntity.getEurekaServiceName() + "/" + "${header[in_uri]}");

        // redirect Http.DELETE requests to appropriate micro-service
        from("spark-rest:delete:services/" + contextPathEurekaServiceMappingEntity.getContextPath())
                .routeId("delete_" + contextPathEurekaServiceMappingEntity.getContextPath())
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.DELETE))
                .setHeader("in_uri", simple("${header[CamelHttpUri].replace('/services', '')}"))
                .removeHeader("CamelHttp*")
                .removeHeader(Exchange.HTTP_PATH)
                .removeHeader(Exchange.HTTP_URI)
                .serviceCall(contextPathEurekaServiceMappingEntity.getEurekaServiceName() + "/" + "${header[in_uri]}");

        // redirect Http.PUT requests to appropriate micro-service
        from("spark-rest:put:services/" + contextPathEurekaServiceMappingEntity.getContextPath())
                .routeId("put_" + contextPathEurekaServiceMappingEntity.getContextPath())
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.PUT))
                .setHeader("in_uri", simple("${header[CamelHttpUri].replace('/services', '')}"))
                .removeHeader("CamelHttp*")
                .removeHeader(Exchange.HTTP_PATH)
                .removeHeader(Exchange.HTTP_URI)
                .serviceCall(contextPathEurekaServiceMappingEntity.getEurekaServiceName() + "/" + "${header[in_uri]}");

        // redirect Http.PATCH requests to appropriate micro-service
        from("spark-rest:patch:services/" + contextPathEurekaServiceMappingEntity.getContextPath())
                .routeId("patch_" + contextPathEurekaServiceMappingEntity.getContextPath())
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.PATCH))
                .setHeader("in_uri", simple("${header[CamelHttpUri].replace('/services', '')}"))
                .removeHeader("CamelHttp*")
                .removeHeader(Exchange.HTTP_PATH)
                .removeHeader(Exchange.HTTP_URI)
                .serviceCall(contextPathEurekaServiceMappingEntity.getEurekaServiceName() + "/" + "${header[in_uri]}");
    }

}
