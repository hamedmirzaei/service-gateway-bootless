package ir.navaco.core.gateway.eureka.model;

import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;

public class DataCenterTypeInfoResolver extends ClassNameIdResolver {

    /**
     * This phantom class name is kept for backwards compatibility. Internally it is mapped to
     * {@link MyDataCenterInfo} during the deserialization process.
     */
    public static final String MY_DATA_CENTER_INFO_TYPE_MARKER = "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo";

    public DataCenterTypeInfoResolver() {
        super(TypeFactory.defaultInstance().constructType(DataCenterInfo.class), TypeFactory.defaultInstance());
    }

    @Override
    public JavaType typeFromId(DatabindContext context, String id) throws IOException {
        if (MY_DATA_CENTER_INFO_TYPE_MARKER.equals(id)) {
            return context.getTypeFactory().constructType(MyDataCenterInfo.class);
        }
        return super.typeFromId(context, id);
    }

    @Override
    public String idFromValue(Object value) {
        return MY_DATA_CENTER_INFO_TYPE_MARKER;
    }
}
