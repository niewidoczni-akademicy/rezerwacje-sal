package org.niewidoczniakademicy.rezerwacje.core.model.rest.recruitment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.core.model.database.RecruitmentPeriod;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRecruitmentPeriodResponse {

    private RecruitmentPeriod recruitmentPeriod;
}
