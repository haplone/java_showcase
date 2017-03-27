package cn.haplone;

/**
 * Created by z on 17-3-27.
 */


@Configuration
@EnableSwagger2
@SpringBootApplication
public class DemoApplication {

        public static void main(String[] args) {
                SpringApplication.run(DemoApplication.class, args);
        }

        @Bean
        public Docket testApi() {
                return new Docket(DocumentationType.SWAGGER_2)
                        .groupName("test")
                        .genericModelSubstitutes(DeferredResult.class)
                        //.genericModelSubstitutes(ResponseEntity.class)
                        .useDefaultResponseMessages(false)
                        .forCodeGeneration(true)
                        .pathMapping("/test")//api测试请求地址
                        .select()
                        .paths(PathSelectors.regex("/common/.*"))//过滤的接口
                        .build()
                        .apiInfo(testApiInfo());
        }


        @Bean
        public Docket demoApi() {
                return new Docket(DocumentationType.SWAGGER_2)
                        .groupName("demo")
                        .genericModelSubstitutes(DeferredResult.class)
                        //  .genericModelSubstitutes(ResponseEntity.class)
                        .useDefaultResponseMessages(false)
                        .forCodeGeneration(false)
                        .pathMapping("/")
                        .select()
                        .paths(PathSelectors.regex("/comm.*"))//过滤的接口
                        .build()
                        .apiInfo(demoApiInfo());
        }

        private ApiInfo testApiInfo() {
                Contact contact = new Contact("王念", "http://my.oschina.net/wangnian", "2251181679@qq.com");
                ApiInfo apiInfo = new ApiInfo("某API接口",//大标题
                        "REST风格API",//小标题
                        "0.1",//版本
                        "www.baidu.com",
                        contact,//作者
                        "主页",//链接显示文字
                        ""//网站链接
                );
                return apiInfo;
        }

        private ApiInfo demoApiInfo() {
                Contact contact = new Contact("王念", "http://my.oschina.net/wangnian", "2251181679@qq.com");
                ApiInfo apiInfo = new ApiInfo("某API接口",//大标题
                        "REST风格API",//小标题
                        "0.1",//版本
                        "www.baidu.com",
                        contact,//作者
                        "主页",//链接显示文字
                        ""//网站链接
                );
                return apiInfo;
        }
}