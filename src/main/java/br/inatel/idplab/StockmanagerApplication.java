package br.inatel.idplab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching
@EnableSwagger2
@EnableSpringDataWebSupport
public class StockmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockmanagerApplication.class, args);
	}

}
