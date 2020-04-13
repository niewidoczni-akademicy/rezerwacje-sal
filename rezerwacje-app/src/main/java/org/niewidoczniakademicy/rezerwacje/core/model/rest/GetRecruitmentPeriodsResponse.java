package org.niewidoczniakademicy.rezerwacje.core.model.rest;

import lombok.*;
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
