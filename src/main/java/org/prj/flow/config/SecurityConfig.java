package org.prj.flow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security의 기본 보안 정책을 설정하는 클래스입니다.
 *
 * <p>현재는 JWT 구현 전 단계이므로 Swagger와 API 요청을 열어두고,
 * 이후 JWT 필터와 역할별 접근 제어를 추가할 예정입니다.</p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /**
   * HTTP 보안 필터 체인을 구성합니다.
   *
   * @param http Spring Security HTTP 설정 객체
   * @return 애플리케이션에 적용할 보안 필터 체인
   * @throws Exception 보안 설정 생성 중 오류가 발생한 경우
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**",
                "/actuator/health"
            ).permitAll()
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .anyRequest().permitAll()
        )
        .build();
  }

  /**
   * 사용자 비밀번호를 BCrypt 방식으로 암호화하기 위한 인코더입니다.
   *
   * @return BCrypt 기반 PasswordEncoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
