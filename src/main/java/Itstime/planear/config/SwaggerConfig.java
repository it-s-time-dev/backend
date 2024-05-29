package Itstime.planear.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "플래니어 API 명세서",
        description = "백엔드 API")
)
@Configuration
public class SwaggerConfig {
}
