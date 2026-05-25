package com.mycompany.poe;

/**
 *
 * Part 3: Store Data and Display Task Report
 * This class handles the Part 3 arrays, reports, searches and delete-by-hash logic.
 * @author lab_services_student: Vuyolwethu Bovu
 */
import java.io.IOException;
import java.util.ArrayList;

public class MessageReportManager {

    //Start of Part 3 arrays
    private Message[] allSessionMessages;
    private Message[] sentMessages;
    private Message[] disregardedMessages;
    private Message[] storedMessages;
    private String[] messageHashes;
    private String[] messageIDs;
    //End of Part 3 arrays

    //Start of Array counters
    private int allSessionMessageCount;
    private int sentMessageCount;
    private int disregardedMessageCount;
    private int messageHashCount;
    private int messageIDCount;
    //End of Array counters

    //Start of Constructor
    public MessageReportManager(int maximumMessagesToEnter) {
        int startingSize = maximumMessagesToEnter;

        if (startingSize < 1) {
            startingSize = 1;
        }

        allSessionMessages = new Message[startingSize];
        sentMessages = new Message[startingSize];
        disregardedMessages = new Message[startingSize];
        storedMessages = new Message[0];
        messageHashes = new String[startingSize];
        messageIDs = new String[startingSize];

        allSessionMessageCount = 0;
        sentMessageCount = 0;
        disregardedMessageCount = 0;
        messageHashCount = 0;
        messageIDCount = 0;

        refreshStoredMessagesArrayFromJson();
    }
    //End of Constructor

    //Start of Message recording section
    public void recordMessageFromCurrentSession(Message message) {
        if (message == null) {
            return;
        }

        if (message.getMessageHash() == null || message.getMessageHash().isEmpty()) {
            message.createMessageHash();
        }

        addToAllSessionMessages(message);
        addToMessageIDs(message.getMessageID());
        addToMessageHashes(message.getMessageHash());

        if (message.getMessageStatus() != null) {
            if (message.getMessageStatus().equalsIgnoreCase("Sent")) {
                addToSentMessages(message);
            } else if (message.getMessageStatus().equalsIgnoreCase("Disregarded")) {
                addToDisregardedMessages(message);
            } else if (message.getMessageStatus().equalsIgnoreCase("Stored")) {
                refreshStoredMessagesArrayFromJson();
            }
        }
    }

    private void addToAllSessionMessages(Message message) {
        if (allSessionMessageCount >= allSessionMessages.length) {
            allSessionMessages = increaseMessageArraySize(allSessionMessages);
        }

        allSessionMessages[allSessionMessageCount] = message;
        allSessionMessageCount++;
    }

    private void addToSentMessages(Message message) {
        if (sentMessageCount >= sentMessages.length) {
            sentMessages = increaseMessageArraySize(sentMessages);
        }

        sentMessages[sentMessageCount] = message;
        sentMessageCount++;
    }

    private void addToDisregardedMessages(Message message) {
        if (disregardedMessageCount >= disregardedMessages.length) {
            disregardedMessages = increaseMessageArraySize(disregardedMessages);
        }

        disregardedMessages[disregardedMessageCount] = message;
        disregardedMessageCount++;
    }

    private void addToMessageIDs(String messageID) {
        if (messageID == null) {
            return;
        }

        if (messageIDCount >= messageIDs.length) {
            messageIDs = increaseStringArraySize(messageIDs);
        }

        messageIDs[messageIDCount] = messageID;
        messageIDCount++;
    }

    private void addToMessageHashes(String messageHash) {
        if (messageHash == null) {
            return;
        }

        if (messageHashCount >= messageHashes.length) {
            messageHashes = increaseStringArraySize(messageHashes);
        }

        messageHashes[messageHashCount] = messageHash;
        messageHashCount++;
    }
    //End of Message recording section

    //Start of Array resize helpers
    private Message[] increaseMessageArraySize(Message[] originalArray) {
        Message[] biggerArray = new Message[originalArray.length + 5];

        for (int index = 0; index < originalArray.length; index++) {
            biggerArray[index] = originalArray[index];
        }

        return biggerArray;
    }

    private String[] increaseStringArraySize(String[] originalArray) {
        String[] biggerArray = new String[originalArray.length + 5];

        for (int index = 0; index < originalArray.length; index++) {
            biggerArray[index] = originalArray[index];
        }

        return biggerArray;
    }
    //End of Array resize helpers

    //Start of Stored messages array section
    public void refreshStoredMessagesArrayFromJson() {
        ArrayList<Message> storedMessagesList = Message.loadStoredMessagesFromJsonFile();
        storedMessages = new Message[storedMessagesList.size()];

        for (int index = 0; index < storedMessagesList.size(); index++) {
            storedMessages[index] = storedMessagesList.get(index);
        }
    }

    public String displaySenderAndRecipientOfStoredMessages() {
        refreshStoredMessagesArrayFromJson();

        if (storedMessages.length == 0) {
            return "No stored messages found.";
        }

        String output = "";

        for (int index = 0; index < storedMessages.length; index++) {
            Message message = storedMessages[index];
            output = output + "Sender: " + message.getSafeSenderFullName() + "\n";
            output = output + "Recipient: " + message.getRecipientCell();

            if (index < storedMessages.length - 1) {
                output = output + "\n----------------------------\n";
            }
        }

        return output;
    }

    public String displayLongestStoredMessage() {
        refreshStoredMessagesArrayFromJson();

        if (storedMessages.length == 0) {
            return "No stored messages found.";
        }

        Message longestMessage = storedMessages[0];

        for (int index = 1; index < storedMessages.length; index++) {
            String currentMessageText = safeString(storedMessages[index].getMessageContent());
            String longestMessageText = safeString(longestMessage.getMessageContent());

            if (currentMessageText.length() > longestMessageText.length()) {
                longestMessage = storedMessages[index];
            }
        }

        return safeString(longestMessage.getMessageContent());
    }
    //End of Stored messages array section

    //Start of Search section
    public String searchMessageByID(String messageID) {
        Message foundMessage = findMessageByID(messageID);

        if (foundMessage == null) {
            return "No message found for message ID: " + safeString(messageID);
        }

        return safeString(foundMessage.getMessageContent());
    }

    public String searchMessageByIDDetails(String messageID) {
        Message foundMessage = findMessageByID(messageID);

        if (foundMessage == null) {
            return "No message found for message ID: " + safeString(messageID);
        }

        String output = "";
        output = output + "Recipient: " + foundMessage.getRecipientCell() + "\n";
        output = output + "Message: " + foundMessage.getMessageContent();
        return output;
    }

    private Message findMessageByID(String messageID) {
        String preparedMessageID = safeString(messageID).trim();

        for (int index = 0; index < allSessionMessageCount; index++) {
            Message message = allSessionMessages[index];

            if (message != null && safeString(message.getMessageID()).equals(preparedMessageID)) {
                return message;
            }
        }

        refreshStoredMessagesArrayFromJson();

        for (int index = 0; index < storedMessages.length; index++) {
            Message message = storedMessages[index];

            if (message != null && safeString(message.getMessageID()).equals(preparedMessageID)) {
                return message;
            }
        }

        return null;
    }

    public String searchMessagesByRecipient(String recipientCell) {
        String preparedRecipientCell = safeString(recipientCell).trim();
        String output = "";
        String[] includedMessageIDs = new String[allSessionMessageCount + storedMessages.length + 5];
        int includedMessageIDCount = 0;

        for (int index = 0; index < allSessionMessageCount; index++) {
            Message message = allSessionMessages[index];

            if (messageMatchesRecipientForSentOrStoredSearch(message, preparedRecipientCell)) {
                output = addMessageContentToOutput(output, message.getMessageContent());
                includedMessageIDs[includedMessageIDCount] = message.getMessageID();
                includedMessageIDCount++;
            }
        }

        refreshStoredMessagesArrayFromJson();

        for (int index = 0; index < storedMessages.length; index++) {
            Message message = storedMessages[index];

            if (messageMatchesRecipientForSentOrStoredSearch(message, preparedRecipientCell)) {
                if (!messageIDAlreadyIncluded(message.getMessageID(), includedMessageIDs, includedMessageIDCount)) {
                    output = addMessageContentToOutput(output, message.getMessageContent());
                    includedMessageIDs[includedMessageIDCount] = message.getMessageID();
                    includedMessageIDCount++;
                }
            }
        }

        if (output.isEmpty()) {
            return "No sent or stored messages found for recipient: " + preparedRecipientCell;
        }

        return output;
    }

    private boolean messageMatchesRecipientForSentOrStoredSearch(Message message, String preparedRecipientCell) {
        if (message == null) {
            return false;
        }

        if (!safeString(message.getRecipientCell()).trim().equals(preparedRecipientCell)) {
            return false;
        }

        String messageStatus = safeString(message.getMessageStatus());
        return messageStatus.equalsIgnoreCase("Sent") || messageStatus.equalsIgnoreCase("Stored");
    }

    private boolean messageIDAlreadyIncluded(String messageID, String[] includedMessageIDs, int includedMessageIDCount) {
        for (int index = 0; index < includedMessageIDCount; index++) {
            if (safeString(includedMessageIDs[index]).equals(safeString(messageID))) {
                return true;
            }
        }

        return false;
    }

    private String addMessageContentToOutput(String currentOutput, String messageContent) {
        if (currentOutput == null || currentOutput.isEmpty()) {
            return safeString(messageContent);
        }

        return currentOutput + "\n" + safeString(messageContent);
    }
    //End of Search section

    //Start of Delete by hash section
    public String deleteStoredMessageByHash(String messageHash) {
        String preparedHash = safeString(messageHash).trim();
        ArrayList<Message> storedMessagesList = Message.loadStoredMessagesFromJsonFile();
        ArrayList<Message> updatedMessagesList = new ArrayList<>();
        Message deletedMessage = null;

        for (int index = 0; index < storedMessagesList.size(); index++) {
            Message currentMessage = storedMessagesList.get(index);

            if (currentMessage.getMessageHash() == null || currentMessage.getMessageHash().isEmpty()) {
                currentMessage.createMessageHash();
            }

            if (currentMessage.getMessageHash().equals(preparedHash) && deletedMessage == null) {
                deletedMessage = currentMessage;
            } else {
                updatedMessagesList.add(currentMessage);
            }
        }

        if (deletedMessage == null) {
            return "No stored message found for message hash: " + preparedHash;
        }

        try {
            Message.writeStoredMessagesToJsonFile(updatedMessagesList);
            refreshStoredMessagesArrayFromJson();
        } catch (IOException e) {
            return "Error deleting message: " + e.getMessage();
        }

        return "Message: \"" + prepareMessageForDeleteResponse(deletedMessage.getMessageContent()) + "\" successfully deleted.";
    }

    private String prepareMessageForDeleteResponse(String messageContent) {
        String preparedMessage = safeString(messageContent).trim();

        if (preparedMessage.endsWith(".")) {
            preparedMessage = preparedMessage.substring(0, preparedMessage.length() - 1);
        }

        return preparedMessage;
    }
    //End of Delete by hash section

    //Start of Report section
    public String displayFullStoredMessagesReport() {
        refreshStoredMessagesArrayFromJson();

        if (storedMessages.length == 0) {
            return "No stored messages found.";
        }

        String report = "Stored Messages Report\n";
        report = report + "======================";

        for (int index = 0; index < storedMessages.length; index++) {
            Message message = storedMessages[index];

            if (message.getMessageHash() == null || message.getMessageHash().isEmpty()) {
                message.createMessageHash();
            }

            report = report + "\n";
            report = report + "Message Hash: " + message.getMessageHash() + "\n";
            report = report + "Recipient: " + message.getRecipientCell() + "\n";
            report = report + "Message: " + message.getMessageContent();

            if (index < storedMessages.length - 1) {
                report = report + "\n----------------------------";
            }
        }

        return report;
    }

    public String displayFullSentMessagesReport() {
        if (sentMessageCount == 0) {
            return "No sent messages found.";
        }

        String report = "Sent Messages Report\n";
        report = report + "====================";

        for (int index = 0; index < sentMessageCount; index++) {
            Message message = sentMessages[index];

            if (message.getMessageHash() == null || message.getMessageHash().isEmpty()) {
                message.createMessageHash();
            }

            report = report + "\n";
            report = report + "Message Hash: " + message.getMessageHash() + "\n";
            report = report + "Recipient: " + message.getRecipientCell() + "\n";
            report = report + "Message: " + message.getMessageContent();

            if (index < sentMessageCount - 1) {
                report = report + "\n----------------------------";
            }
        }

        return report;
    }

    public String displaySentMessagesArray() {
        if (sentMessageCount == 0) {
            return "No sent messages found.";
        }

        String output = "";

        for (int index = 0; index < sentMessageCount; index++) {
            output = addMessageContentToOutput(output, sentMessages[index].getMessageContent());
        }

        return output;
    }
    //End of Report section

    //Start of Getter helpers for unit tests
    public String[] getSentMessageTextsArray() {
        String[] sentMessageTexts = new String[sentMessageCount];

        for (int index = 0; index < sentMessageCount; index++) {
            sentMessageTexts[index] = sentMessages[index].getMessageContent();
        }

        return sentMessageTexts;
    }

    public String[] getDisregardedMessageTextsArray() {
        String[] disregardedMessageTexts = new String[disregardedMessageCount];

        for (int index = 0; index < disregardedMessageCount; index++) {
            disregardedMessageTexts[index] = disregardedMessages[index].getMessageContent();
        }

        return disregardedMessageTexts;
    }

    public String[] getMessageHashesArray() {
        String[] usedHashes = new String[messageHashCount];

        for (int index = 0; index < messageHashCount; index++) {
            usedHashes[index] = messageHashes[index];
        }

        return usedHashes;
    }

    public String[] getMessageIDsArray() {
        String[] usedIDs = new String[messageIDCount];

        for (int index = 0; index < messageIDCount; index++) {
            usedIDs[index] = messageIDs[index];
        }

        return usedIDs;
    }
    //End of Getter helpers for unit tests

    //Start of Utility section
    private String safeString(String value) {
        if (value == null) {
            return "";
        }

        return value;
    }
    //End of Utility section
}
