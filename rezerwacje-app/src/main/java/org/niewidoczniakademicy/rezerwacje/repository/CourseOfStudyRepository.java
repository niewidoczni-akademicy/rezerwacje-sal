package org.niewidoczniakademicy.rezerwacje.repository;

import org.niewidoczniakademicy.rezerwacje.core.model.database.CourseOfStudy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseOfStudyRepository extends JpaRepository<CourseOfStudy, Long> {
    @Override
    <S extends CourseOfStudy> S save(S entity);

    @Override
    <S extends CourseOfStudy> List<S> saveAll(Iterable<S> rooms);

    @Override
    List<CourseOfStudy> findAll();
}
