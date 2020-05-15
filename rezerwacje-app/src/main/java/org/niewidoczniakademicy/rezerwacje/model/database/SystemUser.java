package org.niewidoczniakademicy.rezerwacje.model.database;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.niewidoczniakademicy.rezerwacje.model.shared.UserType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table
@Entity
@Builder
@ToString
@EqualsAndHashCode(exclude = {"userCourses"})
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

    @Builder.Default
    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "systemUser")
    private Set<UserCourses> userCourses = new HashSet<>();
}
