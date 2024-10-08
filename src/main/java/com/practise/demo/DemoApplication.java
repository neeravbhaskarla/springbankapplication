package com.practise.demo;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImp")
@OpenAPIDefinition(
    info = @Info(
        title = "Accounts microservice REST API Documentation",
        description = "EazyBank Accounts microservice REST API Documentation",
        version = "v1",
        contact = @Contact(
            name = "Neerav Bhaskarla",
            email = "ananthaneerav@gmail.com",
            url = "https://www.neeravbhaskarla.site"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.neeravbhaskarla.site"
        )
    ),
    externalDocs = @ExternalDocumentation(
        description = "External Information can be contacted by neerav",
        url = "https://www.neeravbhaskarla.site"
    )
)
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
