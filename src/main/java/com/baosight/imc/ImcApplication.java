package com.baosight.imc;

import com.baosight.iplat4j.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication(scanBasePackages = "com.baosight")
@ServletComponentScan("com.baosight.iplat4j.core.web.servlet")
@ImportResource(locations = {"classpath*:spring/framework/platApplicationContext*.xml","classpath*:spring/framework/applicationContext*.xml"})
@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
@EnableWebSocket
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class ImcApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ImcApplication.class);
        app.run(args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ImcApplication.class);
    }

}
