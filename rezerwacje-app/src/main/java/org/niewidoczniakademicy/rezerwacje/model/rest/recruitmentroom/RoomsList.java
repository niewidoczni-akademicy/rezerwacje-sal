package org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomsList {

    private List<Long> roomsIds;
}
