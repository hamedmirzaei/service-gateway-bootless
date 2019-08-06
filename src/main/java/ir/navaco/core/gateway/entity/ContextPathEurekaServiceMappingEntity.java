package ir.navaco.core.gateway.entity;

import ir.navaco.core.gateway.enums.Schema;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = ContextPathEurekaServiceMappingEntity.CONTEXT_SERVICE_MAPPING_TABLE_NAME, schema = Schema.IF)
public class ContextPathEurekaServiceMappingEntity implements Serializable {

    public static final String CONTEXT_SERVICE_MAPPING_TABLE_NAME = "CPES_MAPPING";//"CONTEXT_PATH_EUREKA_SERVICE_MAPPING";
    public static final String CONTEXT_SERVICE_MAPPING_SEQUENCE_NAME = CONTEXT_SERVICE_MAPPING_TABLE_NAME + "_SEQ";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ctx_svc_generator")
    @SequenceGenerator(name = "ctx_svc_generator", sequenceName = ContextPathEurekaServiceMappingEntity.CONTEXT_SERVICE_MAPPING_SEQUENCE_NAME, schema = Schema.IF)
    private Long id;

    @Column(name = "CONTEXT_PATH", unique = true, nullable = false)
    private String contextPath;

    @Column(name = "EUREKA_SERVICE_NAME", nullable = false)
    private String eurekaServiceName;

    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EUREKA_SERVICE_STATUS_ID", referencedColumnName = "ID")
    private EurekaServiceStatusEntity eurekaServiceStatusEntity;

    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SUB_SYSTEM_CATEGORY_ID", referencedColumnName = "ID")
    private SubSystemCategoryEntity subSystemCategoryEntity;

    @Column(nullable = false, updatable = false, name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false, name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public ContextPathEurekaServiceMappingEntity() {
    }

    public ContextPathEurekaServiceMappingEntity(String contextPath, String eurekaServiceName) {
        this.contextPath = contextPath;
        this.eurekaServiceName = eurekaServiceName;
    }

    public ContextPathEurekaServiceMappingEntity(Long id, String contextPath, String eurekaServiceName) {
        this.id = id;
        this.contextPath = contextPath;
        this.eurekaServiceName = eurekaServiceName;
    }

    public ContextPathEurekaServiceMappingEntity(String contextPath, String eurekaServiceName, EurekaServiceStatusEntity eurekaServiceStatusEntity, SubSystemCategoryEntity subSystemCategoryEntity) {
        this.contextPath = contextPath;
        this.eurekaServiceName = eurekaServiceName;
        this.eurekaServiceStatusEntity = eurekaServiceStatusEntity;
        this.subSystemCategoryEntity = subSystemCategoryEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getEurekaServiceName() {
        return eurekaServiceName;
    }

    public void setEurekaServiceName(String eurekaServiceName) {
        this.eurekaServiceName = eurekaServiceName;
    }

    public EurekaServiceStatusEntity getEurekaServiceStatusEntity() {
        return eurekaServiceStatusEntity;
    }

    public void setEurekaServiceStatusEntity(EurekaServiceStatusEntity eurekaServiceStatusEntity) {
        this.eurekaServiceStatusEntity = eurekaServiceStatusEntity;
    }

    public SubSystemCategoryEntity getSubSystemCategoryEntity() {
        return subSystemCategoryEntity;
    }

    public void setSubSystemCategoryEntity(SubSystemCategoryEntity subSystemCategoryEntity) {
        this.subSystemCategoryEntity = subSystemCategoryEntity;
    }

    @Override
    public String toString() {
        return "ContextPathEurekaServiceMappingEntity{" +
                "id=" + id +
                ", contextPath='" + contextPath + '\'' +
                ", eurekaServiceName='" + eurekaServiceName + '\'' +
                ", eurekaServiceStatusEntity=" + eurekaServiceStatusEntity +
                ", subSystemCategoryEntity=" + subSystemCategoryEntity +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
