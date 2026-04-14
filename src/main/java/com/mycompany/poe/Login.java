package com.mycompany.poe;
/**
 * Part 1: Done
 * @author lab_services_student: Vuyolwethu Bovu
 */
public final class Login {

    private String username;
    private String password;
    private String cellPhoneNumber;

    private boolean successLogin = false;

    private String name;
    private String surname;

    // This will be responsible for holding the registration details for POE-required registration methods in Login class
    private Registration registration;

    // Login constructor for registration (creates the internal Registration object)
    public Login(String username, String password, String cellPhoneNumber, String name, String surname) {
        this.registration = new Registration(username, password, cellPhoneNumber, name, surname);
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        this.name = name;
        this.surname = surname;
    }

    // Overloaded Login constructor for when the user is trying to log in (checks username + password only)
    public Login(String username, String password, String name, String surname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.cellPhoneNumber = null;
        this.registration = null;
    }

    //Start of Methods
    public boolean checkUserName() {
        // validate even if this Login object was created for login-only (registration == null)
        return Registration.isValidUsername(username);
    }

    public boolean checkPasswordComplexity() {
        // validate even if this Login object was created for login-only (registration == null)
        return Registration.isValidPassword(password);
    }

    public boolean checkCellPhoneNumber() {
        // validate even if this Login object was created for login-only (registration == null)
        return Registration.isValidCellPhoneNumber(cellPhoneNumber);
    }

    public String registerUser() {
        if (registration == null) {
            return Messages.REG_DETAILS_NOT_PROVIDED;
        }
        return registration.registerUser();
    }

    public boolean getLoginCanProceedStatus() {
        return registration != null && registration.getLoginCanProceedStatus();
    }

    // Wrappers so Login has the needed methods without rewriting the logic thats already in Registration
    public String getUsernameValidationMessage() {
        if (checkUserName()) {
            return Messages.USERNAME_SUCCESS_MESSAGE;
        }
        return Messages.USERNAME_WRONG_SUGGESTION;
    }

    public String getPasswordValidationMessage() {
        if (checkPasswordComplexity()) {
            return Messages.PASSWORD_SUCCESS_MESSAGE;
        }
        return Messages.USERPASSWORD_WRONG_SUGGESTION;
    }

    public String getCellPhoneValidationMessage() {
        if (checkCellPhoneNumber()) {
            return Messages.CELLPHONE_SUCCESS_MESSAGE;
        }
        return Messages.CELLPHONE_WRONG_SUGGESTION;
    }

    public Registration getRegisteredUser() {
        return registration;
    }
    //End of Wrappers so Login has the required methods without rewriting the logic thats already in Registration


    // Login user
    public boolean loginUser(Registration register) {
        if (register == null || username == null || password == null) {
            successLogin = false;
            return false;
        }

        successLogin = register.getUsername().equals(username)
                && register.getPassword().equals(password);
        return successLogin;
    }
    //End of Login user


    //Added for strict rubric signature compatibility (Boolean loginUser())
    public boolean loginUser() {
        if (registration == null || username == null || password == null) {
            successLogin = false;
            return false;
        }

        successLogin = registration.getUsername().equals(username)
                && registration.getPassword().equals(password);
        return successLogin;
    }
    //End of added rubric signature compatibility


    //Helper setter so you can reuse the same Login object in tests if needed
    public void setLoginCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
    //End of helper setter


    //Login Status
    public String returnLoginStatus() {
        if (successLogin) {
            return Messages.loginSuccessMessage(name, surname);
        }
        return Messages.LOGIN_FAIL_MESSAGE;
    }
    //End of Login status

    //End of Methods
}