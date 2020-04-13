package org.niewidoczniakademicy.rezerwacje.core.model.rest;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
public class AddRecruitmentPeriodResponse {

    @NonNull
    private Long recruitmentPeriodId;
}
