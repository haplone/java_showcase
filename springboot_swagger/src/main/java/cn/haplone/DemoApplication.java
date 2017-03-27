package cn.haplone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by z on 17-3-27.
 * http://springfox.github.io/springfox/docs/current/
 */
@Configuration
@EnableSwagger2
@SpringBootApplication
public class DemoApplication {

        public static void main(String[] args) {
                SpringApplication.run(DemoApplication.class, args);
        }

        @Bean
        public Docket userApi() {
                return new Docket(DocumentationType.SWAGGER_2)
                        .apiInfo(apiInfo())
                        .select()
                        .apis(RequestHandlerSelectors.basePackage("cn.haplone"))
                        .paths(PathSelectors.any())
                        .build();
        }

        private ApiInfo apiInfo() {
                return new ApiInfoBuilder()
                        .title("图书api")
                        .description("图书测试")
                        .termsOfServiceUrl("")
                        .license("Apache License Version 2.0")
                        .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
                        .version("2.0")
                        .build();
        }


}