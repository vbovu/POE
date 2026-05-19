package com.mycompany.poe;

/**
 *
 * Part 1 done + Part 2 QuickChat handover added
 * @author lab_services_student: Vuyolwethu Bovu
 */
import java.util.Scanner;

public class ChatUp {

    //Start of ChatUp system [registration and login gateway before QuickChat]

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
    //End of Registration method

    // Login method
    private static boolean doLoginAttempt(Scanner scanner, Registration register) {
        System.out.println(Messages.LOGIN_PROMPT);

        System.out.print("Please enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Please enter your password: ");
        String password = scanner.nextLine();

        Login login = new Login(username, password, register.getName(), register.getSurname());

        boolean success = login.loginUser(register);

        //Show the rubric message after the attempt
        System.out.println(login.returnLoginStatus());

        return success;
    }
    //End of Login method

    // Main method
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //Start of Registration process
        Registration register = doRegistration(scanner);

        if (register == null) {
            System.out.println(Messages.REGISTRATION_UNSUCCESSFUL);
            scanner.close();
            return;
        }
        //End of Registration process

        //Start of Login process
        boolean success = doLoginAttempt(scanner, register);

        //Retry once [the Part 1 console flow already used this support message]
        if (!success) {
            System.out.println(Messages.FIRST_LOGIN_FAILED_LAST_TRY);
            success = doLoginAttempt(scanner, register);
        }

        //If the retry still fails, the user must not access QuickChat
        if (!success) {
            System.out.println(Messages.ALL_ATTEMPTS_FAILED);
            scanner.close();
            return;
        }
        //End of Login process

        //Start of QuickChat handover [reached only after successful authentication]
        QuickChat quickChat = new QuickChat(scanner);
        quickChat.startQuickChat();
        //End of QuickChat handover [reached only after successful authentication]

        scanner.close();
    }
    //End of Main method

    //End of ChatUp system [registration and login gateway before QuickChat]
}
