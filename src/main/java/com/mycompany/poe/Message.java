package com.mycompany.poe;

/**
 *
 * Part 2: Message feature
 * @author lab_services_student: Vuyolwethu Bovu
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
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
    private static final ArrayList<String> generatedMessageIDs = new ArrayList<>();

    private static final String JSON_FILE = "messages.json";
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    //Start of Constructors
    public Message(String recipientCell, String messageContent, int messageNumber) {
        this(generateUniqueMessageID(), recipientCell, messageContent, messageNumber);
    }

    public Message(String messageID, String recipientCell, String messageContent, int messageNumber) {
        this.messageID = messageID;
        this.recipientCell = recipientCell;
        this.messageContent = messageContent;
        this.messageNumber = messageNumber;
        this.messageHash = "";
        this.messageStatus = "Pending";

        if (messageID != null && !generatedMessageIDs.contains(messageID)) {
            generatedMessageIDs.add(messageID);
        }
    }
    //End of Constructors

    //Start of Message ID section
    private static String generateUniqueMessageID() {
        Random random = new Random();
        String generatedID = "";
        boolean uniqueIDFound = false;

        while (!uniqueIDFound) {
            generatedID = "";

            for (int digitCounter = 0; digitCounter < 10; digitCounter++) {
                int generatedDigit = random.nextInt(10);
                generatedID = generatedID + generatedDigit;
            }

            if (!generatedMessageIDs.contains(generatedID)) {
                uniqueIDFound = true;
            }
        }

        return generatedID;
    }

    public boolean checkMessageID() {
        if (messageID == null) {
            return false;
        }

        return messageID.length() <= 10;
    }

    public String getMessageIDGeneratedMessage() {
        return "Message ID generated: " + messageID;
    }
    //End of Message ID section

    //Start of Recipient cell section
    public boolean isRecipientCellValid() {
        return Registration.isValidCellPhoneNumber(recipientCell);
    }

    public String checkRecipientCell() {
        if (isRecipientCellValid()) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }
    //End of Recipient cell section

    //Start of Message length section
    public boolean isMessageLengthValid() {
        int messageLength = 0;

        if (messageContent != null) {
            messageLength = messageContent.length();
        }

        return messageLength <= 250;
    }

    public String checkMessageLength() {
        int messageLength = 0;

        if (messageContent != null) {
            messageLength = messageContent.length();
        }

        if (messageLength <= 250) {
            return "Message ready to send.";
        }

        int numberOfCharactersOverLimit = messageLength - 250;
        return "Message exceeds 250 characters by " + numberOfCharactersOverLimit + "; please reduce the size.";
    }
    //End of Message length section

    //Start of Message hash section
    public String createMessageHash() {
        String preparedMessage = "";

        if (messageContent != null) {
            preparedMessage = messageContent.trim();
        }

        String firstWord = "";
        String lastWord = "";

        if (!preparedMessage.isEmpty()) {
            String[] messageWords = preparedMessage.split("\\s+");

            firstWord = removePunctuation(messageWords[0]);
            lastWord = removePunctuation(messageWords[messageWords.length - 1]);
        }

        String firstTwoDigitsOfMessageID = "";

        if (messageID != null) {
            if (messageID.length() >= 2) {
                firstTwoDigitsOfMessageID = messageID.substring(0, 2);
            } else {
                firstTwoDigitsOfMessageID = messageID;
            }
        }

        messageHash = firstTwoDigitsOfMessageID
                + ":"
                + messageNumber
                + ":"
                + (firstWord + lastWord).toUpperCase();

        return messageHash;
    }

    private String removePunctuation(String word) {
        return word.replaceAll("[^A-Za-z0-9]", "");
    }
    //End of Message hash section

    //Start of Sending choice section
    //Rubric-compatible no-argument method. The running console app uses SentMessage(scanner)
    //so that one shared Scanner is passed from ChatUp through QuickChat.
    public String SentMessage() {
        Scanner scanner = new Scanner(System.in);
        return SentMessage(scanner);
    }

    public String SentMessage(Scanner scanner) {
        System.out.println("Please select what should happen to this message:");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        System.out.print("Enter your choice: ");

        String messageChoice = scanner.nextLine();
        return SentMessage(messageChoice);
    }

    public String SentMessage(String messageChoice) {
        if (messageChoice == null) {
            return "Invalid choice. Please try again.";
        }

        String preparedChoice = messageChoice.trim().toUpperCase();

        switch (preparedChoice) {
            //Start of Send Message choice
            case "1", "SEND", "SEND MESSAGE" -> {
                return sendMessage();
            }
            //End of Send Message choice

            //Start of Disregard Message choice
            case "2", "DISREGARD", "DISREGARD MESSAGE", "DISCARD", "DISCARD MESSAGE" -> {
                return disregardMessage();
            }
            //End of Disregard Message choice

            //Start of Store Message choice
            case "3", "STORE", "STORE MESSAGE", "STORE MESSAGE TO SEND LATER" -> {
                return storeMessage();
            }
            //End of Store Message choice

            default -> {
                return "Invalid choice. Please try again.";
            }
        }
    }

    //Rubric-friendly lower-case wrapper for calls that use standard Java method naming
    public String sentMessage(Scanner scanner) {
        return SentMessage(scanner);
    }

    //Rubric-friendly lower-case wrapper for direct unit tests
    public String sentMessage(String messageChoice) {
        return SentMessage(messageChoice);
    }
    //End of Sending choice section

    //Start of Send, disregard and store messages section
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
      IEEE Attribution for the researched JSON storage feature:
      This feature uses Gson to convert Message objects into JSON text and to read stored JSON text back into Java Message objects [1].
      This feature also uses Java file utilities to read and write the JSON text file [2].
      The file-open options used when writing the JSON file are described by Oracle's StandardOpenOption documentation [3].

      References
      [1] Google, "Gson User Guide," [Online]. Available:
          <https://google.github.io/gson/UserGuide.html>
          [Accessed: May 19, 2026].

      [2] Oracle, "Files (Java Platform, Standard Edition 17 & JDK 17)," [Online]. Available:
          <https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/file/Files.html>
          [Accessed: May 19, 2026].

      [3] Oracle, "StandardOpenOption (Java Platform, Standard Edition 17 & JDK 17)," [Online]. Available:
          <https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/file/StandardOpenOption.html>
          [Accessed: May 19, 2026].
    */
    public String storeMessage() {
        if (messageHash == null || messageHash.isEmpty()) {
            createMessageHash();
        }

        messageStatus = "Stored";

        //Load whatever is already saved first so that earlier stored messages are not overwritten
        ArrayList<Message> storedMessages = loadMessagesFromJsonFile();

        //Add this exact message instance to the stored message list because the user chose to store it
        storedMessages.add(this);

        //Gson converts the updated Java list of stored Message objects into JSON text [1].
        String json = GSON.toJson(storedMessages);

        //Java Files opens the JSON file for writing [2]. CREATE creates it if needed, while TRUNCATE_EXISTING clears old file text before the updated JSON is written [3].
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
    //End of Send, disregard and store messages section

    //Start of Print messages section
    public String printCurrentMessageDetails() {
        if (messageHash == null || messageHash.isEmpty()) {
            createMessageHash();
        }

        String messageDetails = "";
        messageDetails = messageDetails + "Message ID: " + messageID + "\n";
        messageDetails = messageDetails + "Message Hash: " + messageHash + "\n";
        messageDetails = messageDetails + "Recipient: " + recipientCell + "\n";
        messageDetails = messageDetails + "Message: " + messageContent;

        return messageDetails;
    }

    public static String printMessages() {
        if (sentSessionMessages.isEmpty()) {
            return "No messages sent yet.";
        }

        String sentMessages = "";

        for (Message sentMessage : sentSessionMessages) {
            sentMessages = sentMessages + sentMessage.printCurrentMessageDetails() + "\n";
            sentMessages = sentMessages + "----------------------------" + "\n";
        }

        return sentMessages;
    }
    //End of Print messages section

    //Start of Total messages section
    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    //Rubric typo compatibility [the PoE table uses returnTotalMessagess()]
    public int returnTotalMessagess() {
        return returnTotalMessages();
    }

    public static int getTotalMessagesSent() {
        return totalMessagesSent;
    }
    //End of Total messages section

    //Start of JSON loading section
    private ArrayList<Message> loadMessagesFromJsonFile() {
        File jsonFile = new File(JSON_FILE);

        if (!jsonFile.exists()) {
            return new ArrayList<>();
        }

        //TypeToken keeps the ArrayList<Message> type available while Gson reads the stored JSON data [1].
        Type storedMessageListType = new TypeToken<ArrayList<Message>>() {
        }.getType();

        //Java Files opens the JSON file for reading [2].
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(JSON_FILE))) {
            //Gson converts the stored JSON text back into a Java list of Message objects [1].
            ArrayList<Message> storedMessages = GSON.fromJson(reader, storedMessageListType);

            if (storedMessages == null) {
                return new ArrayList<>();
            }

            return storedMessages;

        } catch (IOException | JsonSyntaxException e) {
            return new ArrayList<>();
        }
    }
    //End of JSON loading section

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

    //Start of Unit test support
    static void resetMessageDataForTests() {
        totalMessagesSent = 0;
        sentSessionMessages.clear();
        generatedMessageIDs.clear();

        try {
            Files.deleteIfExists(Paths.get(JSON_FILE));
        } catch (IOException e) {
            //No further action is required during test cleanup
        }
    }
    //End of Unit test support
}
