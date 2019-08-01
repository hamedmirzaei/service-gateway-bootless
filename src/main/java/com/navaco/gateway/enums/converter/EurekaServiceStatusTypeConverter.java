package com.navaco.gateway.enums.converter;


import com.navaco.gateway.enums.EurekaServiceStatusType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class EurekaServiceStatusTypeConverter implements AttributeConverter<EurekaServiceStatusType, String> {

    @Override
    public String convertToDatabaseColumn(EurekaServiceStatusType serviceStatusEnum) {
        if (serviceStatusEnum == null) {
            return null;
        }
        return serviceStatusEnum.getStatusName();
    }

    @Override
    public EurekaServiceStatusType convertToEntityAttribute(String statusName) {
        if (statusName == null) {
            return null;
        }

        return Stream.of(EurekaServiceStatusType.values())
                .filter(s -> s.getStatusName().equals(statusName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
