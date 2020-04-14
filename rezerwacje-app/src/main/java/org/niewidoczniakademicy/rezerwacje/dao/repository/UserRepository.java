package org.niewidoczniakademicy.rezerwacje.dao.repository;

import org.niewidoczniakademicy.rezerwacje.core.model.database.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<SystemUser, Long> {

    @Override
    <S extends SystemUser> S save(S entity);

    @Override
    List<SystemUser> findAll();

    Optional<SystemUser> findByUserUniqueId(String userUniqueId);

    Optional<List<SystemUser>> findSystemUsersByFirstNameAndLastName(String firstName, String lastName);
}
