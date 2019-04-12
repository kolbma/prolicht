package eu.n4v.prolicht;

import static springfox.documentation.builders.PathSelectors.regex;
import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Generates and provide /swagger-ui.html
 */
@Configuration
@EnableSwagger2
@Slf4j
public class SwaggerConfig {

    @Bean
    public Docket swaggerApi() {
        log.info("swaggerApi() called");
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("eu.n4v.prolicht")).paths(regex("/.*"))
                .build().apiInfo(cfgApiInfo()).securitySchemes(new ArrayList<SecurityScheme>(1) {
                    private static final long serialVersionUID = 1L;

                    {
                        add(new BasicAuth("basicAuth"));
                    }
                });
    }

    protected ApiInfo cfgApiInfo() {
        ApiInfo apiInfo = new ApiInfo("REST API for PROLICHT", "Sample REST API for PROLICHT",
                "1.0", "Terms of service",
                new Contact("Markus Kolb", "https://prolicht.n4v.eu/",
                        "markus.kolb+prolicht@n4v.eu"),
                "Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<VendorExtension>());
        return apiInfo;
    }
}
