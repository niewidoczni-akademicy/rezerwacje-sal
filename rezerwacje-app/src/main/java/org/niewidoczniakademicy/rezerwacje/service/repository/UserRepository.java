package org.niewidoczniakademicy.rezerwacje.service.repository;

import org.niewidoczniakademicy.rezerwacje.model.database.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<SystemUser, Long> {
    Optional<SystemUser> findByLogin(String login);

    Optional<List<SystemUser>> findSystemUsersByFirstNameAndLastName(String firstName, String lastName);
}
