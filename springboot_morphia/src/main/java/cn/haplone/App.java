package cn.haplone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
public class App {

    private static Logger LOG = LoggerFactory.getLogger(App.class);
    private static ConfigurableApplicationContext ctx;
    public static void main( String[] args ) {
        ctx = SpringApplication.run(App.class,args);
        System.out.println( "Hello World!" );
    }

    public void destroy() throws Exception{
        if( ctx!=null && ctx.isRunning() ){
            ctx.close();
        }
    }
}
