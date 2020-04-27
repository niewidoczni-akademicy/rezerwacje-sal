package org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod;

import lombok.*;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentPeriod;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRecruitmentPeriodsResponse {

    @NonNull
    private List<RecruitmentPeriod> recruitmentPeriods;
}
