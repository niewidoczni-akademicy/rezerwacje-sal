package org.niewidoczniakademicy.rezerwacje.dao.repository;

import org.niewidoczniakademicy.rezerwacje.core.model.database.ExamTerm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamTermRepository extends JpaRepository<ExamTerm, Long> {
    Optional<ExamTerm> findByRoomIdAndCourseOfStudyId(Long roomId, Long cosId);
}
