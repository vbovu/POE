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
    private boolean loginCanProceed = false;

    //CONSTANTS FOR PATTERNS
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
    //END OF CONSTANTS FOR PATTERNS
    
    //Constructor 
    public Registration(String username, String password, String cellPhoneNumber, String name, String surname) {
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        this.name = name;
        this.surname = surname;

        loginCanProceed = all_validations_passed();
    }
    //End Of constructor 

    //Getters
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
    //End of Getters

    //Start of Error messages methods
    private String msgUserNameWrong() {
        return Messages.USERNAME_WRONG_SUGGESTION;
    }

    private String msgPasswordWrong() {
        return Messages.USERPASSWORD_WRONG_SUGGESTION;
    }

    private String msgCellPhoneWrong() {
        return Messages.CELLPHONE_WRONG_SUGGESTION;
    }
    //End of Error messages methods

    //Success messages method
    private String msgWelcome() {
        return Messages.REGISTRATION_SUCCESS_BLOCK;
    }
    //End of success message method

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
            return Messages.USERNAME_SUCCESS_MESSAGE;
        }
        return msgUserNameWrong();
    }

    public String getPasswordValidationMessage() {
        if (checkPasswordComplexity()) {
            return Messages.PASSWORD_SUCCESS_MESSAGE;
        }
        return msgPasswordWrong();
    }

    public String getCellPhoneValidationMessage() {
        if (checkCellPhoneNumber()) {
            return Messages.CELLPHONE_SUCCESS_MESSAGE;
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
