package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.niewidoczniakademicy.rezerwacje.core.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.user.AddSystemUserRequest;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.user.AddSystemUserResponse;
import org.niewidoczniakademicy.rezerwacje.dao.repository.UserRepository;
import org.niewidoczniakademicy.rezerwacje.service.converter.ConversionService;
import org.niewidoczniakademicy.rezerwacje.service.exception.InvalidEmailAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final ConversionService conversionService;
    private final UserRepository userRepository;


    public AddSystemUserResponse saveSystemUser(AddSystemUserRequest request) {
        validateAddSystemUserRequest(request);

        SystemUser systemUser = conversionService.convert(request);
        userRepository.save(systemUser);

        return AddSystemUserResponse.builder()
                .userId(systemUser.getId())
                .build();
    }


    public void validateAddSystemUserRequest(AddSystemUserRequest request) {
        boolean isEmailValid = EmailValidator.getInstance().isValid(request.getEmailAddress());

        if (!isEmailValid)
            throw new InvalidEmailAddressException();
    }

}
