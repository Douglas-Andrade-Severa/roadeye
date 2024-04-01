package buildrun.roadeye.config;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi customApi() {
        return GroupedOpenApi.builder()
                .group("custom-api")
                .packagesToScan("buildrun.roadeye.rest.controller")
                .build();
    }
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springdoc-openapi-ui/")
                .resourceChain(false);
    }
}
