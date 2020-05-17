package org.niewidoczniakademicy.rezerwacje.model.rest.recruitment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.Recruitment;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRecruitmentResponse {

    private Recruitment recruitment;
}
