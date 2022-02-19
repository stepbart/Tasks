package com.crud.tasks.domain;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class BadgesTest {

    @Mock
    private AttachmentsByType attachments;

    @Mock
    private Trello trello;

    @Test
    void getterAndSetterTest() {
        //Given
        attachments = new AttachmentsByType();
        trello = new Trello();
        trello.setBoard(1);
        trello.setCard(2);
        attachments.setTrello(trello);

        //When
        Badges badges = new Badges();
        badges.setAttachments(attachments);
        badges.setVotes(44);

        //Then
        assertEquals(44,badges.getVotes());
        assertEquals(2,badges.getAttachments().getTrello().getCard());
    }

}