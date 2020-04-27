package org.niewidoczniakademicy.rezerwacje.model.rest.examterm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.ExamTerm;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddExamTermResponse {

    @NotNull
    ExamTerm examTerm;
}
