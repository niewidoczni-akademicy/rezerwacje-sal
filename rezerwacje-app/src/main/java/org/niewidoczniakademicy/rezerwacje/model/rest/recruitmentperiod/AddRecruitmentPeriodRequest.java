package org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.shared.StudyDegree;
import org.niewidoczniakademicy.rezerwacje.model.shared.StudyType;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddRecruitmentPeriodRequest {

    @NonNull
    private LocalDate startDate;

    @NonNull
    private LocalDate endDate;

    @NonNull
    private StudyType studyType;

    @NonNull
    private StudyDegree studyDegree;
}
