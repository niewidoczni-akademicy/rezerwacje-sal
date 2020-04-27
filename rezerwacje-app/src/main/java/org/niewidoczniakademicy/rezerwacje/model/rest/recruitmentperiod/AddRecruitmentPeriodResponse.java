package org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddRecruitmentPeriodResponse {

    @NonNull
    private Long recruitmentPeriodId;
}
