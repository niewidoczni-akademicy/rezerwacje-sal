package org.niewidoczniakademicy.rezerwacje.service;

import org.junit.jupiter.api.Test;
import org.niewidoczniakademicy.rezerwacje.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.GetSystemUserResponse;
import org.niewidoczniakademicy.rezerwacje.model.shared.UserType;
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

        assertEquals("Testing whether the user service " +
                        "returns exactly 2 users if there are only 2 in the database",
                2, users.size());
    }

    @Test
    public void shouldReturnUserByHisLogin_ifHisLoginExistsInTheDatabase() {
        final String login = "ojtaktakbyczq";
        final SystemUser systemUser = userService.getSystemUserByLogin(login).getSystemUser();

        assertNotNull("Testing whether returned user is exists", systemUser);
        assertEquals("Testing user data", UserType.STANDARD, systemUser.getUserType());
        assertEquals("Testing user data", "Siema", systemUser.getFirstName());
    }

    @Test
    public void shouldThrowUserNotFoundException_whenThereIsNoUserWithSuchLoginInTheDatabase() {
        final String login = "non_existing_login";

        assertThrows(
                UserNotFoundException.class,
                () -> userService.getSystemUserByLogin(login)
        );
    }


}
