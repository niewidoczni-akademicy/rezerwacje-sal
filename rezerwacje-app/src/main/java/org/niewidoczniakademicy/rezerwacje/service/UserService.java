package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.niewidoczniakademicy.rezerwacje.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.AddSystemUserRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.OperationOnSystemUserResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.GetSystemUserResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.GetSystemUsersResponse;
import org.niewidoczniakademicy.rezerwacje.model.shared.UserType;
import org.niewidoczniakademicy.rezerwacje.service.converter.ConversionService;
import org.niewidoczniakademicy.rezerwacje.service.exception.InvalidEmailAddressException;
import org.niewidoczniakademicy.rezerwacje.service.exception.UserNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class UserService {

    private final ConversionService conversionService;
    private final UserRepository userRepository;

    public OperationOnSystemUserResponse saveSystemUser(final AddSystemUserRequest request) {
        validateAddSystemUserRequest(request);

        SystemUser systemUser = conversionService.convert(request);
        userRepository.save(systemUser);

        return OperationOnSystemUserResponse.builder()
                .userId(systemUser.getId())
                .build();
    }

    public GetSystemUserResponse getSystemUserByLogin(final String login) {
        final SystemUser systemUser = getSystemUserFromDatabaseByLogin(login);

        return GetSystemUserResponse.builder()
                .systemUser(systemUser)
                .build();
    }

    public GetSystemUsersResponse getSystemUsersByFirstNameAndLastName(final String firstName, final String lastName) {
        final List<SystemUser> systemUsers = userRepository
                .findSystemUsersByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new UserNotFoundException("No user with first name: "
                        + firstName + " and last name : "
                        + lastName + " found"));

        return GetSystemUsersResponse.builder()
                .systemUsers(systemUsers)
                .build();
    }

    public GetSystemUsersResponse getAllSystemUsers() {
        final List<SystemUser> systemUsers = userRepository.findAll();

        return GetSystemUsersResponse.builder()
                .systemUsers(systemUsers)
                .build();
    }

    public GetSystemUsersResponse getSystemUsersByType(final UserType type) {
        final List<SystemUser> systemUsers = getSystemUsersFromDatabaseByType(type);

        return GetSystemUsersResponse.builder()
                .systemUsers(systemUsers)
                .build();
    }

    public OperationOnSystemUserResponse deleteSystemUserById(final Long userId) {
        final SystemUser systemUser = getSystemUserFromDatabaseById(userId);
        userRepository.delete(systemUser);

        return OperationOnSystemUserResponse.builder()
                .userId(systemUser.getId())
                .build();
    }

    public OperationOnSystemUserResponse deleteSystemUserByLogin(final String login) {
        final SystemUser systemUser = getSystemUserFromDatabaseByLogin(login);
        userRepository.delete(systemUser);

        return OperationOnSystemUserResponse.builder()
                .userId(systemUser.getId())
                .build();
    }


    public void validateAddSystemUserRequest(final AddSystemUserRequest request) {
        boolean isEmailValid = EmailValidator.getInstance().isValid(request.getEmailAddress());

        if (!isEmailValid) {
            throw new InvalidEmailAddressException();
        }
    }

    public SystemUser getSystemUserFromDatabaseById(final Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " does not exist"));
    }

    private SystemUser getSystemUserFromDatabaseByLogin(final String login) {
        return userRepository
                .findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("User with login: " + login + " does not exist"));
    }

    private List<SystemUser> getSystemUsersFromDatabaseByType(final UserType type) {
        return userRepository
                .findSystemUsersByUserType(type)
                .orElseThrow(() -> new UserNotFoundException("Unable to find users with type: " + type));
    }
}
