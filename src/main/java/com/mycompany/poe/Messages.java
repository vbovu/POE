package com.mycompany.poe;

public final class Messages {

    private Messages() {
        //No need for anything here
    }

    // Constant registration success messages
    public static final String USERNAME_SUCCESS_MESSAGE = "Username successfully captured.";
    public static final String PASSWORD_SUCCESS_MESSAGE = "Password successfully captured.";
    public static final String CELLPHONE_SUCCESS_MESSAGE = "Cell number successfully captured.";

    public static final String REGISTRATION_SUCCESS_BLOCK
            = USERNAME_SUCCESS_MESSAGE + "\n"
            + PASSWORD_SUCCESS_MESSAGE + "\n"
            + CELLPHONE_SUCCESS_MESSAGE;

    //End of constant registration success messages
    
    // Constant registration failure messages 
    public static final String USERNAME_WRONG_SUGGESTION
            = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";

    public static final String USERPASSWORD_WRONG_SUGGESTION
            = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";

    public static final String CELLPHONE_WRONG_SUGGESTION
            = "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";

    //End of registration failure messages 
    
    
    // Login messages 
    public static final String LOGIN_FAIL_MESSAGE
            = "Username or password incorrect, please try again.";

    public static final String LOGIN_SUCCESS_SUFFIX
            = " it is great to see you again.";

    public static String loginSuccessMessage(String name, String surname) {
        return "Welcome " + name + ", " + surname + LOGIN_SUCCESS_SUFFIX;
    }
    //End of Login messages 

    // General message (not specified in the rubric, but used for app context)
    public static final String REG_DETAILS_NOT_PROVIDED
            = "Registration details not provided.";
    //End of General message (not specified in the rubric, but used for app context)
    
    
    // Console UI messages (not part of rubric marking, but used for the user experience)
    public static final String APP_WELCOME = "Welcome to ChatUp!";
    public static final String LOGIN_PROMPT = "Time to log in back to a chattered world of ups!";
    public static final String REGISTRATION_UNSUCCESSFUL = "Registration unsuccessful. Please restart the app and try again.";
    public static final String FIRST_LOGIN_FAILED_LAST_TRY = "First login attempt failed. This is your last try";
    public static final String ALL_ATTEMPTS_FAILED = "Apologies. All attempts failed. Goodbye.";
    // End of Console UI messages (not part of rubric marking, but used for the user experience)
}
