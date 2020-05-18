package org.niewidoczniakademicy.rezerwacje.model.rest.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentAndRecruitmentPeriodConnectionResponse {

    @NonNull
    private Long recruitmentId;

    @NonNull
    private Long recruitmentPeriodId;
}
