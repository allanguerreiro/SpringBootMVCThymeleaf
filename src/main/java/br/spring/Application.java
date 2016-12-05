package br.spring;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by allan on 18/11/16.
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@EnableJpaRepositories(basePackages = "br.spring.persistence")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
