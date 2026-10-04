package org.prj.flow.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI 문서 기본 정보를 설정하는 클래스입니다.
 */
@Configuration
public class OpenApiConfig {

  /**
   * Swagger UI에 표시할 API 문서 정보를 생성합니다.
   *
   * @return OpenAPI 문서 설정 객체
   */
  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("WorkFlow API")
            .description("기업 근태·전자결재 관리 시스템 API")
            .version("v1"));
  }
}
