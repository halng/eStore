package com.e.store.auth.entity;

import jakarta.persistence.Column;
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
	private Instant updateDate;

	@Column(name = "create_by")
	private String createBy;

	@Column(name = "update_by")
	private String updateBy;

}
