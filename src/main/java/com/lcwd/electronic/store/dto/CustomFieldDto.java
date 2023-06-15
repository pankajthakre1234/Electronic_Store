package com.lcwd.electronic.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CustomFieldDto implements Serializable {

    private String isactive;

    @CreatedBy
    public String createdBy;

    @CreationTimestamp
    public LocalDateTime createdOn;

    @LastModifiedBy
    public String updatedBy;

    @UpdateTimestamp
    public LocalDateTime updatedOn;
}
