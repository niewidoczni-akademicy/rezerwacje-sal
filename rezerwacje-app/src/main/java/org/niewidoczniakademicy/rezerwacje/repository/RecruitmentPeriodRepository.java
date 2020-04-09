package org.niewidoczniakademicy.rezerwacje.repository;

import org.niewidoczniakademicy.rezerwacje.core.model.RecruitmentPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitmentPeriodRepository extends JpaRepository<RecruitmentPeriod, Integer> {
    @Override
    <S extends RecruitmentPeriod> S save(S entity);

    @Override
    List<RecruitmentPeriod> findAll();
}