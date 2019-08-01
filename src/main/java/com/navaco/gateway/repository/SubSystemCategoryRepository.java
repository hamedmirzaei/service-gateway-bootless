package com.navaco.gateway.repository;

import com.navaco.gateway.entity.SubSystemCategoryEntity;
import com.navaco.gateway.enums.SubSystemCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubSystemCategoryRepository extends JpaRepository<SubSystemCategoryEntity, Long> {
    public SubSystemCategoryEntity findBySubSystemCategoryType(SubSystemCategoryType subSystemCategoryType);
}
