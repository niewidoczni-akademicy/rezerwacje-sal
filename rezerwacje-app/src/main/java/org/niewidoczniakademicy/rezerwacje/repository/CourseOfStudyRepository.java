package org.niewidoczniakademicy.rezerwacje.repository;

import org.niewidoczniakademicy.rezerwacje.core.model.course.CourseOfStudy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseOfStudyRepository extends JpaRepository<CourseOfStudy, Integer> {
    @Override
    <S extends CourseOfStudy> S save(S entity);

    @Override
    List<CourseOfStudy> findAll();
}
