package org.niewidoczniakademicy.rezerwacje.model.rest.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.niewidoczniakademicy.rezerwacje.model.rest.hours.TimeInterval;
import org.niewidoczniakademicy.rezerwacje.model.shared.DayOfWeek;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddRoomRequest {

    @NonNull
    private String building;

    @NonNull
    private String name;

    @NonNull
    private Integer capacity;

    private Map<DayOfWeek, List<TimeInterval>> availabilityDetails;
}
