package com.navaco.gateway.repository;

import com.navaco.gateway.entity.ContextPathEurekaServiceMappingEntity;
import com.navaco.gateway.entity.EurekaServiceStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContextPathEurekaServiceMappingRepository extends JpaRepository<ContextPathEurekaServiceMappingEntity, Long> {
    public List<ContextPathEurekaServiceMappingEntity> findByEurekaServiceStatusEntity(EurekaServiceStatusEntity eurekaServiceStatusEntity);
}
