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
        Registration valid = new Registration("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals("kyl_1", valid.getUsername());
        assertTrue(valid.checkUserName());

        Registration invalid = new Registration("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertFalse(invalid.checkUserName());
    }

    @Test
    public void testGetPassword() {
        Registration valid = new Registration("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals("Ch&&sec@ke99!", valid.getPassword());
        assertTrue(valid.checkPasswordComplexity());

        Registration invalid = new Registration("kyl_1", "password", "+27838968976", "Kyle", "Smith");
        assertFalse(invalid.checkPasswordComplexity());
    }

    @Test
    public void testGetName() {
        Registration reg = new Registration("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals("Kyle", reg.getName());
    }

    @Test
    public void testGetSurname() {
        Registration reg = new Registration("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals("Smith", reg.getSurname());
    }

    @Test
    public void testGetCellPhone() {
        Registration valid = new Registration("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertTrue(valid.checkCellPhoneNumber());

        Registration invalid = new Registration("kyl_1", "Ch&&sec@ke99!", "08966553", "Kyle", "Smith");
        assertFalse(invalid.checkCellPhoneNumber());
    }

    @Test
    public void testLoginUser() {
        Registration registered = new Registration("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");

        Login successLogin = new Login("kyl_1", "Ch&&sec@ke99!", "Kyle", "Smith");
        assertTrue(successLogin.loginUser(registered));
        assertEquals("Welcome Kyle, Smith it is great to see you again.", successLogin.returnLoginStatus());

        Login failedLogin = new Login("kyl_1", "wrongpassword", "Kyle", "Smith");
        assertFalse(failedLogin.loginUser(registered));
        assertEquals("Username or password incorrect, please try again.", failedLogin.returnLoginStatus());
    }
}
