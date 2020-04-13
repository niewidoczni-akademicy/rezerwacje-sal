package org.niewidoczniakademicy.rezerwacje.dao.repository;

import org.niewidoczniakademicy.rezerwacje.core.model.database.CourseOfStudy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseOfStudyRepository extends JpaRepository<CourseOfStudy, Long> {
}
