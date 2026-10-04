package org.prj.flow.config;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * JPA 감사 기능을 활성화하고 생성자/수정자 값을 채우는 설정 클래스입니다.
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {

  /**
   * 현재 인증된 사용자 ID를 JPA 감사 컬럼에 넣기 위한 제공자입니다.
   *
   * <p>JWT 적용 전이나 시스템 작업처럼 인증 정보가 없으면 0을 사용합니다.</p>
   *
   * @return 현재 사용자 ID를 제공하는 AuditorAware
   */
  @Bean
  public AuditorAware<Long> auditorAware() {
    return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
        .filter(Authentication::isAuthenticated)
        .map(Authentication::getName)
        .filter(this::isNumeric)
        .map(Long::valueOf)
        .or(() -> Optional.of(0L));
  }

  /**
   * 인증 객체의 이름이 숫자 ID 형태인지 확인합니다.
   *
   * @param value 인증 객체에서 꺼낸 사용자 식별자 문자열
   * @return 숫자만 포함하면 true
   */
  private boolean isNumeric(String value) {
    return value != null && value.chars().allMatch(Character::isDigit);
  }
}
