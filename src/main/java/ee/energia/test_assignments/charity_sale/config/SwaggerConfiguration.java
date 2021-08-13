package ee.energia.test_assignments.charity_sale.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
@ConfigurationProperties("app.api")
@ConditionalOnProperty(name="app.api.swagger.enable", havingValue = "true")
public class SwaggerConfiguration {
    private ApiInfo apiInfo() {
        return new ApiInfo("Eesti Energia homework app",
                "Charity sale app",
                "0.1",
                "",
                new Contact("Pavel Tsokarev", "", "p.tsykarev@gmail.com"),
                "",
                "",
                new ArrayList<>());
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("ee.energia.test_assignments.charity_sale"))
                .paths(PathSelectors.any())
                .build();
    }
}
