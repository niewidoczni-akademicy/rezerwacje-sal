package org.niewidoczniakademicy.rezerwacje.service.converter;

import org.niewidoczniakademicy.rezerwacje.core.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.user.AddSystemUserRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SystemUserConverter implements GenericConverter<AddSystemUserRequest, SystemUser> {

    @Override
    public SystemUser createFrom(AddSystemUserRequest dto) {
        return SystemUser.builder()
                .userUniqueId(dto.getUserUniqueId())
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
