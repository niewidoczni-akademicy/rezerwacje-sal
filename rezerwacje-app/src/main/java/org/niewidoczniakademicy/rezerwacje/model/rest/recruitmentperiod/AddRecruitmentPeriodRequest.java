package org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.shared.StudyDegree;
import org.niewidoczniakademicy.rezerwacje.model.shared.StudyType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddRecruitmentPeriodRequest {

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StudyType studyType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StudyDegree studyDegree;

    @NotNull
    private Long recruitmentId;
}
