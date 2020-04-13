package org.niewidoczniakademicy.rezerwacje.dao.repository;

import org.niewidoczniakademicy.rezerwacje.core.model.database.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SystemUser, Long> {
}
