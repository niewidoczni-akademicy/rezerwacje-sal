package org.niewidoczniakademicy.rezerwacje.dao.repository;

import org.niewidoczniakademicy.rezerwacje.core.model.database.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findFacultiesByNameIn(Set<String> names);
}
