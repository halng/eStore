package com.e.store.product.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class AuditEntity {
    @CreationTimestamp
    private Instant createDate;
    @UpdateTimestamp
    private Instant lastUpdate;
    @CreatedBy
    private String createBy;
    @LastModifiedBy
    private String lastUpdateBy;

}
