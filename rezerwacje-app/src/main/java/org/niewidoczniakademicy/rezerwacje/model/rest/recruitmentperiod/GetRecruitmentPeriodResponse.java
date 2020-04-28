package org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentPeriod;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRecruitmentPeriodResponse {

    private RecruitmentPeriod recruitmentPeriod;
}
