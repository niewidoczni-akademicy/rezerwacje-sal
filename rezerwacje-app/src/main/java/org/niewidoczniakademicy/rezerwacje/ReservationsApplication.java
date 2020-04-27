package org.niewidoczniakademicy.rezerwacje;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ReservationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationsApplication.class, args);
    }

    @GetMapping
    public String hello() {
        return "Backend";
    }
}
