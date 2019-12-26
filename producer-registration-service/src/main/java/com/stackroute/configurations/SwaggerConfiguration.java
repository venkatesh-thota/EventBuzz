package com.stackroute.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;


//                                       Swagger Configuration file
@Configuration
@EnableSwagger2
public class SwaggerConfiguration  extends WebMvcConfigurationSupport {

    @Value("${producer-registration-configuration.message.swaggerconfigclass}")
    private String swaggerConfigClass;
    @Value("${producer-registration-configuration.message.swaggerconfigpath}")
    private String SwaggerConfigPath;
    @Value("${producer-registration-configuration.message.swaggerconfiguri}")
    private String swaggerConfigUri;
    @Value("${producer-registration-configuration.message.swaggerconfigclasspath}")
    private String swaggerConfigClassPath;
    @Value("${producer-registration-configuration.message.swaggerconfigpath1}")
    private String swaggerConfigPath1;
    @Value("${producer-registration-configuration.message.swaggerconfigpath2}")
    private String swaggerConfigPath2;

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()                 .apis(RequestHandlerSelectors.basePackage(swaggerConfigClass))
                .paths(regex(SwaggerConfigPath))
                .build();

    }
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(swaggerConfigUri)
                .addResourceLocations(swaggerConfigClassPath);

        registry.addResourceHandler(swaggerConfigPath1)
                .addResourceLocations(swaggerConfigPath2);
    }
}