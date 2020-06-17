package org.niewidoczniakademicy.rezerwacje.model.rest.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetReportRecruitmentRequest {

    @NonNull
    private List<Long> recruitmentPeriods;

    @NonNull
    private List<Long> faculties;

    @NonNull
    private List<Long> courseOfStudies;

    @NonNull
    private List<Long> rooms;

}
