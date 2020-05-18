package org.niewidoczniakademicy.rezerwacje.model.rest.recruitment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.Recruitment;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetRecruitmentsResponse {

    private List<Recruitment> recruitments;
}
