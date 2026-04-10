package com.mycompany.poe;

/**
 *
 * @author lab_services_student: Vuyolwethu Bovu
 */
import java.util.Scanner;

public class ChatUp {

    // Registration method
    private static Registration doRegistration(Scanner scanner) {
        System.out.println(Messages.APP_WELCOME);

        System.out.print("Please enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Please enter your surname: ");
        String surname = scanner.nextLine();

        System.out.print("Please enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Please enter your password: ");
        String password = scanner.nextLine();

        System.out.print("Please enter your cell phone number: ");
        String cellPhoneNumber = scanner.nextLine();

        //Registration checks done via Login class methods [like POE asked]
        Login registrationLogin = new Login(username, password, cellPhoneNumber, name, surname);

        System.out.println(registrationLogin.registerUser());

        if (!registrationLogin.getLoginCanProceedStatus()) {
            return null;
        }

        return registrationLogin.getRegisteredUser();
    }

    // Login method 
    private static boolean doLoginAttempt(Scanner scanner, Registration register) {
        System.out.println(Messages.LOGIN_PROMPT);

        System.out.print("Please enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Please enter your password: ");
        String password = scanner.nextLine();

        Login login = new Login(username, password, register.getName(), register.getSurname());

        boolean success = login.loginUser(register);

        // show the rubric message after the attempt
        System.out.println(login.returnLoginStatus());

        return success;
    }

    // Main method
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Registration process 
        Registration register = doRegistration(scanner);

        if (register == null) {
            System.out.println(Messages.REGISTRATION_UNSUCCESSFUL);
            return;
        }
        //End of Registration process

        //Login process
        boolean success = doLoginAttempt(scanner, register);

        // Retry once (as hinted by rubric when they stated 'try again') 
        if (!success) {
            System.out.println(Messages.FIRST_LOGIN_FAILED_LAST_TRY);
            success = doLoginAttempt(scanner, register);
        }

        //if the 'retry' STILL fails 
        if (!success) {
            System.out.println(Messages.ALL_ATTEMPTS_FAILED);
        }

        //End of Login process
    }
}
