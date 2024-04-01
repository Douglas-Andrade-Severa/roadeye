package buildrun.roadeye;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Roadeye Api", version = "1", description = "API"))
@SecurityScheme(name = "roadeyeApi", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class RoadeyeApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(RoadeyeApplication.class, args);
	}
}