package org.niewidoczniakademicy.rezerwacje.core.model.rest;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class AddRecruitmentPeriodResponse {

    @NonNull
    private Long recruitmentPeriodId;
}
