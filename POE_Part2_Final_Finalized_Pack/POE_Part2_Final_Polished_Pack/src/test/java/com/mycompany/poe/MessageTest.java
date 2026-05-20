package com.mycompany.poe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    //Start of Test preparation
    @BeforeEach
    void resetMessageData() {
        Message.resetMessageDataForTests();
    }
    //End of Test preparation

    //Start of Message length tests
    @Test
    void messageLengthReturnsSuccessWhenMessageIsWithinLimit() {
        Message message = new Message(
                "0012345678",
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?",
                0
        );

        assertEquals("Message ready to send.", message.checkMessageLength());
    }

    @Test
    void messageLengthReturnsFailureWhenMessageIsOverLimit() {
        String messageOverLimit = "a".repeat(251);

        Message message = new Message(
                "0012345678",
                "+27718693002",
                messageOverLimit,
                0
        );

        assertEquals(
                "Message exceeds 250 characters by 1; please reduce the size.",
                message.checkMessageLength()
        );
    }
    //End of Message length tests

    //Start of Recipient cell tests
    @Test
    void recipientCellReturnsSuccessWhenCorrectlyFormatted() {
        Message message = new Message(
                "0012345678",
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?",
                0
        );

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    @Test
    void recipientCellReturnsFailureWhenIncorrectlyFormatted() {
        Message message = new Message(
                "0012345678",
                "08575975889",
                "Hi Keegan, did you receive the payment?",
                1
        );

        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                message.checkRecipientCell()
        );
    }
    //End of Recipient cell tests

    //Start of Message hash tests
    @Test
    void messageHashMatchesTheRequiredFirstTestCase() {
        Message message = new Message(
                "0012345678",
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?",
                0
        );

        assertEquals("00:0:HITONIGHT", message.createMessageHash());
    }

    @Test
    void messageHashesCanBeCheckedInALoop() {
        String[] messageIDs = {"0012345678", "9812345678", "4512345678"};
        String[] messageContents = {
                "Hi Mike, can you join us for dinner tonight?",
                "Hi Keegan, did you receive the payment?",
                "Thanks for joining the meeting today."
        };
        String[] expectedHashes = {
                "00:0:HITONIGHT",
                "98:1:HIPAYMENT",
                "45:2:THANKSTODAY"
        };

        for (int testCounter = 0; testCounter < messageContents.length; testCounter++) {
            Message message = new Message(
                    messageIDs[testCounter],
                    "+27718693002",
                    messageContents[testCounter],
                    testCounter
            );

            assertEquals(expectedHashes[testCounter], message.createMessageHash());
        }
    }
    //End of Message hash tests

    //Start of Message ID tests
    @Test
    void generatedMessageIDIsCreatedAndDisplayed() {
        Message message = new Message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?",
                0
        );

        assertTrue(message.checkMessageID());
        assertEquals(10, message.getMessageID().length());
        assertTrue(message.getMessageIDGeneratedMessage().startsWith("Message ID generated: "));
    }
    //End of Message ID tests

    //Start of SentMessage tests
    @Test
    void sentMessageReturnsSuccessfulSendOutput() {
        Message message = new Message(
                "0012345678",
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?",
                0
        );

        assertEquals("Message successfully sent.", message.SentMessage("1"));
        assertEquals(1, message.returnTotalMessages());
    }

    @Test
    void sentMessageReturnsDisregardOutput() {
        Message message = new Message(
                "0012345678",
                "+27718693002",
                "Hi Keegan, did you receive the payment?",
                1
        );

        assertEquals("Press 0 to delete the message.", message.SentMessage("2"));
        assertEquals(0, message.returnTotalMessages());
    }

    @Test
    void sentMessageReturnsStoredOutput() {
        Message message = new Message(
                "0012345678",
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?",
                0
        );

        assertEquals("Message successfully stored.", message.SentMessage("3"));
    }
    //End of SentMessage tests

    //Start of Print messages test
    @Test
    void printMessagesReturnsMessagesSentDuringTheCurrentSession() {
        Message sentMessage = new Message(
                "0012345678",
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?",
                0
        );

        Message storedMessage = new Message(
                "9912345678",
                "+27718693002",
                "Hi Keegan, did you receive the payment?",
                1
        );

        sentMessage.sendMessage();
        storedMessage.storeMessage();

        String printedMessages = Message.printMessages();

        assertTrue(printedMessages.contains("Message ID: 0012345678"));
        assertTrue(printedMessages.contains("Message: Hi Mike, can you join us for dinner tonight?"));
        assertFalse(printedMessages.contains("Message ID: 9912345678"));
    }
    //End of Print messages test
}
