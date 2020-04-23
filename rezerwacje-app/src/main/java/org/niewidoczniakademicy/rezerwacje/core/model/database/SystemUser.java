package org.niewidoczniakademicy.rezerwacje.core.model.database;

import com.univocity.parsers.annotations.Parsed;
import lombok.*;
import org.niewidoczniakademicy.rezerwacje.core.model.enums.UserType;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Parsed(field = "first name")
    @NonNull
    private String firstName;

    @Parsed(field = "last name")
    @NonNull
    private String lastName;

    @Column(unique = true)
    @NonNull
    private String login;

    @NonNull
    private String password;

    @NonNull
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @NonNull
    private String emailAddress;

    @NonNull
    private String phoneNumber;

    @NonNull
    private LocalDateTime additionTime;
}
