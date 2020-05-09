package org.niewidoczniakademicy.rezerwacje.service.converter;

import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentPeriod;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod.AddRecruitmentPeriodRequest;
import org.springframework.stereotype.Component;

@Component
public final class RecruitmentPeriodConverter
        implements GenericConverter<AddRecruitmentPeriodRequest, RecruitmentPeriod> {

    @Override
    public RecruitmentPeriod createFrom(final AddRecruitmentPeriodRequest dto) {
        return RecruitmentPeriod.builder()
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
    }
}
