package com.mycompany.poe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @BeforeEach
    void resetMessageState() {
        Message.resetMessageStateForTesting();
    }

    //Start of Message length tests
    @Test
    void messageLengthReturnsSuccessWhenMessageIsWithinLimit() {
        Message message = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);

        assertEquals("Message ready to send.", message.checkMessageLength());
    }

    @Test
    void messageLengthReturnsFailureWhenMessageExceedsLimit() {
        String longMessage = "a".repeat(251);
        Message message = new Message("0012345678", "+27718693002", longMessage, 0);

        assertEquals("Message exceeds 250 characters by 1; please reduce the size.", message.checkMessageLength());
    }
    //End of Message length tests

    //Start of Recipient cell tests
    @Test
    void recipientNumberReturnsSuccessWhenCorrectlyFormatted() {
        Message message = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);

        assertEquals("Cell phone number successfully captured.", message.checkRecipientCell());
    }

    @Test
    void recipientNumberReturnsFailureWhenIncorrectlyFormatted() {
        Message message = new Message("0012345678", "08575975889", "Hi Keegan, did you receive the payment?", 1);

        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", message.checkRecipientCell());
    }
    //End of Recipient cell tests

    //Start of Message hash tests
    @Test
    void messageHashMatchesRequiredTestCaseOne() {
        Message message = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);

        assertEquals("00:0:HITONIGHT", message.createMessageHash());
    }

    @Test
    void remainderOfMessageHashesAreCheckedInALoop() {
        String[] messageIDs = {"0012345678", "9812345678", "4512345678"};
        String[] messages = {
                "Hi Mike, can you join us for dinner tonight?",
                "Hi Keegan, did you receive the payment?",
                "Thanks for joining the meeting today."
        };
        String[] expectedHashes = {
                "00:0:HITONIGHT",
                "98:1:HIPAYMENT",
                "45:2:THANKSTODAY"
        };

        for (int index = 0; index < messages.length; index++) {
            Message message = new Message(messageIDs[index], "+27718693002", messages[index], index);
            assertEquals(expectedHashes[index], message.createMessageHash());
        }
    }
    //End of Message hash tests

    //Start of Message ID test
    @Test
    void generatedMessageIDIsCreatedAndDisplayed() {
        Message message = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);

        assertTrue(message.checkMessageID());
        assertEquals(10, message.getMessageID().length());
        assertTrue(message.getMessageIDGeneratedMessage().startsWith("Message ID generated: "));
    }
    //End of Message ID test

    //Start of SentMessage tests
    @Test
    void sentMessageReturnsSuccessfulSendChoice() {
        Message message = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);

        assertEquals("Message successfully sent.", message.sentMessage("Send Message"));
        assertEquals(1, message.returnTotalMessages());
    }

    @Test
    void sentMessageReturnsDisregardChoice() {
        Message message = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);

        assertEquals("Press 0 to delete the message.", message.sentMessage("Disregard Message"));
        assertEquals(0, message.returnTotalMessages());
    }

    @Test
    void sentMessageReturnsStoreChoice() {
        Message message = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);

        assertEquals("Message successfully stored.", message.sentMessage("Store Message"));
    }
    //End of SentMessage tests

    //Start of Print messages test
    @Test
    void printMessagesReturnsOnlyMessagesSentDuringTheSession() {
        Message sentMessage = new Message("0012345678", "+27718693002", "Hi Mike, can you join us for dinner tonight?", 0);
        Message storedMessage = new Message("9912345678", "+27718693002", "Hi Keegan, did you receive the payment?", 1);

        sentMessage.sendMessage();
        storedMessage.storeMessage();

        String printedMessages = Message.printMessages();

        assertTrue(printedMessages.contains("Message ID: 0012345678"));
        assertTrue(printedMessages.contains("Message: Hi Mike, can you join us for dinner tonight?"));
        assertFalse(printedMessages.contains("Message ID: 9912345678"));
    }
    //End of Print messages test
}
