package org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentAndRoomConnectionResponse {

    @NonNull
    private Long recruitmentId;

    @NonNull
    private Long roomId;
}
