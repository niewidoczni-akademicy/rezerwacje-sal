package org.niewidoczniakademicy.rezerwacje.model.rest.faculty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddFacultyRequest {

    @NonNull
    private String name;
}
