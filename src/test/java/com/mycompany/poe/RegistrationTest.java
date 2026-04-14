package com.mycompany.poe;

/**
 * Part 1: Done
 * @author lab_services_student: Vuyolwethu Bovu
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest {

    // -----------------------------
    // 6 x assertEquals tests (messages)
    // -----------------------------

    @Test
    public void testUsernameCorrectlyFormatted_AssertEquals_WelcomeMessage() {
        // register user (store details)
        Login regLogin = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        Registration registered = regLogin.getRegisteredUser();

        // login attempt
        Login loginAttempt = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith");
        loginAttempt.loginUser(registered);

        assertEquals(Messages.loginSuccessMessage("Kyle", "Smith"), loginAttempt.returnLoginStatus());
    }

    @Test
    public void testUsernameIncorrectlyFormatted_AssertEquals() {
        Login invalid = new Login("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals(Messages.USERNAME_WRONG_SUGGESTION, invalid.getUsernameValidationMessage());
    }

    @Test
    public void testPasswordMeetsComplexity_AssertEquals() {
        Login valid = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals(Messages.PASSWORD_SUCCESS_MESSAGE, valid.getPasswordValidationMessage());
    }

    @Test
    public void testPasswordDoesNotMeetComplexity_AssertEquals() {
        Login invalid = new Login("kyl_1", "password", "+27838968976", "Kyle", "Smith");
        assertEquals(Messages.USERPASSWORD_WRONG_SUGGESTION, invalid.getPasswordValidationMessage());
    }

    @Test
    public void testCellPhoneCorrectlyFormatted_AssertEquals() {
        Login valid = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals(Messages.CELLPHONE_SUCCESS_MESSAGE, valid.getCellPhoneValidationMessage());
    }

    @Test
    public void testCellPhoneIncorrectlyFormatted_AssertEquals() {
        Login invalid = new Login("kyl_1", "Ch&&sec@ke99!", "08966553", "Kyle", "Smith");
        assertEquals(Messages.CELLPHONE_WRONG_SUGGESTION, invalid.getCellPhoneValidationMessage());
    }

    // -----------------------------
    // 8 x assertTrue / assertFalse tests (booleans)
    // -----------------------------

    @Test
    public void testLoginSuccessful_AssertTrue() {
        Login regLogin = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        Registration registered = regLogin.getRegisteredUser();

        Login attempt = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith");
        assertTrue(attempt.loginUser(registered));
    }

    @Test
    public void testLoginFailed_AssertFalse() {
        Login regLogin = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        Registration registered = regLogin.getRegisteredUser();

        Login attempt = new Login("kyl_1", "wrongpassword", "Kyle", "Smith");
        assertFalse(attempt.loginUser(registered));
    }

    @Test
    public void testUsernameCorrectlyFormatted_AssertTrue() {
        Login valid = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertTrue(valid.checkUserName());
    }

    @Test
    public void testUsernameIncorrectlyFormatted_AssertFalse() {
        Login invalid = new Login("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertFalse(invalid.checkUserName());
    }

    @Test
    public void testPasswordMeetsComplexity_AssertTrue() {
        Login valid = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertTrue(valid.checkPasswordComplexity());
    }

    @Test
    public void testPasswordDoesNotMeetComplexity_AssertFalse() {
        Login invalid = new Login("kyl_1", "password", "+27838968976", "Kyle", "Smith");
        assertFalse(invalid.checkPasswordComplexity());
    }

    @Test
    public void testCellPhoneCorrectlyFormatted_AssertTrue() {
        Login valid = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertTrue(valid.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhoneIncorrectlyFormatted_AssertFalse() {
        Login invalid = new Login("kyl_1", "Ch&&sec@ke99!", "08966553", "Kyle", "Smith");
        assertFalse(invalid.checkCellPhoneNumber());
    }
}