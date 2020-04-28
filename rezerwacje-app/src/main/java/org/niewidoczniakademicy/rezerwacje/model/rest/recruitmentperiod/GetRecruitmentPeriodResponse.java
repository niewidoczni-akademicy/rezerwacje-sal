package org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentPeriod;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRecruitmentPeriodResponse {

    @NotNull
    private RecruitmentPeriod recruitmentPeriod;
}
