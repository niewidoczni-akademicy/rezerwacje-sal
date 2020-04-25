package org.niewidoczniakademicy.rezerwacje.core.model.rest.recruitment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.niewidoczniakademicy.rezerwacje.core.model.database.RecruitmentPeriod;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRecruitmentPeriodsResponse {

    @NonNull
    private List<RecruitmentPeriod> recruitmentPeriods;
}
