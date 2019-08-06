package ir.navaco.core.gateway.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ir.navaco.core.gateway.enums.Schema;
import ir.navaco.core.gateway.enums.SubSystemCategoryType;
import ir.navaco.core.gateway.enums.converter.SubSystemCategoryTypeConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = SubSystemCategoryEntity.SUB_SYSTEM_CATEGORY_TABLE_NAME, schema = Schema.IF)
public class SubSystemCategoryEntity {

    public static final String SUB_SYSTEM_CATEGORY_TABLE_NAME = "SUB_SYSTEM_CATEGORY";
    public static final String SUB_SYSTEM_CATEGORY_SEQUENCE_NAME = SUB_SYSTEM_CATEGORY_TABLE_NAME + "_SEQ";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cat_generator")
    @SequenceGenerator(name = "cat_generator", sequenceName = SubSystemCategoryEntity.SUB_SYSTEM_CATEGORY_SEQUENCE_NAME, schema = Schema.IF)
    private Long id;

    @Column(name = "CATEGORY_TYPE", unique = true, nullable = false)
    @Convert(converter = SubSystemCategoryTypeConverter.class)
    private SubSystemCategoryType subSystemCategoryType;

    @Column(name = "CATEGORY_DESCRIPTION")
    private String description;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subSystemCategoryEntity")
    private List<ContextPathEurekaServiceMappingEntity> contextPathEurekaServiceMappingEntityList;

    public SubSystemCategoryEntity() {
    }

    public SubSystemCategoryEntity(SubSystemCategoryType subSystemCategoryType) {
        this.subSystemCategoryType = subSystemCategoryType;
    }

    public SubSystemCategoryEntity(SubSystemCategoryType subSystemCategoryType, String description) {
        this.subSystemCategoryType = subSystemCategoryType;
        this.description = description;
        this.contextPathEurekaServiceMappingEntityList = new ArrayList<>();
    }

    public SubSystemCategoryEntity(SubSystemCategoryType subSystemCategoryType, String description, List<ContextPathEurekaServiceMappingEntity> contextPathEurekaServiceMappingEntityList) {
        this.subSystemCategoryType = subSystemCategoryType;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ContextPathEurekaServiceMappingEntity> getContextPathEurekaServiceMappingEntityList() {
        return contextPathEurekaServiceMappingEntityList;
    }

    public void setContextPathEurekaServiceMappingEntityList(List<ContextPathEurekaServiceMappingEntity> contextPathEurekaServiceMappingEntityList) {
        this.contextPathEurekaServiceMappingEntityList = contextPathEurekaServiceMappingEntityList;
    }

    @Override
    public String toString() {
        return "SubSystemCategoryEntity{" +
                "id=" + id +
                ", subSystemCategoryType=" + subSystemCategoryType +
                ", description='" + description + '\'' +
                '}';
    }
}
