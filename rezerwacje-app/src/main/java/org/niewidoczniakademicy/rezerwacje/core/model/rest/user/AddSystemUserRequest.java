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
public class AddSystemUserRequest {

    @NonNull
    private String userUniqueId;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String emailAddress;

    @NonNull
    private String phoneNumber;

    @NonNull
    private String login;

    @NonNull
    private String password;
}
