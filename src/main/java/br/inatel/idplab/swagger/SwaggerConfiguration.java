package br.inatel.idplab.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mysql.cj.protocol.Message;

import br.inatel.idplab.notification.Notification;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

	private static final String BASE_PACKAGE = "br.inatel.idplab";
	private static final String API_TITLE = "Quotation Stock Management API";
	private static final String API_DESCRIPTION = "REST API for control stocks e quotes of registered stocks in an eternal API";
	private static final String API_VERSION = "1.0.0";
	private static final String CONTACT_NAME = "Rodrigo Paroli";
	private static final String CONTACT_GITHUB = "https://github.com/roparoli";
	private static final String CONTACT_EMAIL = "rparoli@hotmai.com";
	
	@Bean
	public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.ant("/**"))
                .build()
                .ignoredParameterTypes(Message.class, Notification.class)
                .apiInfo(buildApiInfo());
    }
	
	private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title(API_TITLE)
                .description(API_DESCRIPTION)
                .version(API_VERSION)
                .contact(new Contact(CONTACT_NAME, CONTACT_GITHUB, CONTACT_EMAIL))
                .build();
    }
	
	
}
