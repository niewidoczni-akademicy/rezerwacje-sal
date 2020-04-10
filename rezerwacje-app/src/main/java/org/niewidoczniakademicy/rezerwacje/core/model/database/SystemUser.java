package org.niewidoczniakademicy.rezerwacje.core.model.database;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String emailAddress;

    @NonNull
    private String phoneNumber;

    @NonNull
    private ZonedDateTime additionTime;
}
