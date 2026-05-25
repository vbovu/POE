package com.mycompany.poe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageReportManagerTest {

    //Start of Test preparation
    @BeforeEach
    void resetMessageData() {
        Message.resetMessageDataForTests();
    }
    //End of Test preparation

    //Start of Test data helper
    private MessageReportManager createManagerWithPart3TestData() {
        MessageReportManager manager = new MessageReportManager(5);

        Message message1 = new Message(
                "Developer",
                "1111111111",
                "+27834557896",
                "Did you get the cake?",
                0
        );

        Message message2 = new Message(
                "Developer",
                "2222222222",
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                1
        );

        Message message3 = new Message(
                "Developer",
                "3333333333",
                "+27834484567",
                "Yohoooo, I am at your gate.",
                2
        );

        Message message4 = new Message(
                "Developer",
                "0838884567",
                "+27834557896",
                "It is dinner time!",
                3
        );

        Message message5 = new Message(
                "Developer",
                "5555555555",
                "+27838884567",
                "Ok, I am leaving without you.",
                4
        );

        message1.SentMessage("1");
        manager.recordMessageFromCurrentSession(message1);

        message2.SentMessage("3");
        manager.recordMessageFromCurrentSession(message2);

        message3.SentMessage("2");
        manager.recordMessageFromCurrentSession(message3);

        message4.SentMessage("1");
        manager.recordMessageFromCurrentSession(message4);

        message5.SentMessage("3");
        manager.recordMessageFromCurrentSession(message5);

        return manager;
    }
    //End of Test data helper

    //Start of Sent messages array test
    @Test
    void sentMessagesArrayCorrectlyPopulated() {
        MessageReportManager manager = createManagerWithPart3TestData();

        assertArrayEquals(
                new String[]{"Did you get the cake?", "It is dinner time!"},
                manager.getSentMessageTextsArray()
        );
    }
    //End of Sent messages array test

    //Start of Longest stored message test
    @Test
    void displayLongestStoredMessageReturnsExpectedMessage() {
        MessageReportManager manager = createManagerWithPart3TestData();

        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                manager.displayLongestStoredMessage()
        );
    }
    //End of Longest stored message test

    //Start of Message ID search test
    @Test
    void searchForMessageIDReturnsExpectedMessage() {
        MessageReportManager manager = createManagerWithPart3TestData();

        assertEquals(
                "It is dinner time!",
                manager.searchMessageByID("0838884567")
        );
    }
    //End of Message ID search test

    //Start of Recipient search test
    @Test
    void searchMessagesByRecipientReturnsExpectedStoredMessages() {
        MessageReportManager manager = createManagerWithPart3TestData();

        assertEquals(
                "Where are you? You are late! I have asked you to be on time.\nOk, I am leaving without you.",
                manager.searchMessagesByRecipient("+27838884567")
        );
    }
    //End of Recipient search test

    //Start of Delete by hash test
    @Test
    void deleteMessageByHashReturnsExpectedDeleteMessage() {
        MessageReportManager manager = createManagerWithPart3TestData();
        String messageTwoHash = "22:1:WHERETIME";

        assertEquals(
                "Message: \"Where are you? You are late! I have asked you to be on time\" successfully deleted.",
                manager.deleteStoredMessageByHash(messageTwoHash)
        );

        assertEquals(
                "Ok, I am leaving without you.",
                manager.searchMessagesByRecipient("+27838884567")
        );
    }
    //End of Delete by hash test

    //Start of Report test
    @Test
    void displayReportContainsMessageHashRecipientAndMessage() {
        MessageReportManager manager = createManagerWithPart3TestData();

        String report = manager.displayFullStoredMessagesReport();

        assertTrue(report.contains("Message Hash: 22:1:WHERETIME"));
        assertTrue(report.contains("Recipient: +27838884567"));
        assertTrue(report.contains("Message: Where are you? You are late! I have asked you to be on time."));
        assertTrue(report.contains("Message Hash: 55:4:OKYOU"));
        assertTrue(report.contains("Message: Ok, I am leaving without you."));
    }
    //End of Report test
}
