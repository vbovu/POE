package com.mycompany.poe;

/**
 *
 * @author lab_services_student: Vuyolwethu Bovu
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest {

    public RegistrationTest() {
    }

    @Test
    public void testGetUsername() {
        Login valid = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertTrue(valid.checkUserName());
        assertEquals(Messages.USERNAME_SUCCESS_MESSAGE, valid.getUsernameValidationMessage());

        Login invalid = new Login("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertFalse(invalid.checkUserName());
        assertEquals(Messages.USERNAME_WRONG_SUGGESTION, invalid.getUsernameValidationMessage());
    }

    @Test
    public void testGetPassword() {
        Login valid = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertTrue(valid.checkPasswordComplexity());
        assertEquals(Messages.PASSWORD_SUCCESS_MESSAGE, valid.getPasswordValidationMessage());

        Login invalid = new Login("kyl_1", "password", "+27838968976", "Kyle", "Smith");
        assertFalse(invalid.checkPasswordComplexity());
        assertEquals(Messages.USERPASSWORD_WRONG_SUGGESTION, invalid.getPasswordValidationMessage());
    }

    @Test
    public void testGetCellPhone() {
        Login valid = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertTrue(valid.checkCellPhoneNumber());
        assertEquals(Messages.CELLPHONE_SUCCESS_MESSAGE, valid.getCellPhoneValidationMessage());

        Login invalid = new Login("kyl_1", "Ch&&sec@ke99!", "08966553", "Kyle", "Smith");
        assertFalse(invalid.checkCellPhoneNumber());
        assertEquals(Messages.CELLPHONE_WRONG_SUGGESTION, invalid.getCellPhoneValidationMessage());
    }

    @Test
    public void testRegisterUserOverallMessage() {
        Login valid = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals(Messages.REGISTRATION_SUCCESS_BLOCK, valid.registerUser());
        assertTrue(valid.getLoginCanProceedStatus());

        Login invalidUsername = new Login("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals(Messages.USERNAME_WRONG_SUGGESTION, invalidUsername.registerUser());
        assertFalse(invalidUsername.getLoginCanProceedStatus());
    }

    @Test
    public void testLoginUser() {
        // register first (store details)
        Login reg = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        Registration registered = reg.getRegisteredUser();

        Login successLogin = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith");
        assertTrue(successLogin.loginUser(registered));
        assertEquals(Messages.loginSuccessMessage("Kyle", "Smith"), successLogin.returnLoginStatus());

        Login failedLogin = new Login("kyl_1", "wrongpassword", "Kyle", "Smith");
        assertFalse(failedLogin.loginUser(registered));
        assertEquals(Messages.LOGIN_FAIL_MESSAGE, failedLogin.returnLoginStatus());
    }
}
