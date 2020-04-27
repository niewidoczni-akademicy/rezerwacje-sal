package org.niewidoczniakademicy.rezerwacje.service.repository;

import org.niewidoczniakademicy.rezerwacje.model.database.ExamTerm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamTermRepository extends JpaRepository<ExamTerm, Long> {
    Optional<ExamTerm> findByRoomIdAndCourseOfStudyId(Long roomId, Long cosId);
}
