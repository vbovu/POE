package com.mycompany.poe;

/**
 *
 * Part 2: QuickChat sending messages feature
 * @author lab_services_student: Vuyolwethu Bovu
 */
import java.util.Scanner;

public class QuickChat {

    private final Scanner scanner;
    private int maximumMessagesToEnter;
    private int numberOfMessagesEntered;

    //Start of Constructor
    public QuickChat(Scanner scanner) {
        this.scanner = scanner;
        this.maximumMessagesToEnter = 0;
        this.numberOfMessagesEntered = 0;
    }
    //End of Constructor

    //Start of QuickChat application flow
    public void startQuickChat() {
        System.out.println("Welcome to QuickChat.");

        maximumMessagesToEnter = readMaximumMessagesToEnter();

        boolean applicationIsRunning = true;

        while (applicationIsRunning) {
            displayMainMenu();
            String menuChoice = scanner.nextLine().trim();

            switch (menuChoice) {
                case "1" -> sendMessages();
                case "2" -> System.out.println("Coming Soon.");
                case "3" -> applicationIsRunning = false;
                default -> System.out.println("Invalid menu option. Please choose 1, 2, or 3.");
            }
        }
    }
    //End of QuickChat application flow

    //Start of Menu display
    private void displayMainMenu() {
        System.out.println();
        System.out.println("Please choose an option:");
        System.out.println("1) Send Messages");
        System.out.println("2) Show recently sent messages");
        System.out.println("3) Quit");
        System.out.print("Enter your choice: ");
    }
    //End of Menu display

    //Start of Message limit input
    private int readMaximumMessagesToEnter() {
        int messageLimit = 0;
        boolean validLimitEntered = false;

        while (!validLimitEntered) {
            System.out.print("How many messages would you like to enter? ");
            String userInput = scanner.nextLine().trim();

            try {
                messageLimit = Integer.parseInt(userInput);

                if (messageLimit > 0) {
                    validLimitEntered = true;
                } else {
                    System.out.println("Please enter a whole number greater than 0.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }

        return messageLimit;
    }
    //End of Message limit input

    //Start of Send messages feature
    private void sendMessages() {
        if (numberOfMessagesEntered >= maximumMessagesToEnter) {
            System.out.println("The selected number of messages has already been entered.");
            return;
        }

        for (int messageCounter = numberOfMessagesEntered; messageCounter < maximumMessagesToEnter;) {
            System.out.println();
            System.out.println("Message " + (messageCounter + 1) + " of " + maximumMessagesToEnter);

            System.out.print("Please enter the recipient cell phone number: ");
            String recipientCell = scanner.nextLine();

            System.out.print("Please enter your message: ");
            String messageContent = scanner.nextLine();

            Message message = new Message(recipientCell, messageContent, messageCounter);

            System.out.println(message.getMessageIDGeneratedMessage());
            System.out.println(message.checkRecipientCell());
            System.out.println(message.checkMessageLength());

            boolean recipientIsValid = message.isRecipientCellValid();
            boolean messageLengthIsValid = message.isMessageLengthValid();

            if (!recipientIsValid || !messageLengthIsValid) {
                continue;
            }

            //Only a valid message entry uses one of the user-defined message slots.
            numberOfMessagesEntered++;
            messageCounter++;

            System.out.println("Message Hash: " + message.createMessageHash());

            String sendingDecisionResult = message.SentMessage(scanner);
            System.out.println(sendingDecisionResult);

            if (sendingDecisionResult.equals("Message successfully sent.")) {
                System.out.println(message.printCurrentMessageDetails());
            }
        }

        System.out.println("Total messages successfully sent: " + Message.getTotalMessagesSent());
    }
    //End of Send messages feature
}
