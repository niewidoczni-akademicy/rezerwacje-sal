package org.niewidoczniakademicy.rezerwacje.model.rest.recruitment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.lang.NonNull;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddRecruitmentRequest {

    @NonNull
    private String name;

    @NonNull
    private LocalDateTime startTime;

    @NonNull
    private LocalDateTime endTime;
}
