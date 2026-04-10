package com.mycompany.poe;

/**
 *
 * @author lab_services_student: Vuyolwethu Bovu
 */
import java.util.regex.*;

public class Registration {

    private String username;
    private String password;
    private String cellPhoneNumber;
    private String name;
    private String surname;
    private static boolean permissionForFinalizationOfRegistration = false;
    private static boolean loginCanProceed = false;

    //CONSTANTS 
    //[CONSTANT PATTERNS FOR THE DATA VALIDATION] 
    private static final Pattern PASSWORD_PATTERN
            = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$");

    private static final Pattern CELLPHONENUMBER_PATTERN
            = Pattern.compile("^\\+27\\d{9}$");
    //END OF CONSTANTS [CONSTANT PATTERNS FOR THE DATA VALIDATION] 

    //[CONSTANT PATTERNS FOR THE FAILURE MESSAGES]
    private static final String USERNAME_WRONG_SUGGESTION
            = "\nKindly Ensure your username:\n- Contains an Underscore (_)\n- Is no more than 5 characters long";

    private static final String USERPASSWORD_WRONG_SUGGESTION
            = "\nKindly ensure your password:\n- At least 8 characters\n- Contains a capital letter\n- Contains a number\n- Contains a special character";

    private static final String CELLPHONE_WRONG_SUGGESTION
            = "\nKindly ensure your cellphone number:\n- Is the correct length\n- Contains a valid international country code";
//END OF [CONSTANT PATTERNS FOR THE FAILURE MESSAGES]

    //CONSTRUCTOR
    public Registration(String username, String password, String cellPhoneNumber, String name, String surname) {
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        if (all_validations_passed()) {
            permissionForFinalizationOfRegistration = true;
        }

        javax.swing.JOptionPane.showMessageDialog(null, registerUser()); //Start the registration proess
    }
    //END OF CONSTRUCTOR 

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    //Start of Error messages
    private String msgUserNameWrong() {
        return """
               
               Dear, """ + username + "\nYour username is incorrectly formatted";
    }

    private String msgPasswordWrong() {
        return """
               
               Dear, """ + username + "\nYour password is incorrectly formatted";
    }

    private String msgCellPhoneWrong() {
        return """
               
               Dear, """ + username + "\nYour cellphone number is incorrectly formatted";
    }
    //End of Error messages 

    //Success message
    private String msgWelcome() {
        return "\nWelcome, " + username + "\nYou have been successfully registered!";
    }
    //End of success message

    //Data validation section [String manipulation where needed; Regex where needed] 
    public boolean checkUserName() {
        return (username.contains("_")) && (username.length() <= 5);
    }

    public boolean checkPasswordComplexity() {
        if (password == null || password.isBlank()) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public boolean checkCellPhoneNumber() {
        if (password == null || cellPhoneNumber.isBlank()) {
            return false;
        }
        return CELLPHONENUMBER_PATTERN.matcher(cellPhoneNumber.strip()).matches();
    }

    public boolean getLoginCanProceedStatus() {
        return loginCanProceed;
    }

    private boolean all_validations_passed() {
        return (checkUserName() && checkPasswordComplexity()) && checkCellPhoneNumber(); //checking cell phone was not specified BUT it was inferred
    }
    //End of Data validation section

    //Feedback of validation to user
    private String registerUser() {
        boolean badUsername = !checkUserName();
        boolean badPassword = !checkPasswordComplexity();
        boolean badCell = !checkCellPhoneNumber();

        if (!badUsername && !badPassword && !badCell) {
            loginCanProceed = true;
            return msgWelcome();
        }

        String errorMsg = "Dear, " + username + "\n";

        if (badUsername) {
            errorMsg += msgUserNameWrong() + USERNAME_WRONG_SUGGESTION + "\n";
        }
        if (badPassword) {
            errorMsg += msgPasswordWrong() + USERPASSWORD_WRONG_SUGGESTION + "\n";
        }
        if (badCell) {
            errorMsg += msgCellPhoneWrong() + CELLPHONE_WRONG_SUGGESTION + "\n";
        }

        return errorMsg;
    }
    //End of Feedback of validation to user

}
