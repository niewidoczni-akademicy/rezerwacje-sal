package org.niewidoczniakademicy.rezerwacje.model.rest.room;

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
public class EditRoomRequest {

    @NotNull
    private Long id;

    @NonNull
    private String building;

    @NonNull
    private String name;

    @NonNull
    private Integer capacity;

}
