package org.niewidoczniakademicy.rezerwacje.model.rest.examterm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddExamTermRequest {
    @NotNull
    LocalDate day;

    @NotNull
    LocalTime timeStart;

    @NotNull
    LocalTime timeEnd;

    @NotNull
    Long cosId;

    @NotNull
    Long roomId;
}
