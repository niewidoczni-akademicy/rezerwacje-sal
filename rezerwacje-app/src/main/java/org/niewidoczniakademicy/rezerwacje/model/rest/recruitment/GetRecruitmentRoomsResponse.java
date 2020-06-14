package org.niewidoczniakademicy.rezerwacje.model.rest.recruitment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentRoom;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRecruitmentRoomsResponse {

    private Set<RecruitmentRoom> recruitmentRooms;
}
