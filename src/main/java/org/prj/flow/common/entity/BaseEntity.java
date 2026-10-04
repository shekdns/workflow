package org.prj.flow.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 모든 JPA Entity가 공통으로 상속받는 감사 컬럼 기본 클래스입니다.
 *
 * <p>DB 공통 컬럼 규칙인 CREATED_ID, CREATED_DATE, UPDATED_ID, UPDATED_DATE를
 * 한 곳에서 관리하기 위해 사용합니다.</p>
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

  /** 데이터를 최초 생성한 사용자 ID를 저장합니다. */
  @CreatedBy
  @Column(name = "CREATED_ID", updatable = false)
  private Long createdId;

  /** 데이터가 최초 생성된 일시를 저장합니다. */
  @CreatedDate
  @Column(name = "CREATED_DATE", nullable = false, updatable = false)
  private LocalDateTime createdDate;

  /** 데이터를 마지막으로 수정한 사용자 ID를 저장합니다. */
  @LastModifiedBy
  @Column(name = "UPDATED_ID")
  private Long updatedId;

  /** 데이터가 마지막으로 수정된 일시를 저장합니다. */
  @LastModifiedDate
  @Column(name = "UPDATED_DATE")
  private LocalDateTime updatedDate;
}
