package org.niewidoczniakademicy.rezerwacje.core.model.rest;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.core.model.database.RecruitmentPeriod;

@Data
@Builder
@NoArgsConstructor
public class GetRecruitmentPeriodResponse {

    private RecruitmentPeriod recruitmentPeriod;
}
