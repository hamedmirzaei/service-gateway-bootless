package com.navaco.gateway.service;

import com.navaco.gateway.entity.SubSystemCategoryEntity;
import com.navaco.gateway.enums.SubSystemCategoryType;

public interface SubSystemCategoryService {

    SubSystemCategoryEntity getSubSystemCategoryBySubSystemCategoryType(SubSystemCategoryType subSystemCategoryType);

}
