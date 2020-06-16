package org.niewidoczniakademicy.rezerwacje.service.repository;

import org.niewidoczniakademicy.rezerwacje.model.database.Hours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoursRepository extends JpaRepository<Hours, Long> {
}
