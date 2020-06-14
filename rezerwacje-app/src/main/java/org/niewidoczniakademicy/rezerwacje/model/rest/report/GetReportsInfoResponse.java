package org.niewidoczniakademicy.rezerwacje.model.rest.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetReportsInfoResponse {

    @NotNull
    private List<String> currentReports;

}
