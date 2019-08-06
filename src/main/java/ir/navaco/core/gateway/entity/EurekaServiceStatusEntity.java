package ir.navaco.core.gateway.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ir.navaco.core.gateway.enums.EurekaServiceStatusType;
import ir.navaco.core.gateway.enums.Schema;
import ir.navaco.core.gateway.enums.converter.EurekaServiceStatusTypeConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = EurekaServiceStatusEntity.EUREKA_SERVICE_STATUS_TABLE_NAME, schema = Schema.IF)
public class EurekaServiceStatusEntity {

    public static final String EUREKA_SERVICE_STATUS_TABLE_NAME = "EUREKA_SERVICE_STATUS";
    public static final String EUREKA_SERVICE_STATUS_SEQUENCE_NAME = EUREKA_SERVICE_STATUS_TABLE_NAME + "_SEQ";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stt_generator")
    @SequenceGenerator(name = "stt_generator", sequenceName = EurekaServiceStatusEntity.EUREKA_SERVICE_STATUS_SEQUENCE_NAME, schema = Schema.IF, allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "STATUS_TYPE", unique = true, nullable = false)
    @Convert(converter = EurekaServiceStatusTypeConverter.class)
    private EurekaServiceStatusType eurekaServiceStatusType;

    @Column(name = "STATUS_DESCRIPTION")
    private String description;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "eurekaServiceStatusEntity")
    private List<ContextPathEurekaServiceMappingEntity> contextPathEurekaServiceMappingEntityList;

    public EurekaServiceStatusEntity() {
    }

    public EurekaServiceStatusEntity(EurekaServiceStatusType eurekaServiceStatusType, String description) {
        this.eurekaServiceStatusType = eurekaServiceStatusType;
        this.description = description;
        this.contextPathEurekaServiceMappingEntityList = new ArrayList<>();
    }

    public EurekaServiceStatusEntity(EurekaServiceStatusType eurekaServiceStatusType, String description, List<ContextPathEurekaServiceMappingEntity> contextPathEurekaServiceMappingEntityList) {
        this.eurekaServiceStatusType = eurekaServiceStatusType;
        this.description = description;
        this.contextPathEurekaServiceMappingEntityList = contextPathEurekaServiceMappingEntityList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EurekaServiceStatusType getEurekaServiceStatusType() {
        return eurekaServiceStatusType;
    }

    public void setEurekaServiceStatusType(EurekaServiceStatusType eurekaServiceStatusType) {
        this.eurekaServiceStatusType = eurekaServiceStatusType;
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
        return "EurekaServiceStatusEntity{" +
                "id=" + id +
                ", eurekaServiceStatusType=" + eurekaServiceStatusType +
                ", description='" + description + '\'' +
                '}';
    }
}
