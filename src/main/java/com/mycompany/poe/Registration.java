package com.mycompany.poe;

/**
 * Part 1 done
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
  IEEE Attribution (phone format requirement + Java regex implementation)
  Purpose of this regex:
  1) I used this regex to validate the POE test format for a South African number with the +27 followed by 9 digits.
  2) Then I created the regex using the numbering/country code information in [1] and [2], and implemented it using Java’s regex engine
    (Pattern/Matcher) as explained in [3].
    
  [1] Independent Communications Authority of South Africa (ICASA), “Electronic Communications Act: Regulations: Numbering plan,”
      Government Gazette No. 39861, Notice 370, Mar. 24, 2016. [Online]. Available:
      <https://www.gov.za/sites/default/files/gcis_document/201603/39861gon370.pdf>. [Accessed: Apr. 10, 2026].

  [2] International Telecommunication Union (ITU), “List of Recommendation ITU-T E.164 assigned country codes (Position on 15 December 2016),”
      Annex to ITU Operational Bulletin No. 1114, Geneva, 2016. [Online]. Available:
      <https://www.itu.int/dms_pub/itu-t/opb/sp/T-SP-E.164D-2016-PDF-E.pdf>. [Accessed: Apr. 10, 2026].

  [3] Oracle, “Pattern (Java Platform, Standard Edition 8 API Specification),” [Online]. Available:
      <https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html>. [Accessed: Apr. 10, 2026].
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

        loginCanProceed = allValidationsPassed();
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

    // Static validators (lets Login check and validate safely even without a Registration object)
    public static boolean isValidUsername(String username) {
        if (username == null) {
            return false;
        }
        return (username.contains("_")) && (username.length() <= 5);
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.trim().isEmpty()) { // Java 8 safe
            return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean isValidCellPhoneNumber(String cellPhoneNumber) {
        if (cellPhoneNumber == null || cellPhoneNumber.trim().isEmpty()) { // Java 8 safe
            return false;
        }
        return CELLPHONENUMBER_PATTERN.matcher(cellPhoneNumber.trim()).matches(); // Java 8 safe
    }
    //End of static validators

    //Data validation section [String manipulation where needed; Regex where needed]
    public boolean checkUserName() {
        return isValidUsername(username);
    }

    public boolean checkPasswordComplexity() {
        return isValidPassword(password);
    }

    public boolean checkCellPhoneNumber() {
        return isValidCellPhoneNumber(cellPhoneNumber);
    }

    public boolean getLoginCanProceedStatus() {
        return loginCanProceed;
    }

    private boolean allValidationsPassed() {
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