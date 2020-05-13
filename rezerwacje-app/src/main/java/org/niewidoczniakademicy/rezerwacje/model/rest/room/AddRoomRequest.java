package org.niewidoczniakademicy.rezerwacje.model.rest.room;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddRoomRequest {

    @NonNull
    private String building;

    @NonNull
    private String name;

    @NonNull
    private Integer capacity;
}
