package org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectRecruitmentAndRoomRequest {

    @NotNull
    private Long recruitmentId;

    @NotNull
    private Long roomId;
}
