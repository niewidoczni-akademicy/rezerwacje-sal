package org.niewidoczniakademicy.rezerwacje.core.model.rest.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.core.model.database.SystemUser;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSystemUserResponse {

    private SystemUser systemUser;
}
