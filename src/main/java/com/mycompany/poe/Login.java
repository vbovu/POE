package com.mycompany.poe;
/**
 * Part 1: Done
 * @author lab_services_student: Vuyolwethu Bovu
 */
public final class Login {

    private String username;
    private String password;

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
        this.name = name;
        this.surname = surname;
    }

    // Overloaded Login constructor for when the user is trying to log in (checks username + password only)
    public Login(String username, String password, String name, String surname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.registration = null;
    }

    //Start of Methods
    public boolean checkUserName() {
        return registration != null && registration.checkUserName();
    }

    public boolean checkPasswordComplexity() {
        return registration != null && registration.checkPasswordComplexity();
    }

    public boolean checkCellPhoneNumber() {
        return registration != null && registration.checkCellPhoneNumber();
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
        return registration != null ? registration.getUsernameValidationMessage() : Messages.REG_DETAILS_NOT_PROVIDED;
    }

    public String getPasswordValidationMessage() {
        return registration != null ? registration.getPasswordValidationMessage() : Messages.REG_DETAILS_NOT_PROVIDED;
    }

    public String getCellPhoneValidationMessage() {
        return registration != null ? registration.getCellPhoneValidationMessage() : Messages.REG_DETAILS_NOT_PROVIDED;
    }

    public Registration getRegisteredUser() {
        return registration;
    }
    //End of Wrappers so Login has the required methods without rewriting the logic thats already in Registration

    
    // Login user
    public boolean loginUser(Registration register) {
        successLogin = register.getUsername().equals(username)
                && register.getPassword().equals(password);
        return successLogin;
    }
    //End of Login user
    
    
    
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
