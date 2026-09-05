package com.devavasanth.ridehailing.userservice.audit;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
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
	
	private String created_by;
	
	private String update_by;
}
