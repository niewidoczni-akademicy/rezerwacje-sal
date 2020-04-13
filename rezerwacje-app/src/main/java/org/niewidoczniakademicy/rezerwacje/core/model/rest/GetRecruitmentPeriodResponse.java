package org.niewidoczniakademicy.rezerwacje.core.model.rest;

import lombok.Builder;
import lombok.Data;
import org.niewidoczniakademicy.rezerwacje.core.model.database.RecruitmentPeriod;

@Data
@Builder
public class GetRecruitmentPeriodResponse {

    RecruitmentPeriod recruitmentPeriod;
}
