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
    private boolean loginCanProceed = false;

    //CONSTANTS 
    //[CONSTANT PATTERNS FOR THE DATA VALIDATION] 
    private static final Pattern PASSWORD_PATTERN
            = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$");

    /*
      IEEE Attribution for cellphone number regex (+27 followed by 9 digits):
      [1] Independent Communications Authority of South Africa (ICASA), “Electronic Communications Act: Regulations: Numbering plan,”
          Government Gazette No. 39861, Mar. 24, 2016. [Online]. Available:
          https://www.gov.za/sites/default/files/gcis_document/201603/39861gon370.pdf. [Accessed: 10-Apr-2026].
      [2] International Telecommunication Union, “Annex to ITU Operational Bulletin 1114: List of ITU-T Recommendation E.164 assigned country codes,”
          2016. [Online]. Available:
          https://www.itu.int/dms_pub/itu-t/opb/sp/T-SP-E.164D-2016-PDF-E.pdf. [Accessed: 10-Apr-2026].
     */
    private static final Pattern CELLPHONENUMBER_PATTERN
            = Pattern.compile("^\\+27\\d{9}$");
    //END OF CONSTANTS [CONSTANT PATTERNS FOR THE DATA VALIDATION] 

    //[CONSTANT PATTERNS FOR THE FAILURE MESSAGES]
    private static final String USERNAME_WRONG_SUGGESTION
            = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";

    private static final String USERPASSWORD_WRONG_SUGGESTION
            = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";

    private static final String CELLPHONE_WRONG_SUGGESTION
            = "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
//END OF [CONSTANT PATTERNS FOR THE FAILURE MESSAGES]

    //CONSTRUCTOR
    public Registration(String username, String password, String cellPhoneNumber, String name, String surname) {
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;

        // FIX: store name and surname
        this.name = name;
        this.surname = surname;

        if (all_validations_passed()) {
            permissionForFinalizationOfRegistration = true;
            loginCanProceed = true;
        } else {
            loginCanProceed = false;
        }
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
        return USERNAME_WRONG_SUGGESTION;
    }

    private String msgPasswordWrong() {
        return USERPASSWORD_WRONG_SUGGESTION;
    }

    private String msgCellPhoneWrong() {
        return CELLPHONE_WRONG_SUGGESTION;
    }
    //End of Error messages 

    //Success message
    private String msgWelcome() {
        return "Username successfully captured.\nPassword successfully captured.\nCell number successfully captured.";
    }
    //End of success message

    //Data validation section [String manipulation where needed; Regex where needed] 
    public boolean checkUserName() {
        if (username == null) {
            return false;
        }
        return (username.contains("_")) && (username.length() <= 5);
    }

    public boolean checkPasswordComplexity() {
        if (password == null || password.isBlank()) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public boolean checkCellPhoneNumber() {
        if (cellPhoneNumber == null || cellPhoneNumber.isBlank()) {
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
    public String getUsernameValidationMessage() {
        if (checkUserName()) {
            return "Username successfully captured.";
        }
        return msgUserNameWrong();
    }

    public String getPasswordValidationMessage() {
        if (checkPasswordComplexity()) {
            return "Password successfully captured.";
        }
        return msgPasswordWrong();
    }

    public String getCellPhoneValidationMessage() {
        if (checkCellPhoneNumber()) {
            return "Cell number successfully captured.";
        }
        return msgCellPhoneWrong();
    }

    public String registerUser() {
        if (!checkUserName()) {
            loginCanProceed = false;
            return msgUserNameWrong();
        }

        if (!checkPasswordComplexity()) {
            loginCanProceed = false;
            return msgPasswordWrong();
        }

        if (!checkCellPhoneNumber()) {
            loginCanProceed = false;
            return msgCellPhoneWrong();
        }

        loginCanProceed = true;
        return msgWelcome();
    }
    //End of Feedback of validation to user

}
