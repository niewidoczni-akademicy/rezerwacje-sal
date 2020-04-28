package org.niewidoczniakademicy.rezerwacje.model.rest.examterm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.ExamTerm;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetExamTermsResponse {

    @NotNull
    private Set<ExamTerm> examTerms;

}
