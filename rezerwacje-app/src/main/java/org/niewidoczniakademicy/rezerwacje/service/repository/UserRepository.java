package org.niewidoczniakademicy.rezerwacje.service.repository;

import org.niewidoczniakademicy.rezerwacje.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.model.shared.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<SystemUser, Long> {
    Optional<SystemUser> findByLogin(String login);

    List<SystemUser> findSystemUsersByLoginIn(Set<String> logins);

    Optional<List<SystemUser>> findSystemUsersByFirstNameAndLastName(String firstName, String lastName);

    Optional<List<SystemUser>> findSystemUsersByUserType(UserType userType);
}
