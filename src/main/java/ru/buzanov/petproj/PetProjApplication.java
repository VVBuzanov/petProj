package ru.buzanov.petproj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class PetProjApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetProjApplication.class, args);
    }

}
