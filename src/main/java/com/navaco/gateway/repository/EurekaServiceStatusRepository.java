package com.navaco.gateway.repository;

import com.navaco.gateway.entity.EurekaServiceStatusEntity;
import com.navaco.gateway.enums.EurekaServiceStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EurekaServiceStatusRepository extends JpaRepository<EurekaServiceStatusEntity, Long> {
    public EurekaServiceStatusEntity findByEurekaServiceStatusType(EurekaServiceStatusType eurekaServiceStatusType);
}
