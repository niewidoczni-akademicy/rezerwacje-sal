package org.niewidoczniakademicy.rezerwacje.service.converter;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.AddSystemUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class SystemUserConverter
        implements GenericConverter<AddSystemUserRequest, SystemUser> {

    private final PasswordEncoder encoder;

    @Override
    public SystemUser createFrom(final AddSystemUserRequest dto) {
        return SystemUser.builder()
                .userType(dto.getUserType())
                .additionTime(LocalDateTime.now())
                .emailAddress(dto.getEmailAddress())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .login(dto.getLogin())
                .password(encoder.encode(dto.getPassword()))
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }
}
