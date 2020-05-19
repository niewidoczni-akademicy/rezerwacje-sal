package org.niewidoczniakademicy.rezerwacje.service.converter;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.Recruitment;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentPeriod;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod.AddRecruitmentPeriodRequest;
import org.niewidoczniakademicy.rezerwacje.service.exception.RecruitmentNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.repository.RecruitmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class RecruitmentPeriodConverter
        implements GenericConverter<AddRecruitmentPeriodRequest, RecruitmentPeriod> {

    private final RecruitmentRepository recruitmentRepository;

    @Override
    public RecruitmentPeriod createFrom(final AddRecruitmentPeriodRequest dto) {
        Long recruitmentId = dto.getRecruitmentId();
        Recruitment recruitment = recruitmentRepository
                .findById(recruitmentId)
                .orElseThrow(() -> new RecruitmentNotFoundException("No recruitment with id " + recruitmentId));

        return RecruitmentPeriod.builder()
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .studyType(dto.getStudyType())
                .studyDegree(dto.getStudyDegree())
                .recruitment(recruitment)
                .build();
    }
}
