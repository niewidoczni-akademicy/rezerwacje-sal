package org.niewidoczniakademicy.rezerwacje.service.converter;

import org.niewidoczniakademicy.rezerwacje.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.AddSystemUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public final class SystemUserConverter
        implements GenericConverter<AddSystemUserRequest, SystemUser> {

    private final PasswordEncoder encoder;

    @Autowired
    public SystemUserConverter(@Lazy PasswordEncoder encoder) {
        this.encoder = encoder;
    }

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
