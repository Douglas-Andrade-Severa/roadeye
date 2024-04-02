package buildrun.roadeye.rest.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("Bearer")
                                        .in(SecurityScheme.In.HEADER)
                                        .bearerFormat("JWT")
                        )
                )
                .info(new Info()
                        .title("Roadeye API")
                        .version("1")
                        .description("Project related to the semester conclusion work of the system analysis and development course at Senac Brusque-SC college")
                        .contact(new Contact()
                                .name("Douglas Andrade")
                                .email("douglas.severa96@gmail.com")
                                .url("https://github.com/Douglas-Andrade-Severa/roadeye.git")
                        )
                );
    }
}
