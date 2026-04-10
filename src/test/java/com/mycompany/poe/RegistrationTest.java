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
        assertEquals("Username successfully captured.", valid.getUsernameValidationMessage());

        Login invalid = new Login("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertFalse(invalid.checkUserName());
        assertEquals(
                "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.",
                invalid.getUsernameValidationMessage()
        );
    }

    @Test
    public void testGetPassword() {
        Login valid = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertTrue(valid.checkPasswordComplexity());
        assertEquals("Password successfully captured.", valid.getPasswordValidationMessage());

        Login invalid = new Login("kyl_1", "password", "+27838968976", "Kyle", "Smith");
        assertFalse(invalid.checkPasswordComplexity());
        assertEquals(
                "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
                invalid.getPasswordValidationMessage()
        );
    }

    @Test
    public void testGetCellPhone() {
        Login valid = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertTrue(valid.checkCellPhoneNumber());
        assertEquals("Cell number successfully captured.", valid.getCellPhoneValidationMessage());

        Login invalid = new Login("kyl_1", "Ch&&sec@ke99!", "08966553", "Kyle", "Smith");
        assertFalse(invalid.checkCellPhoneNumber());
        assertEquals(
                "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.",
                invalid.getCellPhoneValidationMessage()
        );
    }

    @Test
    public void testRegisterUserOverallMessage() {
        Login valid = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals(
                "Username successfully captured.\nPassword successfully captured.\nCell number successfully captured.",
                valid.registerUser()
        );
        assertTrue(valid.getLoginCanProceedStatus());

        Login invalidUsername = new Login("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals(
                "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.",
                invalidUsername.registerUser()
        );
        assertFalse(invalidUsername.getLoginCanProceedStatus());
    }

    @Test
    public void testLoginUser() {
        // register first (store details)
        Login reg = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertTrue(reg.getLoginCanProceedStatus());
        Registration registered = reg.getRegisteredUser();

        Login successLogin = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith");
        assertTrue(successLogin.loginUser(registered));
        assertEquals("Welcome Kyle, Smith it is great to see you again.", successLogin.returnLoginStatus());

        Login failedLogin = new Login("kyl_1", "wrongpassword", "Kyle", "Smith");
        assertFalse(failedLogin.loginUser(registered));
        assertEquals("Username or password incorrect, please try again.", failedLogin.returnLoginStatus());
    }
}
