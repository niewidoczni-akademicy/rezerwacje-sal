package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.niewidoczniakademicy.rezerwacje.core.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.user.AddSystemUserRequest;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.user.AddSystemUserResponse;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.user.GetSystemUserResponse;
import org.niewidoczniakademicy.rezerwacje.core.model.rest.user.GetSystemUsersResponse;
import org.niewidoczniakademicy.rezerwacje.dao.repository.UserRepository;
import org.niewidoczniakademicy.rezerwacje.service.converter.ConversionService;
import org.niewidoczniakademicy.rezerwacje.service.exception.InvalidEmailAddressException;
import org.niewidoczniakademicy.rezerwacje.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class UserService {

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

    public GetSystemUserResponse getSystemUserByLogin(String login) {
        final SystemUser systemUser = userRepository
                .findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("User with login: " + login + " does not exist"));

        return GetSystemUserResponse.builder()
                .systemUser(systemUser)
                .build();
    }

    public GetSystemUsersResponse getSystemUsersByFirstNameAndLastName(String firstName, String lastName) {
        final List<SystemUser> systemUsers = userRepository
                .findSystemUsersByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new UserNotFoundException("No user with first name: " + firstName + " and last name : " + lastName + " found"));

        return GetSystemUsersResponse.builder()
                .systemUsers(systemUsers)
                .build();
    }

    public GetSystemUsersResponse getAllSystemUsers() {
        final List<SystemUser> systemUsers = userRepository.findAll();

        if (systemUsers == null)
            throw new UserNotFoundException("No users in the system found");

        return GetSystemUsersResponse.builder()
                .systemUsers(systemUsers)
                .build();
    }

    public void validateAddSystemUserRequest(AddSystemUserRequest request) {
        boolean isEmailValid = EmailValidator.getInstance().isValid(request.getEmailAddress());

        if (!isEmailValid)
            throw new InvalidEmailAddressException();
    }

}
