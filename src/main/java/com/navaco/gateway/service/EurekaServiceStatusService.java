package com.navaco.gateway.service;

import com.navaco.gateway.entity.EurekaServiceStatusEntity;
import com.navaco.gateway.enums.EurekaServiceStatusType;

public interface EurekaServiceStatusService {

    EurekaServiceStatusEntity getEurekaServiceStatusByEurekaServiceStatusType(EurekaServiceStatusType eurekaServiceStatusType);

}
