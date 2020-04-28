package org.niewidoczniakademicy.rezerwacje.service.repository;

import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RecruitmentPeriodRepository extends JpaRepository<RecruitmentPeriod, Long> {
    List<RecruitmentPeriod> findByStartDateGreaterThanEqualAndEndDateLessThanEqual(LocalDate startDate,
                                                                                   LocalDate endDate);
}
