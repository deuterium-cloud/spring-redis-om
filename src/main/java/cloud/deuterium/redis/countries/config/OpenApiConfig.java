package cloud.deuterium.redis.countries.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

/**
 * Created by MilanNuke 25-Jun-20
 */


@OpenAPIDefinition(info = @Info(title = "Countries Test App",
                                version = "0.9",
                                contact = @Contact(name = "Countries API Support",
                                                   url = "https://deuterium.cloud/",
                                                   email = "milan@svrljig.net"),
                                license = @License(name = "Apache 2.0",
                                                   url = "http://www.apache.org/licenses/LICENSE-2.0.html")))
/**
 *      http://localhost:8080/v3/api-docs
 *      http://localhost:8080/swagger-ui/index.html
 */
@Configuration
public class OpenApiConfig {
}
