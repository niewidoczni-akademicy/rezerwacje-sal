package org.niewidoczniakademicy.rezerwacje.service.converter;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.core.model.database.RecruitmentPeriod;
import org.niewidoczniakademicy.rezerwacje.core.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.recruitment.AddRecruitmentPeriodRequest;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.user.AddSystemUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class ConversionServiceImpl implements ConversionService {

    private final SystemUserConverter systemUserConverter;
    private final RecruitmentPeriodConverter recruitmentPeriodConverter;

    @Override
    public SystemUser convert(AddSystemUserRequest request) {
        return systemUserConverter.createFrom(request);
    }

    @Override
    public RecruitmentPeriod convert(AddRecruitmentPeriodRequest request) {
        return recruitmentPeriodConverter.createFrom(request);
    }

}
