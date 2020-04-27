package org.niewidoczniakademicy.rezerwacje.service.repository;

import org.niewidoczniakademicy.rezerwacje.model.database.CourseOfStudy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseOfStudyRepository extends JpaRepository<CourseOfStudy, Long> {
}
