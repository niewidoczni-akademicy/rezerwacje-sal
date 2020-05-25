package org.niewidoczniakademicy.rezerwacje.model.rest.examterm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExamTermRequest {

    @NotNull
    private Long id;

    @NonNull
    private Boolean isDeleted;

    @NotNull
    private LocalDate day;

    @NotNull
    private LocalTime timeStart;

    @NotNull
    private LocalTime timeEnd;

    @NotNull
    private Long recruitmentPeriodId;

    @NotNull
    private Long courseOfStudyId;

    @NotNull
    private Long recruitmentRoomId;
}
