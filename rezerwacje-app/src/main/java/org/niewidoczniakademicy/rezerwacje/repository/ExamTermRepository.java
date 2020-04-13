package org.niewidoczniakademicy.rezerwacje.repository;

import org.niewidoczniakademicy.rezerwacje.core.model.database.ExamTerm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamTermRepository extends JpaRepository<ExamTerm, Long> {
    @Override
    <S extends ExamTerm> S save(S entity);

    @Override
    List<ExamTerm> findAll();

    @Override
    Optional<ExamTerm> findById(Long id);

    Optional<ExamTerm> findByRoomIdAndCourseOfStudyId(Long roomId, Long cosId);
}
