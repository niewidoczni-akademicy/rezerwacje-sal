package org.niewidoczniakademicy.rezerwacje.model.rest.hours;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.shared.DayOfWeek;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddHoursRequest {

    @NonNull
    private Map<DayOfWeek, List<TimeInterval>> availabilityDetails;

    @NotNull
    private Long roomId;
}
