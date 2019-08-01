package com.navaco.gateway.service;

import com.navaco.gateway.entity.SubSystemCategoryEntity;
import com.navaco.gateway.enums.SubSystemCategoryType;
import com.navaco.gateway.repository.SubSystemCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubSystemCategoryService {

    private SubSystemCategoryRepository subSystemCategoryRepository;

    @Autowired
    public SubSystemCategoryService(SubSystemCategoryRepository subSystemCategoryRepository) {
        this.subSystemCategoryRepository = subSystemCategoryRepository;
    }

    @Transactional
    public SubSystemCategoryEntity getSubSystemCategoryBySubSystemCategoryType(SubSystemCategoryType subSystemCategoryType) {
        return subSystemCategoryRepository.findBySubSystemCategoryType(subSystemCategoryType);
    }
}
