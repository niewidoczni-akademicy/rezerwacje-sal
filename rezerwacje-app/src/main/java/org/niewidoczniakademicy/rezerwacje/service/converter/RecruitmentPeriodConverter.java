package org.niewidoczniakademicy.rezerwacje.service.converter;

import org.niewidoczniakademicy.rezerwacje.core.model.database.RecruitmentPeriod;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.recruitment.AddRecruitmentPeriodRequest;
import org.springframework.stereotype.Component;

@Component
public class RecruitmentPeriodConverter
        implements GenericConverter<AddRecruitmentPeriodRequest, RecruitmentPeriod> {

    @Override
    public RecruitmentPeriod createFrom(AddRecruitmentPeriodRequest dto) {
        return RecruitmentPeriod.builder()
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
    }
}
