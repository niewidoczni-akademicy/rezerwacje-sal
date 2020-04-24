package org.niewidoczniakademicy.rezerwacje.dao.repository;

import org.niewidoczniakademicy.rezerwacje.core.model.database.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<SystemUser, Long> {

    @Override
    <S extends SystemUser> S save(S entity);

    @Override
    List<SystemUser> findAll();

    Optional<SystemUser> findByLogin(String login);

    List<SystemUser> findSystemUsersByLoginIn(Set<String> logins);

    Optional<List<SystemUser>> findSystemUsersByFirstNameAndLastName(String firstName, String lastName);
}
