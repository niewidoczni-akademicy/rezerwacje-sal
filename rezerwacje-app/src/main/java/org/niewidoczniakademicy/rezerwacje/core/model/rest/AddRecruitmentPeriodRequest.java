package org.niewidoczniakademicy.rezerwacje.core.model.rest;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Data
@Builder
public class AddRecruitmentPeriodRequest {

    @NonNull
    private LocalDate startDate;

    @NonNull
    private LocalDate endDate;
}
