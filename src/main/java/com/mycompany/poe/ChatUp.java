package com.mycompany.poe;

/**
 *
 * Part 1 done + Part 2 sending messages added
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

    //Start of Part 2 QuickChat application
    private static void runQuickChat(Scanner scanner) {
        System.out.println("Welcome to QuickChat.");

        int numberOfMessagesToEnter = readMessageLimit(scanner);
        boolean messagesCaptured = false;
        boolean applicationRunning = true;

        while (applicationRunning) {
            displayQuickChatMenu();
            String menuChoice = scanner.nextLine().trim();

            switch (menuChoice) {
                case "1":
                    if (!messagesCaptured) {
                        captureMessages(scanner, numberOfMessagesToEnter);
                        messagesCaptured = true;
                    } else {
                        System.out.println("The selected number of messages has already been entered.");
                    }
                    break;

                case "2":
                    System.out.println("Coming Soon.");
                    break;

                case "3":
                    System.out.println("Total messages successfully sent: " + getCurrentTotalMessages());
                    applicationRunning = false;
                    break;

                default:
                    System.out.println("Invalid menu option. Please choose 1, 2, or 3.");
                    break;
            }
        }
    }

    private static void displayQuickChatMenu() {
        System.out.println();
        System.out.println("Please choose an option:");
        System.out.println("1) Send Messages");
        System.out.println("2) Show recently sent messages");
        System.out.println("3) Quit");
        System.out.print("Enter your choice: ");
    }

    private static int readMessageLimit(Scanner scanner) {
        while (true) {
            System.out.print("How many messages would you like to enter? ");
            String input = scanner.nextLine().trim();

            try {
                int messageLimit = Integer.parseInt(input);

                if (messageLimit > 0) {
                    return messageLimit;
                }

                System.out.println("Please enter a whole number greater than 0.");

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static void captureMessages(Scanner scanner, int numberOfMessagesToEnter) {
        for (int messageCounter = 0; messageCounter < numberOfMessagesToEnter; messageCounter++) {
            System.out.println();
            System.out.println("Message " + (messageCounter + 1) + " of " + numberOfMessagesToEnter);

            System.out.print("Please enter the recipient cell phone number: ");
            String recipientCell = scanner.nextLine();

            System.out.print("Please enter your message: ");
            String messageContent = scanner.nextLine();

            Message message = new Message(recipientCell, messageContent, messageCounter);

            String recipientValidationMessage = message.checkRecipientCell();
            String messageLengthValidationMessage = message.checkMessageLength();

            System.out.println(message.getMessageIDGeneratedMessage());
            System.out.println(recipientValidationMessage);
            System.out.println(messageLengthValidationMessage);

            if (!recipientValidationMessage.equals("Cell phone number successfully captured.")) {
                continue;
            }

            if (!messageLengthValidationMessage.equals("Message ready to send.")) {
                continue;
            }

            System.out.println("Message Hash: " + message.createMessageHash());
            String sentMessageResult = message.sentMessage(scanner);
            System.out.println(sentMessageResult);

            if (sentMessageResult.equals("Message successfully sent.")) {
                System.out.println(message.printCurrentMessageDetails());
            }
        }

        System.out.println("Total messages successfully sent: " + getCurrentTotalMessages());
    }

    private static int getCurrentTotalMessages() {
        return Message.getTotalMessagesSent();
    }
    //End of Part 2 QuickChat application

    // Main method
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Registration process
        Registration register = doRegistration(scanner);

        if (register == null) {
            System.out.println(Messages.REGISTRATION_UNSUCCESSFUL);
            scanner.close();
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
            scanner.close();
            return;
        }

        //End of Login process

        //Part 2 quick chat process
        runQuickChat(scanner);
        //End of Part 2 quick chat process

        scanner.close();
    }
}
