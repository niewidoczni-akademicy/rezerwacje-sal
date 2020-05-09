package org.niewidoczniakademicy.rezerwacje.model.database;

import lombok.*;
import org.niewidoczniakademicy.rezerwacje.model.shared.UserType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table
@Entity
@Builder
@ToString
@EqualsAndHashCode(exclude = {"coursesOfStudies"})
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

    @ManyToMany(mappedBy = "systemUsers")
    private Set<CourseOfStudy> coursesOfStudies = new HashSet<>();
}
