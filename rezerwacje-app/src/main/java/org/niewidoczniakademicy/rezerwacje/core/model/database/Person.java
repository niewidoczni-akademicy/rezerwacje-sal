package org.niewidoczniakademicy.rezerwacje.core.model.database;

import com.univocity.parsers.annotations.Parsed;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Parsed(field = "first name")
    @NonNull
    private String firstName;

    @Parsed(field = "last name")
    @NonNull
    private String lastName;

    @Parsed(field = "e-mail")
    @NonNull
    private String emailAddress;

    @Parsed(field = "phone number")
    @NonNull
    private String phoneNumber;

    //@NonNull
    private ZonedDateTime additionTime;
}
