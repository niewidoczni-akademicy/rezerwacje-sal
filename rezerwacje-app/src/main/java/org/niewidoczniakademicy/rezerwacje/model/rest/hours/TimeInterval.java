package org.niewidoczniakademicy.rezerwacje.model.rest.hours;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeInterval {

    @NonNull
    @JsonFormat(pattern = "H:mm")
    private LocalTime timeStart;

    @NonNull
    @JsonFormat(pattern = "H:mm")
    private LocalTime timeEnd;
}
