package org.niewidoczniakademicy.rezerwacje.core.model.rest.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddSystemUserResponse {

    @NonNull
    private Long userId;
}
