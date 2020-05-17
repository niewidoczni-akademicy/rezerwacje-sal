package org.niewidoczniakademicy.rezerwacje.service.converter;

import org.niewidoczniakademicy.rezerwacje.model.database.Recruitment;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitment.AddRecruitmentRequest;
import org.springframework.stereotype.Component;

@Component
public final class RecruitmentConverter
        implements GenericConverter<AddRecruitmentRequest, Recruitment> {

    @Override
    public Recruitment createFrom(final AddRecruitmentRequest dto) {
        return Recruitment.builder()
                .name(dto.getName())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }
}
