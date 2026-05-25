package com.mycompany.poe;

/**
 *
 * Part 2: QuickChat sending messages feature + Part 3 stored messages menu
 * @author lab_services_student: Vuyolwethu Bovu
 */
import java.util.Scanner;

public class QuickChat {

    private final Scanner scanner;
    private final String senderFullName;
    private int maximumMessagesToEnter;
    private int numberOfMessagesEntered;
    private MessageReportManager messageReportManager;

    //Start of Constructors
    public QuickChat(Scanner scanner) {
        this(scanner, null);
    }

    public QuickChat(Scanner scanner, Registration registeredUser) {
        this.scanner = scanner;
        this.senderFullName = getSenderFullNameFromRegisteredUser(registeredUser);
        this.maximumMessagesToEnter = 0;
        this.numberOfMessagesEntered = 0;
        this.messageReportManager = null;
    }
    //End of Constructors

    //Start of Sender helper
    private String getSenderFullNameFromRegisteredUser(Registration registeredUser) {
        if (registeredUser == null) {
            return "Unknown sender";
        }

        String fullName = registeredUser.getName() + " " + registeredUser.getSurname();

        if (fullName.trim().isEmpty()) {
            return "Unknown sender";
        }

        return fullName.trim();
    }
    //End of Sender helper

    //Start of QuickChat application flow
    public void startQuickChat() {
        System.out.println("Welcome to QuickChat.");

        maximumMessagesToEnter = readMaximumMessagesToEnter();
        messageReportManager = new MessageReportManager(maximumMessagesToEnter);

        boolean applicationIsRunning = true;

        while (applicationIsRunning) {
            displayMainMenu();
            String menuChoice = scanner.nextLine().trim();

            switch (menuChoice) {
                case "1" -> sendMessages();
                case "2" -> System.out.println("Coming Soon.");
                case "3" -> applicationIsRunning = false;
                case "4" -> openStoredMessagesMenu();
                default -> System.out.println("Invalid menu option. Please choose 1, 2, 3, or 4.");
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
        System.out.println("4) Stored Messages");
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

        for (int messageCounter = numberOfMessagesEntered; messageCounter < maximumMessagesToEnter; messageCounter++) {
            System.out.println();
            System.out.println("Message " + (messageCounter + 1) + " of " + maximumMessagesToEnter);

            System.out.print("Please enter the recipient cell phone number: ");
            String recipientCell = scanner.nextLine();

            System.out.print("Please enter your message: ");
            String messageContent = scanner.nextLine();

            Message message = new Message(senderFullName, recipientCell, messageContent, messageCounter, true);
            numberOfMessagesEntered++;

            System.out.println(message.getMessageIDGeneratedMessage());
            System.out.println(message.checkRecipientCell());
            System.out.println(message.checkMessageLength());
            System.out.println("Message Hash: " + message.createMessageHash());

            String sendingDecisionResult = message.SentMessage(scanner);
            System.out.println(sendingDecisionResult);

            if (messageReportManager != null) {
                messageReportManager.recordMessageFromCurrentSession(message);
            }

            if (sendingDecisionResult.equals("Message successfully sent.")) {
                System.out.println(message.printCurrentMessageDetails());
            }
        }

        System.out.println("Total messages successfully sent: " + Message.getTotalMessagesSent());
    }
    //End of Send messages feature

    //Start of Stored messages menu feature
    private void openStoredMessagesMenu() {
        if (messageReportManager == null) {
            messageReportManager = new MessageReportManager(maximumMessagesToEnter);
        }

        boolean storedMessagesMenuIsRunning = true;

        while (storedMessagesMenuIsRunning) {
            displayStoredMessagesMenu();
            String storedMenuChoice = scanner.nextLine().trim();

            switch (storedMenuChoice) {
                case "1" -> System.out.println(messageReportManager.displaySenderAndRecipientOfStoredMessages());
                case "2" -> System.out.println(messageReportManager.displayLongestStoredMessage());
                case "3" -> searchForMessageByID();
                case "4" -> searchMessagesByRecipient();
                case "5" -> deleteMessageByHash();
                case "6" -> System.out.println(messageReportManager.displayFullStoredMessagesReport());
                case "7" -> storedMessagesMenuIsRunning = false;
                default -> System.out.println("Invalid stored messages option. Please choose 1 to 7.");
            }
        }
    }

    private void displayStoredMessagesMenu() {
        System.out.println();
        System.out.println("Stored Messages Menu");
        System.out.println("1) Display sender and recipient of all stored messages");
        System.out.println("2) Display the longest stored message");
        System.out.println("3) Search for a message by message ID");
        System.out.println("4) Search sent or stored messages by recipient");
        System.out.println("5) Delete a message using the message hash");
        System.out.println("6) Display full stored-message report");
        System.out.println("7) Back to main menu");
        System.out.print("Enter your choice: ");
    }

    private void searchForMessageByID() {
        System.out.print("Please enter the message ID to search for: ");
        String messageID = scanner.nextLine();
        System.out.println(messageReportManager.searchMessageByIDDetails(messageID));
    }

    private void searchMessagesByRecipient() {
        System.out.print("Please enter the recipient cell phone number to search for: ");
        String recipientCell = scanner.nextLine();
        System.out.println(messageReportManager.searchMessagesByRecipient(recipientCell));
    }

    private void deleteMessageByHash() {
        System.out.print("Please enter the message hash to delete: ");
        String messageHash = scanner.nextLine();
        System.out.println(messageReportManager.deleteStoredMessageByHash(messageHash));
    }
    //End of Stored messages menu feature
}
