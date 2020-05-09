package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.niewidoczniakademicy.rezerwacje.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.AddSystemUserRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.AddSystemUserResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.GetSystemUserResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.GetSystemUsersResponse;
import org.niewidoczniakademicy.rezerwacje.model.security.UserPrincipal;
import org.niewidoczniakademicy.rezerwacje.service.converter.ConversionService;
import org.niewidoczniakademicy.rezerwacje.service.exception.InvalidEmailAddressException;
import org.niewidoczniakademicy.rezerwacje.service.exception.UserNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class UserService implements UserDetailsService {

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

    private SystemUser getSystemUserByLoginInternal(String login) {
        return userRepository
                .findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("User with login: " + login + " does not exist"));
    }

    public GetSystemUserResponse getSystemUserByLogin(String login) {
        return GetSystemUserResponse.builder()
                .systemUser(getSystemUserByLoginInternal(login))
                .build();
    }

    public GetSystemUsersResponse getSystemUsersByFirstNameAndLastName(String firstName, String lastName) {
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

    public void validateAddSystemUserRequest(AddSystemUserRequest request) {
        boolean isEmailValid = EmailValidator.getInstance().isValid(request.getEmailAddress());

        if (!isEmailValid) {
            throw new InvalidEmailAddressException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug(username + " tries to log in");
        try {
            return new UserPrincipal(getSystemUserByLoginInternal(username));
        } catch (UserNotFoundException e) {
            log.debug(username + " not found", e);
            throw new UsernameNotFoundException(username + " not found", e);
        }
    }
}
