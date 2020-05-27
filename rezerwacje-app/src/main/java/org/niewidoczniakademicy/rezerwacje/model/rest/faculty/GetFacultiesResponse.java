package org.niewidoczniakademicy.rezerwacje.model.rest.faculty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.Faculty;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetFacultiesResponse {

    @NotNull
    private Set<Faculty> faculties;
}
