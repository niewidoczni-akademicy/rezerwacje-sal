package org.niewidoczniakademicy.rezerwacje.service.repository;

import org.niewidoczniakademicy.rezerwacje.model.database.CourseOfStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseOfStudyRepository extends JpaRepository<CourseOfStudy, Long> {

    @Query(value = "SELECT c.id"
            + " FROM CourseOfStudy c"
            + " JOIN UserCourses u ON u.courseOfStudy.id = c.id"
            + " WHERE u.systemUser.id = :userId")
    List<Long> getCourseOfStudiesForUser(
            @Param("userId") Long userId
    );
}
