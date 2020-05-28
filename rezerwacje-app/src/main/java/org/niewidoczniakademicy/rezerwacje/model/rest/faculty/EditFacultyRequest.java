package org.niewidoczniakademicy.rezerwacje.model.rest.faculty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditFacultyRequest {

    @NotNull
    private Long id;

    @NonNull
    private String name;

}
