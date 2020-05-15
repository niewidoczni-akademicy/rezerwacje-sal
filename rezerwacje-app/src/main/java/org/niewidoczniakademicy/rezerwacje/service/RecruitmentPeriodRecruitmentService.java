package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.Recruitment;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentPeriod;
import org.niewidoczniakademicy.rezerwacje.model.rest.other.RecruitmentAndRecruitmentPeriodConnectionResponse;
import org.niewidoczniakademicy.rezerwacje.service.repository.RecruitmentPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class RecruitmentPeriodRecruitmentService {

    private final RecruitmentPeriodRepository recruitmentPeriodRepository;

    public RecruitmentAndRecruitmentPeriodConnectionResponse connectRecruitmentWithRecruitmentPeriod(
            final Recruitment recruitment,
            final RecruitmentPeriod recruitmentPeriod) {

        recruitmentPeriod.addRecruitment(recruitment);
        recruitmentPeriodRepository.save(recruitmentPeriod);

        return RecruitmentAndRecruitmentPeriodConnectionResponse.builder()
                .recruitmentId(recruitment.getId())
                .recruitmentPeriodId(recruitmentPeriod.getId())
                .build();
    }

}
