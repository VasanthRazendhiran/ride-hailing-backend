package com.devavasanth.ridehailing.userservice.entity.audit;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class AuditTableEntity {

	@CreatedDate
	private LocalDateTime created_at;

	@LastModifiedDate
	private LocalDateTime updated_at;

	@CreatedBy
	private String created_by;

	@LastModifiedBy
	private String update_by;
}
