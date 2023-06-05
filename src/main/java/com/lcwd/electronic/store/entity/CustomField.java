package com.lcwd.electronic.store.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CustomField {

    @Column(name = "is_active_switch")
    private String isactive;

    @Column(name = "created_by")
    @CreatedBy
    public String createdBy;

    @Column(name = "created_date",updatable = false)
    @CreationTimestamp
    public LocalDateTime createdOn;

    @Column(name = "updated_by")
    @LastModifiedBy
    public String updatedBy;

    @Column(name = "updated_date",updatable = false)
    @UpdateTimestamp
    public LocalDateTime updatedOn;
}
