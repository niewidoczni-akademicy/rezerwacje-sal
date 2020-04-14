package org.niewidoczniakademicy.rezerwacje.core.model.rest.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.core.model.database.SystemUser;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSystemUsersResponse {

    private List<SystemUser> systemUsers;
}
