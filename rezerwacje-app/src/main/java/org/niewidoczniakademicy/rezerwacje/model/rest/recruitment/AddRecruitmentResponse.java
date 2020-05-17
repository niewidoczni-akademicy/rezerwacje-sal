package org.niewidoczniakademicy.rezerwacje.model.rest.recruitment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddRecruitmentResponse {

    @NonNull
    private Long recruitmentId;
}
