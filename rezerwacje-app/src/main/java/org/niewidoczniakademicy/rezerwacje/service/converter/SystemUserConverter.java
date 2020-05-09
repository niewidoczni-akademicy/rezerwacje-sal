package org.niewidoczniakademicy.rezerwacje.service.converter;

import org.niewidoczniakademicy.rezerwacje.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.AddSystemUserRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public final class SystemUserConverter
        implements GenericConverter<AddSystemUserRequest, SystemUser> {

    @Override
    public SystemUser createFrom(final AddSystemUserRequest dto) {
        return SystemUser.builder()
                .userType(dto.getUserType())
                .additionTime(LocalDateTime.now())
                .emailAddress(dto.getEmailAddress())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .login(dto.getLogin())
                .password(dto.getPassword())
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }
}
