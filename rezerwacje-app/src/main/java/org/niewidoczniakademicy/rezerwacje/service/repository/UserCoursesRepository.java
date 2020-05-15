package org.niewidoczniakademicy.rezerwacje.service.repository;

import org.niewidoczniakademicy.rezerwacje.model.database.UserCourses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCoursesRepository extends JpaRepository<UserCourses, Long> {
}
