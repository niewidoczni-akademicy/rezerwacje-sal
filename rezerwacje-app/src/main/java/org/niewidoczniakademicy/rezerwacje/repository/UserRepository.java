package org.niewidoczniakademicy.rezerwacje.repository;

import org.niewidoczniakademicy.rezerwacje.core.model.database.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<SystemUser, Long> {

    @Override
    <S extends SystemUser> S save(S entity);

    @Override
    List<SystemUser> findAll();
}
