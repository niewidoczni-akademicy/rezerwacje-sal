package org.niewidoczniakademicy.rezerwacje.service.repository;

import org.niewidoczniakademicy.rezerwacje.model.database.ExamTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ExamTermRepository extends JpaRepository<ExamTerm, Long> {

    @Query(value = "SELECT ET.ID\n"
            + "FROM EXAM_TERM ET\n"
            + "         JOIN RECRUITMENT_PERIOD RP ON ET.RECRUITMENT_PERIOD_ID = RP.RECRUITMENT_PERIOD_ID\n"
            + "WHERE RP.recruitment_id = (\n"
            + "    SELECT R.ID\n"
            + "    FROM RECRUITMENT R\n"
            + "             JOIN RECRUITMENT_PERIOD RP ON R.ID = RP.recruitment_id\n"
            + "    WHERE RP.ID = :recruitmentPeriodId\n"
            + "  AND ET.DAY = :day\n"
            + "  AND (\n"
            + "        (ET.TIME_START BETWEEN :timeStart AND :timeEnd)\n"
            + "        OR (ET.TIME_END BETWEEN :timeStart AND :timeEnd)\n"
            + "    )", nativeQuery = true)
    List<Long> getExamTermIdsByDates(
            @Param("recruitmentPeriodId") Long recruitmentPeriodId,
            @Param("day") LocalDate day,
            @Param("timeStart") LocalTime timeStart,
            @Param("timeEnd") LocalTime timeEnd);

    Optional<ExamTerm> findByRecruitmentRoomIdAndCourseOfStudyId(Long roomId, Long cosId);
}
