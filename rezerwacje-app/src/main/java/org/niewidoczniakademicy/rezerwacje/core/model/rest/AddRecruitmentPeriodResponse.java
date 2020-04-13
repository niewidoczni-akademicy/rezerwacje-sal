package org.niewidoczniakademicy.rezerwacje.core.model.rest;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddRecruitmentPeriodResponse {

    @NonNull
    private Long recruitmentPeriodId;
}
