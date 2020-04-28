package org.niewidoczniakademicy.rezerwacje.service;

import org.junit.jupiter.api.Test;
import org.niewidoczniakademicy.rezerwacje.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.AddSystemUserRequest;
import org.niewidoczniakademicy.rezerwacje.model.shared.UserType;
import org.niewidoczniakademicy.rezerwacje.service.exception.InvalidEmailAddressException;
import org.niewidoczniakademicy.rezerwacje.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = "classpath:database/user_service_test_inserts.sql")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void shouldReturnExactlyTwoUsers_whenThereAreExactlyTwoUsersInTheDatabase() {
        List<SystemUser> users = userService.getAllSystemUsers().getSystemUsers();

        assertEquals("Verifying whether the user service " +
                        "returns exactly 2 users if there are only 2 in the database",
                2, users.size());
    }

    @Test
    public void shouldReturnUserByHisLogin_ifHisLoginExistsInTheDatabase() {
        final String login = "ojtaktakbyczq";
        final SystemUser systemUser = userService.getSystemUserByLogin(login).getSystemUser();

        assertNotNull("Verifying whether returned user is exists", systemUser);
        assertEquals("Verifying user data", UserType.STANDARD, systemUser.getUserType());
        assertEquals("Verifying user data", "Siema", systemUser.getFirstName());
    }

    @Test
    public void shouldThrowUserNotFoundException_whenThereIsNoUserWithSuchLoginInTheDatabase() {
        final String login = "non_existing_login";

        assertThrows(
                UserNotFoundException.class,
                () -> userService.getSystemUserByLogin(login)
        );
    }

    @Test
    public void shouldSaveSystemUser_andReturnHimAfterAdditionCorrectly() {
        final String uniqueLogin = "UNIQUE_LOGIN_FOR_TESTING";
        final String emailAddressToVerify = "email_address_to_verify@gmail.com";

        final AddSystemUserRequest request = AddSystemUserRequest.builder()
                .emailAddress(emailAddressToVerify)
                .firstName("Essa")
                .lastName("Kozak")
                .login(uniqueLogin)
                .password("xdxdxdxdxd")
                .phoneNumber("123456789")
                .userType(UserType.ADMINISTRATOR)
                .build();

        userService.saveSystemUser(request);

        final SystemUser user = userService.getSystemUserByLogin(uniqueLogin).getSystemUser();

        assertNotNull("Testing getting recently added user from the database", user);
        assertEquals("Verifying the user", emailAddressToVerify, user.getEmailAddress());
    }

    @Test
    public void shouldFindUserByHisFirstAndLastName_whenHeExistsInTheDatabase() {
        final String firstName = "Essasito";
        final String lastName = "Wariacie";

        final List<SystemUser> users = userService
                .getSystemUsersByFirstNameAndLastName(firstName, lastName)
                .getSystemUsers();

        assertEquals("Verifying whether there is only one user with such first and last name",
                1, users.size());

        final SystemUser user = users.get(0);
        assertEquals("Verifying user data", "nielegalne", user.getLogin());
    }

    @Test
    public void shouldThrowUserNotFoundException_whenThereIsNoUserWithSuchFirstAndLastNameInTheDatabase() {
        final String firstName = "I DO NOT";
        final String lastName = "EXIST";

        assertThrows(
                UserNotFoundException.class,
                () -> userService.getSystemUsersByFirstNameAndLastName(firstName, lastName)
        );
    }

    @Test
    public void shouldThrowInvalidEmailAddressException_whenTryingToSaveUserWithIncorrectAddress() {
        final String invalidEmailAddress = "invalid_email_address@gmail.";

        final AddSystemUserRequest request = AddSystemUserRequest.builder()
                .emailAddress(invalidEmailAddress)
                .firstName("Essa")
                .lastName("Kozak")
                .login("qwertyuiop")
                .password("xdxdxdxdxd")
                .phoneNumber("123456789")
                .userType(UserType.ADMINISTRATOR)
                .build();

        assertThrows(
                InvalidEmailAddressException.class,
                () -> userService.saveSystemUser(request)
        );
    }

}
