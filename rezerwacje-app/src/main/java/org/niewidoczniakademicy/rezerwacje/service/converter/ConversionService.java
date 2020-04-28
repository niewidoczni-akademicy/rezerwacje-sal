package org.niewidoczniakademicy.rezerwacje.service.converter;

import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentPeriod;
import org.niewidoczniakademicy.rezerwacje.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod.AddRecruitmentPeriodRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.AddSystemUserRequest;

public interface ConversionService {

    SystemUser convert(AddSystemUserRequest request);

    RecruitmentPeriod convert(AddRecruitmentPeriodRequest request);
}
