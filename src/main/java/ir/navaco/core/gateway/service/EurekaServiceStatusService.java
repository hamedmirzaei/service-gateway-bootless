package ir.navaco.core.gateway.service;

import ir.navaco.core.gateway.entity.EurekaServiceStatusEntity;
import ir.navaco.core.gateway.enums.EurekaServiceStatusType;

public interface EurekaServiceStatusService {

    EurekaServiceStatusEntity getEurekaServiceStatusByEurekaServiceStatusType(EurekaServiceStatusType eurekaServiceStatusType);

}
