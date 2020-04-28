package org.niewidoczniakademicy.rezerwacje.core.model.database;

import com.univocity.parsers.annotations.Parsed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Data
@Entity
@Table
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

    @NonNull
    private ZonedDateTime additionTime;
}
