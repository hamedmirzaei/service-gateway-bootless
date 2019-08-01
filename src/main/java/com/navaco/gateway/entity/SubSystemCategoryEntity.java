package com.navaco.gateway.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.navaco.gateway.enums.SubSystemCategoryType;
import com.navaco.gateway.enums.converter.SubSystemCategoryTypeConverter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = SubSystemCategoryEntity.SUB_SYSTEM_CATEGORY_TABLE_NAME, schema = "USRPRF")
public class SubSystemCategoryEntity {

    public static final String SUB_SYSTEM_CATEGORY_TABLE_NAME = "SUB_SYSTEM_CATEGORY";
    public static final String SUB_SYSTEM_CATEGORY_SEQUENCE_NAME = SUB_SYSTEM_CATEGORY_TABLE_NAME + "_SEQ";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cat_generator")
    @SequenceGenerator(name = "cat_generator", sequenceName = SubSystemCategoryEntity.SUB_SYSTEM_CATEGORY_SEQUENCE_NAME, schema = "USRPRF")
    private Long id;

    @Column(name = "CATEGORY_TYPE", unique = true, nullable = false)
    @Convert(converter = SubSystemCategoryTypeConverter.class)
    private SubSystemCategoryType subSystemCategoryType;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subSystemCategoryEntity")
    private List<ContextPathEurekaServiceMappingEntity> contextPathEurekaServiceMappingEntityList;

    public SubSystemCategoryEntity() {
    }

    public SubSystemCategoryEntity(SubSystemCategoryType subSystemCategoryType) {
        this.subSystemCategoryType = subSystemCategoryType;
    }

    public SubSystemCategoryEntity(SubSystemCategoryType subSystemCategoryType, List<ContextPathEurekaServiceMappingEntity> contextPathEurekaServiceMappingEntityList) {
        this.subSystemCategoryType = subSystemCategoryType;
        this.contextPathEurekaServiceMappingEntityList = contextPathEurekaServiceMappingEntityList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubSystemCategoryType getSubSystemCategoryType() {
        return subSystemCategoryType;
    }

    public void setSubSystemCategoryType(SubSystemCategoryType subSystemCategoryType) {
        this.subSystemCategoryType = subSystemCategoryType;
    }

    public List<ContextPathEurekaServiceMappingEntity> getContextPathEurekaServiceMappingEntityList() {
        return contextPathEurekaServiceMappingEntityList;
    }

    public void setContextPathEurekaServiceMappingEntityList(List<ContextPathEurekaServiceMappingEntity> contextPathEurekaServiceMappingEntityList) {
        this.contextPathEurekaServiceMappingEntityList = contextPathEurekaServiceMappingEntityList;
    }

    @Override
    public String toString() {
        return "SubSystemCategoryType{" +
                "id=" + id +
                ", subSystemCategoryType='" + subSystemCategoryType + '\'' +
                '}';
    }

}
