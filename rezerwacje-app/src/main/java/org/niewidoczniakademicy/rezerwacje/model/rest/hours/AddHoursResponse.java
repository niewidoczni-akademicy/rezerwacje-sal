package org.niewidoczniakademicy.rezerwacje.model.rest.hours;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.Hours;
import org.springframework.lang.NonNull;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddHoursResponse {

    @NonNull
    private List<Hours> availabilityHours;
}
