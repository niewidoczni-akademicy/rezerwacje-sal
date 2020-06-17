package org.niewidoczniakademicy.rezerwacje.service.repository;

import org.niewidoczniakademicy.rezerwacje.model.database.CourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.model.database.UserCourses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCoursesRepository extends JpaRepository<UserCourses, Long> {

    Optional<UserCourses> findBySystemUserAndCourseOfStudy(SystemUser systemUser, CourseOfStudy courseOfStudy);
}
