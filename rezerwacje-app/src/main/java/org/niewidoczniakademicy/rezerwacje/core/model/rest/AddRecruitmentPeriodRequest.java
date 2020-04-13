package org.niewidoczniakademicy.rezerwacje.core.model.rest;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
public class AddRecruitmentPeriodRequest {

    @NonNull
    private LocalDate startDate;

    @NonNull
    private LocalDate endDate;
}
