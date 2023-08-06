package com.e.store.product.entity;

import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
@Getter
@Setter
public class AuditEntity {
    @CreationTimestamp
    private Instant createDate;
    @UpdateTimestamp
    private Instant lastUpdate;
    private String createBy;
    private String lastUpdateBy;

}
