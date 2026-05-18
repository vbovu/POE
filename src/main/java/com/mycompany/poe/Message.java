package com.mycompany.poe;

/**
 * Part 2 done
 *
 * @author lab_services_student: Vuyolwethu Bovu
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Message {

    private String messageID;
    private String recipientCell;
    private String messageContent;
    private String messageHash;
    private int messageNumber;
    private String messageStatus;

    private static int totalMessagesSent = 0;

    private static final ArrayList<Message> sentSessionMessages = new ArrayList<>();
    private static final String JSON_FILE = "messages.json";
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    //Start of Constructors
    public Message(String recipientCell, String messageContent, int messageNumber) {
        this(generateMessageID(), recipientCell, messageContent, messageNumber);
    }

    public Message(String messageID, String recipientCell, String messageContent, int messageNumber) {
        this.messageID = messageID;
        this.recipientCell = recipientCell;
        this.messageContent = messageContent;
        this.messageNumber = messageNumber;
        this.messageStatus = "Pending";
    }
    //End of Constructors

    //Start of Message ID section
    private static String generateMessageID() {
        Random random = new Random();
        StringBuilder generatedID = new StringBuilder();

        for (int digitCounter = 0; digitCounter < 10; digitCounter++) {
            generatedID.append(random.nextInt(10));
        }

        return generatedID.toString();
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public String getMessageIDGeneratedMessage() {
        return "Message ID generated: " + messageID;
    }
    //End of Message ID section

    //Start of Recipient cell number section
    public String checkRecipientCell() {
        if (Registration.isValidCellPhoneNumber(recipientCell)) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }
    //End of Recipient cell number section

    //Start of Message length section
    public String checkMessageLength() {
        int currentLength = (messageContent == null) ? 0 : messageContent.length();

        if (currentLength <= 250) {
            return "Message ready to send.";
        }

        int charactersOverLimit = currentLength - 250;
        return "Message exceeds 250 characters by " + charactersOverLimit + "; please reduce the size.";
    }
    //End of Message length section

    //Start of Message Hash section
    public String createMessageHash() {
        String safeMessage = (messageContent == null) ? "" : messageContent.trim();
        String[] messageWords = safeMessage.isEmpty() ? new String[0] : safeMessage.split("\\s+");

        String firstWord = (messageWords.length == 0) ? "" : removePunctuation(messageWords[0]);
        String lastWord = (messageWords.length == 0) ? "" : removePunctuation(messageWords[messageWords.length - 1]);

        String safeMessageID = (messageID == null) ? "" : messageID;
        String firstTwoDigits = safeMessageID.length() >= 2 ? safeMessageID.substring(0, 2) : safeMessageID;
        this.messageHash = firstTwoDigits + ":" + messageNumber + ":" + (firstWord + lastWord).toUpperCase(Locale.ROOT);

        return this.messageHash;
    }

    private String removePunctuation(String word) {
        return word.replaceAll("[^A-Za-z0-9]", "");
    }
    //End of Message Hash section

    //Start of Message decision section
    public String sentMessage() {
        Scanner scanner = new Scanner(System.in);
        return sentMessage(scanner);
    }

    public String sentMessage(Scanner scanner) {
        System.out.println("Key of choices:");
        System.out.println("1 - Send Message");
        System.out.println("2 - Disregard Message");
        System.out.println("3 - Store Message to send later");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine();
        return sentMessage(choice);
    }

    public String sentMessage(String choice) {
        if (choice == null) {
            return "Invalid choice. Please try again.";
        }

        String preparedChoice = choice.trim().toUpperCase(Locale.ROOT);

        return switch (preparedChoice) {
            //Send Message
            case "1", "SEND", "SEND MESSAGE" -> sendMessage();
            //End of Send Message

            //Disregard Message
            case "2", "DISREGARD", "DISREGARD MESSAGE", "DISCARD", "DISCARD MESSAGE" -> disregardMessage();
            //End of Disregard Message

            //Store Message to send later
            case "3", "STORE", "STORE MESSAGE", "STORE MESSAGE TO SEND LATER" -> storeMessage();
            //End of Store Message to send later

            default -> "Invalid choice. Please try again.";
        };
    }

    //Added for strict rubric wording compatibility (String: SentMessage())
    public String SentMessage() {
        return sentMessage();
    }
    //End of added rubric wording compatibility
    //End of Message decision section

    //Start of Send, disregard and store section
    public String sendMessage() {
        if (messageHash == null || messageHash.isEmpty()) {
            createMessageHash();
        }

        messageStatus = "Sent";
        totalMessagesSent++;
        sentSessionMessages.add(this);
        return "Message successfully sent.";
    }

    public String disregardMessage() {
        if (messageHash == null || messageHash.isEmpty()) {
            createMessageHash();
        }

        messageStatus = "Disregarded";
        return "Press 0 to delete the message.";
    }

    /*
      IEEE Attribution (JSON storage research)
      Purpose of this method:
      1) Gson is used to convert Message objects into JSON and back into Java objects [1].
      2) Java NIO file helpers are used to read and write the JSON file safely [2].

      [1] Google, "Gson User Guide," [Online]. Available:
          <https://google.github.io/gson/UserGuide.html>. [Accessed: May 18, 2026].

      [2] Oracle, "Files (Java Platform, Standard Edition 8 API Specification)," [Online]. Available:
          <https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html>. [Accessed: May 18, 2026].
    */
    public String storeMessage() {
        if (messageHash == null || messageHash.isEmpty()) {
            createMessageHash();
        }

        messageStatus = "Stored";

        // Load whatever is already saved first so that earlier stored messages are not overwritten
        ArrayList<Message> existingStoredMessages = loadMessagesFromFile();

        // Add this message instance to the list
        existingStoredMessages.add(this);

        // Write the updated list back to the JSON file
        String json = GSON.toJson(existingStoredMessages);

        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(JSON_FILE),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            writer.write(json);
            return "Message successfully stored.";

        } catch (IOException e) {
            return "Error storing message: " + e.getMessage();
        }
    }
    //End of Send, disregard and store section

    //Start of Print messages section
    public String printCurrentMessageDetails() {
        if (messageHash == null || messageHash.isEmpty()) {
            createMessageHash();
        }

        StringBuilder messageDetails = new StringBuilder();
        messageDetails.append("Message ID: ").append(messageID).append("\n");
        messageDetails.append("Message Hash: ").append(messageHash).append("\n");
        messageDetails.append("Recipient: ").append(recipientCell).append("\n");
        messageDetails.append("Message: ").append(messageContent);
        return messageDetails.toString();
    }

    public static String printMessages() {
        if (sentSessionMessages.isEmpty()) {
            return "No messages sent yet.";
        }

        StringBuilder sentMessages = new StringBuilder();

        for (Message message : sentSessionMessages) {
            sentMessages.append(message.printCurrentMessageDetails()).append("\n");
            sentMessages.append("----------------------------").append("\n");
        }

        return sentMessages.toString();
    }
    //End of Print messages section

    //Start of Total messages section
    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    public static int getTotalMessagesSent() {
        return totalMessagesSent;
    }

    //Added for strict rubric typo compatibility (returnTotalMessagess)
    public int returnTotalMessagess() {
        return returnTotalMessages();
    }
    //End of added rubric typo compatibility
    //End of Total messages section

    //Start of JSON file loading section
    private ArrayList<Message> loadMessagesFromFile() {
        File file = new File(JSON_FILE);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        Type listType = new TypeToken<ArrayList<Message>>() {
        }.getType();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(JSON_FILE))) {
            ArrayList<Message> loadedMessages = GSON.fromJson(reader, listType);
            return (loadedMessages != null) ? loadedMessages : new ArrayList<>();

        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    //End of JSON file loading section

    //Start of Getters
    public String getMessageID() {
        return messageID;
    }

    public String getRecipientCell() {
        return recipientCell;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getMessageStatus() {
        return messageStatus;
    }
    //End of Getters

    //Start of Unit test helper
    static void resetMessageStateForTesting() {
        totalMessagesSent = 0;
        sentSessionMessages.clear();

        try {
            Files.deleteIfExists(Paths.get(JSON_FILE));
        } catch (IOException ignored) {
            //No action is required during test cleanup
        }
    }
    //End of Unit test helper
}
