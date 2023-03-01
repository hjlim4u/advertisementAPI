//package com.example.assignment1;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//
//@Configuration  //스프링 실행시 설정파일 읽어드리기 위한 어노테이션
//@EnableSwagger2
//public class SwaggerConfig {
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.ant("/*/**"))
//                .build();
//    }
//
//    public ApiInfo apiInfo(){
//        return new ApiInfoBuilder()
//                .title("게시글 API")
//                .version("3.4")
//                .description("추삭수가 가능한 게시글 api 입니다잇!!")
//                .build();
//    }
//}