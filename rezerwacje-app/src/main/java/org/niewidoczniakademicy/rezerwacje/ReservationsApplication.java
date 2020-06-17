package org.niewidoczniakademicy.rezerwacje;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ReservationsApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ReservationsApplication.class, args);
    }

    @Secured({"ROLE_ANON", "ROLE_STANDARD", "ROLE_SUPERVISOR", "ROLE_ADMINISTRATOR"})
    @GetMapping
    public final String hello() {
        return "Backend";
    }
}
