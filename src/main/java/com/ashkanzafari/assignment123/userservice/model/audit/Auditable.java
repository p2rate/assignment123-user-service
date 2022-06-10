package com.ashkanzafari.assignment123.userservice.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

/**
 * Auditable.
 *
 * <p>Extend this class in your model for classes that are going to be
 *  persisted and require Created Date and Last Modified Date information
 *  and other auditable fields.</p>
 */
@Data
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable{

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @JsonIgnore
  @Column(name = "created_date", nullable = false, updatable = false)
  @CreatedDate
  private LocalDateTime createdDate;

  @JsonIgnore
  @Column(name = "modified_date")
  @LastModifiedDate
  private LocalDateTime modifiedDate;

  @JsonIgnore
  @Column(name = "created_by")
  @CreatedBy
  private String createdBy;

  @JsonIgnore
  @Column(name = "modified_by")
  @LastModifiedBy
  private String modifiedBy;

  @JsonIgnore
  @Column(name = "deleted", nullable = false, columnDefinition = "boolean default false")
  private boolean deleted;

  @Version
  Long version;


}
