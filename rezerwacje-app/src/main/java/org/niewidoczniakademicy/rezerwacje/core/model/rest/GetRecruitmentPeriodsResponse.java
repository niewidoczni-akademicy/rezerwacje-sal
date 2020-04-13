package org.niewidoczniakademicy.rezerwacje.core.model.rest;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.niewidoczniakademicy.rezerwacje.core.model.database.RecruitmentPeriod;

import java.util.List;

@Data
@Builder
public class GetRecruitmentPeriodsResponse {

    @NonNull
    private List<RecruitmentPeriod> recruitmentPeriods;
}
